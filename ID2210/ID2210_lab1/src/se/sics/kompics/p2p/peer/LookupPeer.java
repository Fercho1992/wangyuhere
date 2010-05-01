package se.sics.kompics.p2p.peer;

import java.math.BigInteger;
import se.sics.kompics.Event;

public class LookupPeer extends Event {
	private final BigInteger id;

	public LookupPeer(BigInteger id) {
		this.id = id;
	}

	public BigInteger getID() {
		return this.id;
	}
}
