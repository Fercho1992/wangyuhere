package se.kth.ict.id2203.assignment4.rwac;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.sics.kompics.address.Address;

public class ReadMessage extends BebDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7142999876445001865L;
	private final Address source;
	private final int id;
	private final int tstamp;
	public ReadMessage(Address source, int id, Integer t) {
		this.source = source;
		this.id = id;
		tstamp = t;
	}
	
	public Address getSource() {
		return source;
	}

	public int getId() {
		return id;
	}
	public int getTstamp() {
		return tstamp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======ReadMessage=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("id = " + id + "\n");
		sb.append("tstamp = " + tstamp + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}

}
