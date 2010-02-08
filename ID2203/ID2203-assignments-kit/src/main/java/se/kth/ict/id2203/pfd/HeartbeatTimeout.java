package se.kth.ict.id2203.pfd;

import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timeout;

public class HeartbeatTimeout extends Timeout {

	protected HeartbeatTimeout(ScheduleTimeout request) {
		super(request);
	}

}
