package se.kth.ict.id2203.assignment3.atomicRegister;

import java.io.Serializable;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.sics.kompics.address.Address;

public class WriteMessage extends BebDeliver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6178010430649566294L;
	
	private final int register;
	private final int reqid;
	private final int ts;
	private final int rank;
	private final int value;
	private final Address source;
	
	public WriteMessage(Address source, int r, int i, int j, int k, int l) {
		super();
		this.source = source;
		register = r;
		reqid = i;
		ts = j;
		rank = k;
		value = l;
	}

	public int getRegister() {
		return register;
	}

	public int getReqid() {
		return reqid;
	}

	public int getTs() {
		return ts;
	}

	public int getRank() {
		return rank;
	}

	public int getValue() {
		return value;
	}
	
	
	public Address getSource() {
		return source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======WriteMessage=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("Register = " + register + "\n");
		sb.append("Reqid = " + reqid + "\n");
		sb.append("Ts = " + ts + "\n");
		sb.append("Rank = " + rank + "\n");
		sb.append("Value = " + value + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}
	

}
