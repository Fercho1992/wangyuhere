package se.kth.ict.id2203.assignment4.rwac;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.sics.kompics.address.Address;

public class WriteMessage extends BebDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3183386845774064863L;
	private final Address source;
	private final int id;
	private final int ts;
	private final int val;
	
	public WriteMessage(Address source, int id, int ts, int val) {
		this.source = source;
		this.id = id;
		this.ts = ts;
		this.val = val;
	}

	public int getId() {
		return id;
	}

	public int getTs() {
		return ts;
	}

	public int getVal() {
		return val;
	}
	
	public Address getSource() {
		return source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======WriteMessage=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("id = " + id + "\n");
		sb.append("ts = " + ts + "\n");
		sb.append("val = " + val + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}
}
