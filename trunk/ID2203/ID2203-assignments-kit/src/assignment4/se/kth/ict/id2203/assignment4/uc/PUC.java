package se.kth.ict.id2203.assignment4.uc;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.assignment3.beb.BebBroadcast;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.assignment4.eld.EldLink;
import se.kth.ict.id2203.assignment4.eld.Trust;
import se.kth.ict.id2203.assignment4.rwac.AcDecide;
import se.kth.ict.id2203.assignment4.rwac.AcLink;
import se.kth.ict.id2203.assignment4.rwac.AcPropose;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class PUC extends ComponentDefinition {
	private static final Logger logger = LoggerFactory
	.getLogger(PUC.class);
	
	Negative<UcLink> uc = negative(UcLink.class);
	Positive<BebLink> beb = positive(BebLink.class);
	Positive<AcLink> ac = positive(AcLink.class);
	Positive<EldLink> eld = positive(EldLink.class);
	
	private Address self;
	private Set<Integer> seenIds;
	private boolean leader;
	private Hashtable<Integer, Integer> proposal = new Hashtable<Integer, Integer>();
	private Hashtable<Integer, Boolean> proposed = new Hashtable<Integer, Boolean>();
	private Hashtable<Integer, Boolean> decided = new Hashtable<Integer, Boolean>();
	
	public PUC() {
		subscribe(handleUcInit, control);
		subscribe(handleTrust, eld);
		subscribe(handleUcPropose, uc);
		subscribe(handleAcDecide, ac);
		subscribe(handleDecided, beb);
	}
	
	private Handler<UcInit> handleUcInit = new Handler<UcInit>() {

		@Override
		public void handle(UcInit event) {
			self = event.getTopology().getSelfAddress();
			
			seenIds = new LinkedHashSet<Integer>();
			leader = false;
			
			logger.debug("PUC initialized!");
		}
		
	};
	
	private void initInstance(int id) {
		if(! seenIds.contains(id)) {
			proposal.put(id, Integer.MIN_VALUE);
			proposed.put(id, false);
			decided.put(id, false);
			seenIds.add(id);
		}
	}
	
	private void garbageCollect(int id) {
		seenIds.remove(id);
		proposal.remove(id);
		proposed.remove(id);
		decided.remove(id);
	}
	
	private Handler<Trust> handleTrust = new Handler<Trust>() {

		@Override
		public void handle(Trust event) {
			Address pi = event.getLeader();
			if(pi.equals(self)) {
				leader = true;
				for(int id : seenIds) {
					tryPropose(id);
				}
			} else {
				leader = false;
			}
		}
		
	};
	
	private Handler<UcPropose> handleUcPropose = new Handler<UcPropose>() {

		@Override
		public void handle(UcPropose event) {
			logger.debug("Receive UcPropose");
			int id = event.getId();
			int v = event.getVal();
			
			initInstance(id);
			proposal.put(id, v);
			tryPropose(id);
		}
		
	};
	
	private void tryPropose(int id) {
		if(leader || !proposed.get(id) || proposal.get(id) != Integer.MIN_VALUE) {
			proposed.put(id, true);
			AcPropose ap = new AcPropose(id, proposal.get(id));
			trigger(ap, ac);
			logger.debug("Send AcPropose: {}", ap);
		}
	}
	
	private Handler<AcDecide> handleAcDecide = new Handler<AcDecide>() {

		@Override
		public void handle(AcDecide event) {
			logger.debug("Received AcDecide");
			int id = event.getId();
			int result = event.getVal();
			
			if(result != Integer.MIN_VALUE) {
				trigger(new BebBroadcast(new Decided(id, result)), beb);
			} else {
				//proposed.put(id, false);
				proposal.put(id, result);
				//tryPropose(id);
			}
		}
		
	};
	
	
	private Handler<Decided> handleDecided = new Handler<Decided>() {

		@Override
		public void handle(Decided event) {
			int id = event.getId();
			int v = event.getResult();
			
			initInstance(id);
			
			if(decided.get(id) == null || !decided.get(id)) {
				decided.put(id, true);
				trigger(new UcDecide(id, v), uc);
				// Added Garbage Collect
				//garbageCollect(id);
			}
		}
		
	};
}
