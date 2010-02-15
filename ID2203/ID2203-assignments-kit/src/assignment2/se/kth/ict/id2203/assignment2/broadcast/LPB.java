package se.kth.ict.id2203.assignment2.broadcast;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import se.kth.ict.id2203.flp2p.FairLossPointToPointLink;
import se.kth.ict.id2203.flp2p.Flp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class LPB extends ComponentDefinition {

	Negative<LPBLink> lpb = negative(LPBLink.class);
	Positive<Timer> timer = positive(Timer.class);
	Positive<FairLossPointToPointLink> flp2p = positive(FairLossPointToPointLink.class);
	Positive<UnLink> ul = positive(UnLink.class);
	
	private int fanout;
	private int maxrounds;
	private double storeThreshold;
	private int timeDelay;
	private int lsn;
	private Address self;
	private Set<Address> neighbors;
	private Hashtable<Address, Integer> delivered;
	private Set<DataMessage> pending;
	private Set<DataMessage> stored;
	private Random random = new Random();
	
	
	public LPB() {
		subscribe(handleInit, control);
		subscribe(handleLPBTimeout, timer);
		subscribe(handleRequestMessage, flp2p);
		subscribe(handleDataMessage, flp2p);
		subscribe(handleUnDeliver, ul);
		subscribe(handleLPBBroadcast, lpb);
	}
	
	private Handler<LPBInit> handleInit = new Handler<LPBInit>() {

		@Override
		public void handle(LPBInit event) {
			fanout = event.getFanout();
			maxrounds = event.getMaxrounds();
			storeThreshold = event.getStoreThreshold();
			timeDelay = event.getTimeDelay();
			
			self = event.getTopology().getSelfAddress();
			neighbors = event.getTopology().getNeighbors(self);
			delivered = new Hashtable<Address, Integer>();
			for(Address a : neighbors) {
				delivered.put(a, 0);
			}
			lsn = 0;
			pending = new LinkedHashSet<DataMessage>();
			stored = new LinkedHashSet<DataMessage>();
		}
		
	};
	
	private void deliverPending(Address s) {
		for(DataMessage d : pending) {
			if(d.getSource().equals(s) && d.getSn() == delivered.get(s) + 1) {
				delivered.put(s, delivered.get(s)+1);
				pending.remove(d);
				trigger(new LPBDeliver(s, d.getMessage()), lpb);
			}
		}
	}
	
	private Set<Address> pickTargets(int ntargets) {
		Set<Address> targets = new LinkedHashSet<Address>();
		if(neighbors.size() < ntargets) return null;
		while(targets.size() < ntargets) {
			Address candidate = (Address) neighbors.toArray()[random.nextInt(neighbors.size())];
			if(!targets.contains(candidate)) {
				targets.add(candidate);
			}
		}
		return targets;
	}
	
	private void gossip(RequestMessage msg) {
		for(Address a : pickTargets(fanout)) {
			trigger(new Flp2pSend(a, msg), flp2p);
		}
	}
	
	private Handler<LPBBroadcast> handleLPBBroadcast = new Handler<LPBBroadcast>() {

		@Override
		public void handle(LPBBroadcast event) {
			lsn++;
			trigger(new UnBroadcast(new DataMessage(self, event.getMessage(), lsn)), ul);
		}
		
	};
	
	private Handler<UnDeliver> handleUnDeliver = new Handler<UnDeliver>() {

		@Override
		public void handle(UnDeliver event) {
			DataMessage data = event.getData();
			Address sm = data.getSource();
			int snm = data.getSn();
			if(random.nextDouble() > storeThreshold) {
				stored.add(event.getData());
			}
			if(snm == delivered.get(sm) + 1) {
				delivered.put(sm, snm);
			} else if(snm > delivered.get(sm) + 1) {
				pending.add(data);
				for(int seqnb = delivered.get(sm)+1; seqnb < snm; seqnb++) {
					gossip(new RequestMessage(self, sm, seqnb, maxrounds-1));
				}
				startTimer(timeDelay, event.getSource(), snm);
			}
		}
		
	};
	
	private Handler<RequestMessage> handleRequestMessage = new Handler<RequestMessage>() {

		@Override
		public void handle(RequestMessage event) {
			Address sm = event.getSm();
			int snm = event.getSeqnb();
			boolean sent = false;
			for(DataMessage d : stored) {
				if(d.getSn() == snm && d.getSource().equals(sm)) {
					trigger(new Flp2pSend(self, d), flp2p);
					sent = true;
				}
			}
			if(! sent && event.getMaxrounds() > 0) {
				gossip(new RequestMessage(event.getSource(), sm, snm, event.getMaxrounds()-1));
			}
		}
		
	};
	
	private Handler<DataMessage> handleDataMessage = new Handler<DataMessage>(){

		@Override
		public void handle(DataMessage event) {
			if(event.getSn() == delivered.get(event.getSource()) + 1) {
				delivered.put(event.getSource(), event.getSn());
				trigger(new LPBDeliver(event.getSource(), event.getMessage()), lpb);
				deliverPending(event.getSource());
			} else {
				pending.add(event);
			}
		}
	};
	
	private Handler<LPBTimeout> handleLPBTimeout = new Handler<LPBTimeout>() {

		@Override
		public void handle(LPBTimeout event) {
			if(event.getSn() == delivered.get(event.getSource()) + 1) {
				delivered.put(event.getSource(), event.getSn());
			}
		}
		
	};
	
	
	private void startTimer(int time, Address s, int snm) {
		ScheduleTimeout st = new ScheduleTimeout(time);
		st.setTimeoutEvent(new LPBTimeout(st, s, snm));
		trigger(st, timer);
	}
	
	
}
