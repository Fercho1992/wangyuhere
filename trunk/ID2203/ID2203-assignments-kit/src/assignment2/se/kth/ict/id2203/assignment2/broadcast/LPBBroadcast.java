package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Event;

public class LPBBroadcast extends Event {

	private final String message;
	
	public LPBBroadcast(String m) {
		super();
		message = m;
	}

	public String getMessage() {
		return message;
	}
	
	
}
