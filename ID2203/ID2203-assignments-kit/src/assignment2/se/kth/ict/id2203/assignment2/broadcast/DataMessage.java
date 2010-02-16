package se.kth.ict.id2203.assignment2.broadcast;

import se.kth.ict.id2203.flp2p.Flp2pDeliver;
import se.sics.kompics.address.Address;

public class DataMessage extends Flp2pDeliver {

	private final String message;
	private final int snm;
	private final Address sm;
	
	protected DataMessage(Address source, String m, int s) {
		super(source);
		this.sm =source;
		message = m;
		snm = s;
	}
	
	public Address getSm() {
		return sm;
	}

	public String getMessage() {
		return message;
	}


	public int getSnm() {
		return snm;
	}
	
	@Override
	public String toString() {
		return String.format("[%s,%s,%d]", sm, message, snm);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2423061419439139131L;

}
