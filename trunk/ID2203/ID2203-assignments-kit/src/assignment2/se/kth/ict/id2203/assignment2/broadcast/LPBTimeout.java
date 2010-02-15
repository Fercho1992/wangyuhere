package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.address.Address;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timeout;

public class LPBTimeout extends Timeout {

	private final Address source;
	private final int sn;
	
	protected LPBTimeout(ScheduleTimeout request, Address s, int sn) {
		super(request);
		source = s;
		this.sn = sn;
	}

	public Address getSource() {
		return source;
	}

	public int getSn() {
		return sn;
	}

}
