package se.kth.ict.id2203.assignment4.rwac;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.assignment3.beb.BebBroadcast;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
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
	private Hashtable<Address, Set<Address>> readSet = new Hashtable<Address, Set<Address>>();
	
	
	public RWAC() {
		subscribe(handleAcInit, control);
		subscribe(handleAcPropose, ac);
		subscribe(handleReadMessage, beb);
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
			readSet.put(id, new LinkedHashSet<Address>());
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
				// TODO next time 
			}
			
			logger.debug("RWAC initialized!");
		}
		
	};
}
