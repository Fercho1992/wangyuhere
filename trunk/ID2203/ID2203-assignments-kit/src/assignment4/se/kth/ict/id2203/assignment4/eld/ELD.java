package se.kth.ict.id2203.assignment4.eld;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class ELD extends ComponentDefinition {

	public static final int DELTA = 200;
	
	private static final Logger logger = LoggerFactory
	.getLogger(ELD.class);
	
	
	Negative<EldLink> eld = negative(EldLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	Positive<Timer> timer = positive(Timer.class);
	
	
	
	private Address leader;
	private Set<Address> all;
	private Address self;
	private int period;
	private Set<Address> candidateset;
	
	public ELD() {
		subscribe(handleEldInit, control);
		subscribe(handleEldTimeout, timer);
		subscribe(handleHeartbeat, pp2p);
	}
	
	private Handler<EldInit> handleEldInit = new Handler<EldInit>() {

		@Override
		public void handle(EldInit event) {
			all = event.getTopology().getAllAddresses();
			self = event.getTopology().getSelfAddress();
			leader = select(all);
			trigger(new Trust(leader), eld);
			for(Address a : all) {
				trigger(new Pp2pSend(a, new Heartbeat(self)), pp2p);
			}
			candidateset = new LinkedHashSet<Address>();
			period = event.getTimeDelay();
			startTimer(period);
			logger.debug("ELD initialized!");
			logger.debug("ELD select the leader:" + leader.toString());
		}
		
	};
	
	// The node with lowest ID is the leader
	private Address select(Set<Address> all) {
		Address l = null;
		for(Address a : all) {
			if(l == null) {
				l = a;
			} else {
				if(a.getId() < l.getId()) {
					l = a;
				}
			}
		}
		return l;
	}
	
	private void startTimer(int time) {
		ScheduleTimeout st = new ScheduleTimeout(time);
		st.setTimeoutEvent(new EldTimeout(st));
		trigger(st, timer);
		
		logger.trace("ELD timer started for "+time);
	}
	
	private Handler<EldTimeout> handleEldTimeout = new Handler<EldTimeout>() {

		@Override
		public void handle(EldTimeout event) {
			Address newleader = select(candidateset);
			if(newleader != null && !leader.equals(newleader)) {
				period = period + DELTA;
				leader = newleader;
				trigger(new Trust(leader), eld);
				
				logger.debug("ELD select new leader "+leader.toString());
			}
			for(Address a : all) {
				trigger(new Pp2pSend(a, new Heartbeat(self)), pp2p);
			}
			candidateset.clear();
			startTimer(period);
		}
		
	};
	
	private Handler<Heartbeat> handleHeartbeat = new Handler<Heartbeat>() {

		@Override
		public void handle(Heartbeat event) {
			candidateset.add(event.getSource());
			logger.trace("ELD got heartbeat and added " + event.getSource());
		}
		
	};
}
