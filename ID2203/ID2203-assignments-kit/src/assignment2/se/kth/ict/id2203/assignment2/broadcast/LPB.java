package se.kth.ict.id2203.assignment2.broadcast;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(LPB.class);

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
	private Set<Address> all;
	private Hashtable<Address, Integer> delivered;
	private Set<DataMessage> pending;
	private Set<DataMessage> stored;
	private Set<DataMessage> lost;
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
			all = event.getTopology().getAllAddresses();
			delivered = new Hashtable<Address, Integer>();
			for (Address a : all) {
				delivered.put(a, 0);
			}
			lsn = 0;
			pending = Collections.synchronizedSet(new LinkedHashSet<DataMessage>());
			stored = Collections.synchronizedSet(new LinkedHashSet<DataMessage>());
			lost = Collections.synchronizedSet(new LinkedHashSet<DataMessage>());
		}

	};

	private DataMessage exists(Address sm, int snm) {
		synchronized(pending) {
			for (DataMessage d : pending) {
				if (d.getSm().equals(sm) && snm == d.getSnm()) {
					return d;
				}
			}
		}
		return null;
	}

	private void deliverPending(Address s) {
		DataMessage d = null;
		while ((d = exists(s, delivered.get(s)+1)) != null) {
			delivered.put(s, d.getSnm());
			pending.remove(d);
			trigger(new LPBDeliver(s, d.getMessage()), lpb);
		}
	}

	private Set<Address> pickTargets(int ntargets) {
		Set<Address> targets = new LinkedHashSet<Address>();
		if (all.size() < ntargets)
			return null;
		while (targets.size() < ntargets) {
			Address candidate = (Address) all.toArray()[random.nextInt(all
					.size())];
			if (!targets.contains(candidate) && !candidate.equals(self)) {
				targets.add(candidate);
			}
		}
		return targets;
	}

	private void gossip(RequestMessage msg) {
		for (Address a : pickTargets(fanout)) {
			trigger(new Flp2pSend(a, msg), flp2p);
			logger.debug("gossip from {} for {} to " + a, msg.getSource(), msg
					.getSm()
					+ ":" + msg.getSeqnb());
		}
	}

	private Handler<LPBBroadcast> handleLPBBroadcast = new Handler<LPBBroadcast>() {

		@Override
		public void handle(LPBBroadcast event) {
			lsn++;
			trigger(new UnBroadcast(new DataMessage(self, event.getMessage(),
					lsn)), ul);
			logger.debug("LPB Broadcast");
		}

	};

	private Handler<UnDeliver> handleUnDeliver = new Handler<UnDeliver>() {

		@Override
		public void handle(UnDeliver event) {
			DataMessage data = event.getData();
			Address sm = data.getSm();
			int snm = data.getSnm();
			if (random.nextDouble() > storeThreshold) {
				stored.add(event.getData());
				logger.debug("{} store data {}", self, event.getData());
			}
			if (snm == delivered.get(sm) + 1) {
				delivered.put(sm, snm);
				trigger(new LPBDeliver(sm, data.getMessage()), lpb);
			} else if (snm > delivered.get(sm) + 1) {
				pending.add(data);
				for (int seqnb = delivered.get(sm) + 1; seqnb < snm; seqnb++) {
					gossip(new RequestMessage(self, sm, seqnb, maxrounds - 1));
				}
				// Correct: this pi should be sm
				// startTimer(timeDelay, event.getSource(), snm) -->
				logger.debug("Start timeout");
				startTimer(timeDelay, sm, snm);
			}
		}

	};

	private Handler<RequestMessage> handleRequestMessage = new Handler<RequestMessage>() {

		@Override
		public void handle(RequestMessage event) {
			Address sm = event.getSm();
			int snm = event.getSeqnb();
			boolean sent = false;
			for (DataMessage d : stored) {
				if (d.getSnm() == snm && d.getSm().equals(sm)) {
					trigger(new Flp2pSend(event.getSource(), d), flp2p);
					sent = true;
					logger.debug(self+" forward {} to {}", d, event.getSource());
				}
			}
			if (!sent && event.getMaxrounds() > 0) {
				logger.debug("forward gossip from {}", event.getSource());
				gossip(new RequestMessage(event.getSource(), sm, snm, event
						.getMaxrounds() - 1));
			}
		}

	};

	private Handler<DataMessage> handleDataMessage = new Handler<DataMessage>() {

		@Override
		public void handle(DataMessage event) {
			Address sm = event.getSm();
			if (event.getSnm() == delivered.get(sm) + 1) {
				delivered.put(sm, event.getSnm());
				trigger(new LPBDeliver(sm, event.getMessage()), lpb);
				deliverPending(sm);
			// Correct: only add pending when received sn > delivered sn + 1
			} else if(event.getSnm() > delivered.get(sm) + 1) {
				pending.add(event);
			} else {
				synchronized(lost){
					Iterator<DataMessage> i = lost.iterator();
					while(i.hasNext()) {
						DataMessage dm = i.next();
						if(dm.getSm().equals(event.getSm()) && dm.getSnm() == event.getSnm()) {
							i.remove();
							trigger(new LPBDeliver(sm, event.getMessage()), lpb);
							logger.info("{} was retrived!", event);
						}
					}
				}
			}
		}
	};

	private Handler<LPBTimeout> handleLPBTimeout = new Handler<LPBTimeout>() {

		@Override
		public void handle(LPBTimeout event) {
			logger.debug("gossip timeout");
			Address sm = event.getSource();
			int snm = event.getSn();
			for (int seqnb = delivered.get(sm) + 1; seqnb <= snm; seqnb++) {
				DataMessage d = null;
				if((d =exists(sm, seqnb)) != null) {
					delivered.put(sm, seqnb);
					pending.remove(d);
					trigger(new LPBDeliver(sm, d.getMessage()), lpb);
				}
				else {
					DataMessage dm = new DataMessage(sm, null, seqnb);
					logger.info("{} was lost!", dm);
					lost.add(dm);
				}
			}
		}
	};

	private void startTimer(int time, Address s, int snm) {
		ScheduleTimeout st = new ScheduleTimeout(time);
		st.setTimeoutEvent(new LPBTimeout(st, s, snm));
		trigger(st, timer);
	}

}
