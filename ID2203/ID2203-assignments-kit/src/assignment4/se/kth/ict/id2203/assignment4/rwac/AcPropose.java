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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======AcPropose=========\n");
		sb.append("id = " + id + "\n");
		sb.append("value = " + value + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}
	
}
