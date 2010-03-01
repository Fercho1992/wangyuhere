package se.kth.ict.id2203.assignment4.uc;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.sics.kompics.address.Address;

public class Decided extends BebDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7078384200006359551L;
	private final Address id;
	private final int result;
	
	public Decided(Address id, int result) {
		this.id = id;
		this.result = result;
	}

	public Address getId() {
		return id;
	}

	public int getResult() {
		return result;
	}
	
}
