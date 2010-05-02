package se.sics.kompics.p2p.simulator;

import java.math.BigInteger;
import se.sics.kompics.Event;

public final class PeerLookup extends Event {
	private final BigInteger id;

	public PeerLookup(BigInteger id) {
		this.id = id;
	}

	public BigInteger getID() {
		return this.id;
	}
}
