package se.kth.ict.id2203.epfd;

import se.kth.ict.id2203.flp2p.Flp2pDeliver;
import se.sics.kompics.address.Address;

public class Flp2pHeartbeatMessage extends Flp2pDeliver {

	protected Flp2pHeartbeatMessage(Address source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086548300873613010L;

}
