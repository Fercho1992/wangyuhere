package se.kth.ict.id2203.assignment3.atomicRegister;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class ReadValueMessage extends Pp2pDeliver {

	private final int register;
	private final int reqid;
	private final int ts;
	private final int rank;
	private final int value;
	
	protected ReadValueMessage(Address source, int r, int i, int j, int k, int l) {
		super(source);
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 7450275478629639764L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======ReadValueMessage======\n");
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
