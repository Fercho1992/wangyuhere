package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.Event;

public class ReadRequest extends Event {

	private final int register;
	
	public ReadRequest(int r) {
		register = r;
	}

	public int getRegister() {
		return register;
	}
	
	
}
