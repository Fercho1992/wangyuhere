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
	private Set<Integer> seenIds;
	private int majority;
	private Hashtable<Integer, Integer> tempValue = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Integer> val = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Integer> wAcks = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Integer> rts = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Integer> wts = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Integer> tstamp = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Set<int[]>> readSet = new Hashtable<Integer, Set<int[]>>();
	
	
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
			
			seenIds = new LinkedHashSet<Integer>();
			majority = all.size()/2+1;
			
			logger.debug("RWAC initialized!");
		}
		
	};
	
	private void initInstance(int id) {
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
			logger.debug("Received AcPropose");
			int id = event.getId();
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
			int id = event.getId();
			int ts = event.getTstamp();
			
			logger.debug("Received ReadMessage from {}", pj);
			
			initInstance(id);
			if(rts.get(id) >= ts || wts.get(id) >= ts) {
				NAck ack = new NAck(self, id);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend NAck message to {} {}", pj, ack);
			} else {
				rts.put(id, ts);
				ReadAck ack = new ReadAck(self, id, wts.get(id), val.get(id), ts);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend ReadAck message to {} {}",pj,ack);
			}
		}
		
	};
	
	private Handler<NAck> handleNAck = new Handler<NAck>() {

		@Override
		public void handle(NAck event) {
			int id = event.getId();
			
			logger.debug("Received NAck from {}", event.getSource());
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
			int id = event.getId();
			int ts = event.getWts();
			int v = event.getVal();
			int sentts = event.getTs();
			
			logger.debug("Received ReadAck from {}", event.getSource());
			
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
			int id = event.getId();
			int ts = event.getTs();
			int v = event.getVal();
			
			logger.debug("Received WriteMessage from {}", event.getSource());
			
			initInstance(id);
			if(rts.get(id) > ts || wts.get(id) > ts) {
				NAck ack = new NAck(self, id);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend NAck message to {} {}", pj, ack);
			} else {
				val.put(id, v);
				rts.put(id, ts);
				WriteAck ack = new WriteAck(self, id, ts);
				trigger(new Pp2pSend(pj, ack), pp2p);
				logger.debug("Pp2psend WriteAck message to {} {}", pj, ack);
			}
		}
		
	};
	
	private Handler<WriteAck> handleWriteAck = new Handler<WriteAck>() {

		@Override
		public void handle(WriteAck event) {
			int id = event.getId();
			int sentts = event.getTs();
			
			logger.debug("Received WriteAck from {}", event.getSource());
			
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
