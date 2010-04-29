package se.sics.kompics.p2p.peer;

import java.math.BigInteger;

public class FindSuccReply extends PeerMessage {

	private static final long serialVersionUID = 1381490582457993230L;
	private final BigInteger id;
	private final PeerAddress responsible;
	private int fingerIndex;
	private int hopCount;
	private boolean lookup;

	public int getHopCount() {
		return hopCount;
	}

	public boolean isLookup() {
		return lookup;
	}

	public BigInteger getId() {
		return id;
	}

	// -------------------------------------------------------------------
	public FindSuccReply(PeerAddress source, PeerAddress destination,
			PeerAddress responsible, BigInteger id, int fingerIndex, int hopCount, boolean lookup) {
		super(source, destination);
		this.responsible = responsible;
		this.fingerIndex = fingerIndex;
		this.hopCount = hopCount;
		this.lookup = lookup;
		this.id = id;
	}

	// -------------------------------------------------------------------
	public PeerAddress getResponsible() {
		return this.responsible;
	}

	// -------------------------------------------------------------------
	public int getFingerIndex() {
		return this.fingerIndex;
	}
}
