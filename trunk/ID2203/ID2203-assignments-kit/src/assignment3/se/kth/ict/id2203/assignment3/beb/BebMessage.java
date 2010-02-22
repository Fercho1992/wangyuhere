package se.kth.ict.id2203.assignment3.beb;

import se.kth.ict.id2203.assignment3.atomicRegister.WriteMessage;
import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class BebMessage extends Pp2pDeliver {

	private final WriteMessage data;
	
	protected BebMessage(Address source, WriteMessage d) {
		super(source);
		data = d;
	}

	
	
	public WriteMessage getData() {
		return data;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587428848638781685L;

}
