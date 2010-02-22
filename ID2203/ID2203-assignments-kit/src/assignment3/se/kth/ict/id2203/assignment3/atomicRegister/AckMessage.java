package se.kth.ict.id2203.assignment3.atomicRegister;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class AckMessage extends Pp2pDeliver {

	private final int register;
	private final int reqid;
	
	protected AckMessage(Address source, int r, int req) {
		super(source);
		register = r;
		reqid = req;
	}
	
	

	public int getRegister() {
		return register;
	}



	public int getReqid() {
		return reqid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======WriteMessage=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("Register = " + register + "\n");
		sb.append("Reqid = " + reqid + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -8318042245777968883L;

}
