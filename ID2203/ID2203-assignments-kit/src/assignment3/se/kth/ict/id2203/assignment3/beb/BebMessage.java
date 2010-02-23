package se.kth.ict.id2203.assignment3.beb;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class BebMessage extends Pp2pDeliver {

	private final BebDeliver data;
	
	protected BebMessage(Address source, BebDeliver d) {
		super(source);
		data = d;
	}

	
	
	public BebDeliver getData() {
		return data;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587428848638781685L;

}
