package se.kth.ict.id2203.pfd;

import java.util.LinkedHashSet;
import java.util.Set;

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

public class PerfectFailureDetector extends ComponentDefinition {

	Negative<PfdLink> pfd = negative(PfdLink.class);
	
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	Positive<Timer> timer = positive(Timer.class);
	
	private static int HEARTBEAT = 0;
	private static int CHECK = 1;
	
	//private static final Logger logger = LoggerFactory.getLogger(PerfectFailureDetector.class);
	
	private Address self;
	private Topology topology;
	private int interval;
	private int bound_delay;
	private Set<Address> alive;
	private Set<Address> detected;
	private Set<Address> all;
	
	public PerfectFailureDetector() {
		subscribe(handleInit, control);
		subscribe(handleHeartbeatTimeout, timer);
		subscribe(handleCheckTimeout, timer);
		subscribe(handleHeartbeatMessage, pp2p);
		subscribe(handlePp2pDeliver, pp2p);
		subscribe(handlePp2pSend, pfd);
	}
	
	private Handler<PfdInit> handleInit = new Handler<PfdInit>() {

		@Override
		public void handle(PfdInit event) {
			topology = event.getTopology();
			interval = event.getInterval();
			bound_delay = event.getBound_delay();
			self = topology.getSelfAddress();
			
			all = topology.getAllAddresses();
			alive = new LinkedHashSet<Address>();
			detected = new LinkedHashSet<Address>();
			
			startTimer(interval, HEARTBEAT);
			startTimer(interval+bound_delay, CHECK);
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
				trigger(new Pp2pSend(a, new HeartbeatMessage(self)),
						pp2p);
			}
			startTimer(interval, HEARTBEAT);
		}
		
	};
	
	private Handler<CheckTimeout> handleCheckTimeout = new Handler<CheckTimeout>() {

		@Override
		public void handle(CheckTimeout event) {
			for(Address a : all)  {
				if(!alive.contains(a) && !detected.contains(a)) {
					detected.add(a);
					trigger(new CrashEvent(a), pfd);
				}
			}
			alive.clear();
			startTimer(interval+bound_delay, CHECK);
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
			trigger(event, pfd);
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
	
	
}
