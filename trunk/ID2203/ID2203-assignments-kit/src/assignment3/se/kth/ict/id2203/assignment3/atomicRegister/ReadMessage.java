package se.kth.ict.id2203.assignment3.atomicRegister;

import java.io.Serializable;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.sics.kompics.address.Address;

public class ReadMessage extends BebDeliver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5599405263292686100L;
	private final int register;
	private final int reqid;
	private final Address source;
	
	public ReadMessage(Address s, int r, int id) {
		super();
		source = s;
		register = r;
		reqid = id;
	}

	public int getRegister() {
		return register;
	}

	public int getReqid() {
		return reqid;
	}

	public Address getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======ReadMessage=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("Register = " + register + "\n");
		sb.append("Reqid = " + reqid + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}
	
}
