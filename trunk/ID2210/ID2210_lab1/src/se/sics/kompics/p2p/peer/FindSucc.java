package se.sics.kompics.p2p.peer;

import java.math.BigInteger;

public class FindSucc extends PeerMessage {

	private static final long serialVersionUID = 1381490582457993230L;
	private final PeerAddress initiator;
	private final BigInteger id;
	private int fingerIndex;
	private boolean lookup;
	private int hopCount;

	public boolean isLookup() {
		return lookup;
	}

	public int getHopCount() {
		return hopCount;
	}

	// -------------------------------------------------------------------
	public FindSucc(PeerAddress source, PeerAddress destination,
			PeerAddress initiator, BigInteger id, int fingerIndex, int hopCount, boolean lookup) {
		super(source, destination);
		this.initiator = initiator;
		this.id = id;
		this.fingerIndex = fingerIndex;
		this.hopCount = hopCount;
		this.lookup = lookup;
	}

	// -------------------------------------------------------------------
	public PeerAddress getInitiator() {
		return this.initiator;
	}

	// -------------------------------------------------------------------
	public BigInteger getID() {
		return this.id;
	}

	// -------------------------------------------------------------------
	public int getFingerIndex() {
		return this.fingerIndex;
	}
}
