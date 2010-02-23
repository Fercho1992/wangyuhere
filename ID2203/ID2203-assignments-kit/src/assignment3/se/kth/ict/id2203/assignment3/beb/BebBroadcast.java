package se.kth.ict.id2203.assignment3.beb;

import se.sics.kompics.Event;

public class BebBroadcast extends Event {

	private final BebDeliver message;
	
	public BebBroadcast(BebDeliver m) {
		super();
		message = m;
	}

	public BebDeliver getMessage() {
		return message;
	}
	
}
