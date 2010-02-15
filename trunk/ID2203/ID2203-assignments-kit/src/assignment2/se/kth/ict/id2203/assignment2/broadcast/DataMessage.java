package se.kth.ict.id2203.assignment2.broadcast;

import se.kth.ict.id2203.flp2p.Flp2pDeliver;
import se.sics.kompics.address.Address;

public class DataMessage extends Flp2pDeliver {

	private final String message;
	private final int sn;
	
	protected DataMessage(Address source, String m, int s) {
		super(source);
		message = m;
		sn = s;
	}
	
	

	public String getMessage() {
		return message;
	}


	public int getSn() {
		return sn;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2423061419439139131L;

}
