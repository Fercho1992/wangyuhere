package se.kth.ict.id2203.assignment4.rwac;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class AcPropose extends Event {

	private final Address id;
	private final int value;
	
	public AcPropose(Address id, int v) {
		this.id = id;
		this.value = v;
	}

	public Address getId() {
		return id;
	}

	public int getValue() {
		return value;
	}
	
	
}
