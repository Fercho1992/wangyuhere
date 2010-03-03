package se.kth.ict.id2203.assignment4.uc;

import se.sics.kompics.Event;

public class UcDecide extends Event {

	private final int id;
	private final int val;
	
	public UcDecide(int id, int val) {
		this.id = id;
		this.val = val;
	}

	public int getId() {
		return id;
	}

	public int getVal() {
		return val;
	}
	
	
}
