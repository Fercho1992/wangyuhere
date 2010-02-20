package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.Event;

public class WriteRequest extends Event {

	private final String value;
	
	public WriteRequest(String n) {
		value = n;
	}

	public String getValue() {
		return value;
	}

}
