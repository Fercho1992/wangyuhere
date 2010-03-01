package se.kth.ict.id2203.assignment4.uc;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class UcDecide extends Event {

	private final Address id;
	private final int val;
	
	public UcDecide(Address id, int val) {
		this.id = id;
		this.val = val;
	}

	public Address getId() {
		return id;
	}

	public int getVal() {
		return val;
	}
	
	
}
