package se.kth.ict.id2203.assignment3.atomicRegister;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class AckMessage extends Pp2pDeliver {

	protected AckMessage(Address source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8318042245777968883L;

}
