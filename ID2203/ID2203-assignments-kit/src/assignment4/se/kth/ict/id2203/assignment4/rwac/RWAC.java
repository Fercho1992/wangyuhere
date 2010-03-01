package se.kth.ict.id2203.assignment4.rwac;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.assignment3.beb.BebBroadcast;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class RWAC extends ComponentDefinition {
	
	private static final Logger logger = LoggerFactory
	.getLogger(RWAC.class);
	
	
	Negative<AcLink> ac = negative(AcLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	Positive<BebLink> beb = positive(BebLink.class);
	
	private Set<Address> all;
	private Address self;
	private Set<Address> seenIds;
	private int majority;
	private Hashtable<Address, Integer> tempValue = new Hashtable<Address, Integer>();
	private Hashtable<Address, Integer> val = new Hashtable<Address, Integer>();
	private Hashtable<Address, Integer> wAcks = new Hashtable<Address, Integer>();
	private Hashtable<Address, Integer> rts = new Hashtable<Address, Integer>();
	private Hashtable<Address, Integer> wts = new Hashtable<Address, Integer>();
	private Hashtable<Address, Integer> tstamp = new Hashtable<Address, Integer>();
	private Hashtable<Address, Set<int[]>> readSet = new Hashtable<Address, Set<int[]>>();
	
	
	public RWAC() {
		subscribe(handleAcInit, control);
		subscribe(handleAcPropose, ac);
		subscribe(handleReadMessage, beb);
		subscribe(handleNAck, pp2p);
		subscribe(handleReadAck, pp2p);
		subscribe(handleWriteMessage, beb);
		subscribe(handleWriteAck, pp2p);
		
	}
	
	private Handler<AcInit> handleAcInit = new Handler<AcInit>() {

		@Override
		public void handle(AcInit event) {
			all = event.getTopology().getAllAddresses();
			self = event.getTopology().getSelfAddress();
			
			seenIds = new LinkedHashSet<Address>();
			majority = all.size()/2+1;
			
			logger.debug("RWAC initialized!");
		}
		
	};
	
	private void initInstance(Address id) {
		if(!seenIds.contains(id)) {
			tempValue.put(id, Integer.MIN_VALUE);
			val.put(id, Integer.MIN_VALUE);
			wAcks.put(id, 0);
			rts.put(id, 0);
			wts.put(id, 0);
			tstamp.put(id, self.getId());
			readSet.put(id, new LinkedHashSet<int[]>());
			seenIds.add(id);
		}
	}
	
	private Handler<AcPropose> handleAcPropose = new Handler<AcPropose>() {

		@Override
		public void handle(AcPropose event) {
			Address id = event.getId();
			int v = event.getValue();
			int N = all.size();
			initInstance(id);
			tstamp.put(id, tstamp.get(id)+N);
			tempValue.put(id, v);
			ReadMessage rm = new ReadMessage(self, id, tstamp.get(id));
			trigger(new BebBroadcast(rm), beb);
			
			logger.debug("Broadcast ReadMessage: " + rm);
		}
		
	};
	
	private Handler<ReadMessage> handleReadMessage = new Handler<ReadMessage>() {

		@Override
		public void handle(ReadMessage event) {
			Address pj = event.getSource();
			Address id = event.getId();
			int ts = event.getTstamp();
			
			initInstance(id);
			if(rts.get(id) >= ts || wts.get(id) >= ts) {
				NAck ack = new NAck(self, id);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend NAck message {}", ack);
			} else {
				rts.put(id, ts);
				ReadAck ack = new ReadAck(self, id, wts.get(id), val.get(id), ts);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend ReadAck message {}", ack);
			}
		}
		
	};
	
	private Handler<NAck> handleNAck = new Handler<NAck>() {

		@Override
		public void handle(NAck event) {
			Address id = event.getId();
			
			readSet.get(id).clear();
			wAcks.put(id, 0);
			AcDecide decide = new AcDecide(id, Integer.MIN_VALUE);
			trigger(decide, ac);
			logger.debug("Send AcDecide message {}", decide);
		}
		
	};
	
	private Handler<ReadAck> handleReadAck = new Handler<ReadAck>() {

		@Override
		public void handle(ReadAck event) {
			Address id = event.getId();
			int ts = event.getWts();
			int v = event.getVal();
			int sentts = event.getTs();
			
			if(sentts == tstamp.get(id)) {
				readSet.get(id).add(new int[]{ts, v});
				if(readSet.get(id).size() == majority) {
					for(int[] pair : readSet.get(id)) {
						if(pair[0] > ts) {
							ts = pair[0];
							v = pair[1];
						}
					}
					if(v != Integer.MIN_VALUE) {
						tempValue.put(id, v);
					}
					WriteMessage wm = new WriteMessage(self, id, tstamp.get(id), tempValue.get(id));
					trigger(new BebBroadcast(wm), beb);
					
					logger.debug("Broadcast WriteMessage: " + wm);
				}
			}
		}
		
	};
	
	private Handler<WriteMessage> handleWriteMessage = new Handler<WriteMessage>() {

		@Override
		public void handle(WriteMessage event) {
			Address pj = event.getSource();
			Address id = event.getId();
			int ts = event.getTs();
			int v = event.getVal();
			
			initInstance(id);
			if(rts.get(id) > ts || wts.get(id) > ts) {
				NAck ack = new NAck(self, id);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend NAck message {}", ack);
			} else {
				val.put(id, v);
				rts.put(id, ts);
				WriteAck ack = new WriteAck(self, id, ts);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend WriteAck message {}", ack);
			}
		}
		
	};
	
	private Handler<WriteAck> handleWriteAck = new Handler<WriteAck>() {

		@Override
		public void handle(WriteAck event) {
			Address id = event.getId();
			int sentts = event.getTs();
			
			if(sentts == tstamp.get(id)) {
				wAcks.put(id, wAcks.get(id) + 1);
				if(wAcks.get(id) == majority) {
					readSet.get(id).clear();
					wAcks.put(id, 0);
					AcDecide decide = new AcDecide(id, tempValue.get(id));
					trigger(decide, ac);
					logger.debug("Send AcDecide message {}", decide);
				}
			}
		}
		
	};
}
