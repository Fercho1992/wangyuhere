package se.kth.ict.id2203.assignment4.uc;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;

public class Decided extends BebDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7078384200006359551L;
	private final int id;
	private final int result;
	
	public Decided(int id, int result) {
		this.id = id;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public int getResult() {
		return result;
	}
	
}
