package se.kth.ict.id2203.assignment4.rwac;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class AcDecide extends Event {

	private final Address id;
	private final int val;
	
	public AcDecide(Address id, int val) {
		this.id = id;
		this.val = val;
	}

	public Address getId() {
		return id;
	}

	public int getVal() {
		return val;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n========AcDecide=========\n");
		sb.append("id = " + id + "\n");
		sb.append("val = " + val + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}
}
