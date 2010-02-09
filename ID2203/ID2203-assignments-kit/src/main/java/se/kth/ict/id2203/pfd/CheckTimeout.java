package se.kth.ict.id2203.pfd;

import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timeout;

public class CheckTimeout extends Timeout {

	public CheckTimeout(ScheduleTimeout request) {
		super(request);
	}

}
