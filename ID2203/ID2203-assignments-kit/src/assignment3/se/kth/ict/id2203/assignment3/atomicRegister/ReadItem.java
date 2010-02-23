package se.kth.ict.id2203.assignment3.atomicRegister;

import java.io.Serializable;

public class ReadItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7023951380566964980L;
	public ReadItem(int t, int rk, int val) {
		this.t = t;
		this.rk = rk;
		this.val = val;
	}
	public int t;
	public int rk;
	public int val;
}
