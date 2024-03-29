package se.sics.kompics.p2p.peer;

import se.sics.kompics.network.Message;

public class PeerMessage extends Message {
	private static final long serialVersionUID = -6815596147580962155L;
	private final PeerAddress source;
	private final PeerAddress destination;

	public PeerMessage(PeerAddress source, PeerAddress destination) {
		super(source.getPeerAddress(), destination.getPeerAddress());
		this.source = source;
		this.destination = destination;
	}

	public PeerAddress getMSPeerDestination() {
		return this.destination;
	}

	public PeerAddress getMSPeerSource() {
		return this.source;
	}
}