package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.Event;

public class WriteRequest extends Event {

	private final int register;
	private final int value;
	
	public WriteRequest(int r, int n) {
		register = r;
		value = n;
	}

	public int getValue() {
		return value;
	}

	public int getRegister() {
		return register;
	}
	
	

}
