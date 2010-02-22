package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.Event;

public class ReadResponse extends Event {

	private final int register;
	private final int value;
	
	public ReadResponse(int r, int v) {
		register = r;
		value = v;
	}

	public int getRegister() {
		return register;
	}

	public int getValue() {
		return value;
	}
	
	

}
