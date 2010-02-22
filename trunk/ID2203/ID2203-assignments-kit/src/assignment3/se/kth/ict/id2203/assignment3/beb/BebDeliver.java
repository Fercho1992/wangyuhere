package se.kth.ict.id2203.assignment3.beb;

import se.kth.ict.id2203.assignment3.atomicRegister.WriteMessage;
import se.sics.kompics.Event;

public class BebDeliver extends Event {

	private final WriteMessage message;
	
	public BebDeliver(WriteMessage m) {
		super();
		message = m;
	}

	public WriteMessage getMessage() {
		return message;
	}
	
}
