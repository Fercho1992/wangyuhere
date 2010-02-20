package se.kth.ict.id2203.assignment3.beb;

import se.sics.kompics.Event;

public class BebBroadcast extends Event {

	private final BebMessage message;
	
	public BebBroadcast(BebMessage m) {
		super();
		message = m;
	}

	public BebMessage getMessage() {
		return message;
	}
	
}
