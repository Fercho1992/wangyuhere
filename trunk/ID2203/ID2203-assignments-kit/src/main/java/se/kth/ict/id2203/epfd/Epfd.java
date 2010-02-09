package se.kth.ict.id2203.epfd;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.epfd.EpfdInit.LinkType;
import se.kth.ict.id2203.flp2p.FairLossPointToPointLink;
import se.kth.ict.id2203.flp2p.Flp2pSend;
import se.kth.ict.id2203.pfd.CheckTimeout;
import se.kth.ict.id2203.pfd.HeartbeatMessage;
import se.kth.ict.id2203.pfd.HeartbeatTimeout;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;
import se.sics.kompics.launch.Topology;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class Epfd extends ComponentDefinition {

	Negative<EpfdLink> epfd = negative(EpfdLink.class);
	
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	Positive<FairLossPointToPointLink> flp2p = positive(FairLossPointToPointLink.class);
	Positive<Timer> timer = positive(Timer.class);
	
	private static int HEARTBEAT = 0;
	private static int CHECK = 1;
	
	private static final Logger logger = LoggerFactory.getLogger(Epfd.class);
	
	private Address self;
	private Topology topology;
	private int timeDelay;
	private int delta;
	private int period;
	private Set<Address> alive;
	private Set<Address> suspected;
	private Set<Address> all;
	private LinkType type;
	
	public Epfd() {
		subscribe(handleInit, control);
		subscribe(handleHeartbeatTimeout, timer);
		subscribe(handleCheckTimeout, timer);
		subscribe(handleHeartbeatMessage, pp2p);
		subscribe(handleFlp2pHeartbeatMessage, flp2p);
		subscribe(handlePp2pDeliver, pp2p);
		subscribe(handlePp2pSend, epfd);
	}
	
	private Handler<EpfdInit> handleInit = new Handler<EpfdInit>() {

		@Override
		public void handle(EpfdInit event) {
			topology = event.getTopology();
			timeDelay = event.getTimeDelay();
			delta = event.getDelta();
			type = event.getType();
			period = timeDelay;
			self = topology.getSelfAddress();
			
			all = topology.getAllAddresses();
			alive = new LinkedHashSet<Address>();
			alive.addAll(all);
			suspected = new LinkedHashSet<Address>();
			
			logger.info("Timedelay={}, Delta={}", timeDelay, delta);
			
			startTimer(timeDelay, HEARTBEAT);
			startTimer(period, CHECK);
		}
		
	};
	
	private Handler<Pp2pSend> handlePp2pSend = new Handler<Pp2pSend>() {

		@Override
		public void handle(Pp2pSend event) {
			trigger(event, pp2p);
		}
		
	};
	
	private Handler<HeartbeatTimeout> handleHeartbeatTimeout = new Handler<HeartbeatTimeout>() {

		@Override
		public void handle(HeartbeatTimeout event) {
			for (Address a : all) {
				if(type == LinkType.Pp2p) {
					trigger(new Pp2pSend(a, new HeartbeatMessage(self)), pp2p);
				} else {
					//logger.info("Send flp2p to "+a.getId());
					trigger(new Flp2pSend(a, new Flp2pHeartbeatMessage(self)), flp2p);
				}
			}
			startTimer(timeDelay, HEARTBEAT);
		}
		
	};
	
	private Handler<CheckTimeout> handleCheckTimeout = new Handler<CheckTimeout>() {

		@Override
		public void handle(CheckTimeout event) {
			if(hasIntersection(alive, suspected)) {
				period += delta;
				logger.info("Increase period by "+delta+" and current period is " + period);
			}
			for(Address a : all) {
				if(!alive.contains(a) && !suspected.contains(a)) {
					suspected.add(a);
					trigger(new Suspect(a, period), epfd);
				} else if(alive.contains(a) && suspected.contains(a)) {
					suspected.remove(a);
					trigger(new Restore(a, period), epfd);
				}
			}
			alive.clear();
			startTimer(period, CHECK);
		}
		
	};
	
	private Handler<Flp2pHeartbeatMessage> handleFlp2pHeartbeatMessage = new Handler<Flp2pHeartbeatMessage>() {

		@Override
		public void handle(Flp2pHeartbeatMessage event) {
			//logger.info("Receiving heartbeat from "+event.getSource().getId());
			alive.add(event.getSource());
		}
		
	};
	
	private Handler<HeartbeatMessage> handleHeartbeatMessage = new Handler<HeartbeatMessage>() {

		@Override
		public void handle(HeartbeatMessage event) {
			//logger.info("Receiving heartbeat from "+event.getSource().getId());
			alive.add(event.getSource());
		}
		
	};
	
	private Handler<Pp2pDeliver> handlePp2pDeliver = new Handler<Pp2pDeliver>() {

		@Override
		public void handle(Pp2pDeliver event) {
			trigger(event, epfd);
		}
		
	};
	
	private void startTimer(int time,  final int type) {
		ScheduleTimeout st = new ScheduleTimeout(time);
		if(type == CHECK) {
			st.setTimeoutEvent(new CheckTimeout(st));
		} else {
			st.setTimeoutEvent(new HeartbeatTimeout(st));
		}
		trigger(st, timer);
	}
	
	private boolean hasIntersection(Set<Address> alive2, Set<Address> suspected2) {
		for(Address a : alive2) {
			if(suspected2.contains(a)) return true;
		}
		return false;
	}
	
}
