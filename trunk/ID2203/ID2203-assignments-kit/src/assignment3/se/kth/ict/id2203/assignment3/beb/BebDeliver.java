package se.kth.ict.id2203.assignment3.beb;

import se.sics.kompics.Event;

public class BebDeliver extends Event {

	private final BebMessage message;
	
	public BebDeliver() {
		message = null;
	}
	
	public BebDeliver(BebMessage m) {
		message = m;
	}

	public BebMessage getMessage() {
		return message;
	}
	
}
