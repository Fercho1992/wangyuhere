package se.kth.ict.id2203.pfd;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class HeartbeatMessage extends Pp2pDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5985465659375132267L;
	
	protected HeartbeatMessage(Address source) {
		super(source);
	}

}
