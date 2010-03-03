package se.kth.ict.id2203.assignment4.rwac;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class ReadAck extends Pp2pDeliver {

	private final int id;
	private final int wts;
	private final int val;
	private final int ts;
	
	protected ReadAck(Address source, int id, int wts, int val, int ts) {
		super(source);
		this.id = id;
		this.wts = wts;
		this.val = val;
		this.ts = ts;
	}
	
	

	public int getId() {
		return id;
	}



	public int getWts() {
		return wts;
	}



	public int getVal() {
		return val;
	}



	public int getTs() {
		return ts;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======ReadAck=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("id = " + id + "\n");
		sb.append("wts = " + wts + "\n");
		sb.append("val = " + val + "\n");
		sb.append("ts = " + ts + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 8284404406521539005L;

}
