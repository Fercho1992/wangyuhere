package se.sics.kompics.p2p.peer;

import se.sics.kompics.PortType;

public class PeerPort extends PortType {
	public PeerPort() {
		negative(JoinPeer.class);
		negative(LookupPeer.class);
	}
}
