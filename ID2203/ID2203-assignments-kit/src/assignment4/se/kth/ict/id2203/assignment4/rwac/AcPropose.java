package se.kth.ict.id2203.assignment4.rwac;

import se.sics.kompics.Event;

public class AcPropose extends Event {

	private final int id;
	private final int value;
	
	public AcPropose(int id, int v) {
		this.id = id;
		this.value = v;
	}

	public int getId() {
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
