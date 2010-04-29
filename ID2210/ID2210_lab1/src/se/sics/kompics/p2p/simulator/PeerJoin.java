package se.sics.kompics.p2p.simulator;

import java.math.BigInteger;

import se.sics.kompics.Event;

public final class PeerJoin extends Event {
	private final BigInteger peerId;

//-------------------------------------------------------------------	
	public PeerJoin(BigInteger peerId) {
		this.peerId = peerId;
	}

//-------------------------------------------------------------------	
	public BigInteger getPeerId() {
		return peerId;
	}
}
