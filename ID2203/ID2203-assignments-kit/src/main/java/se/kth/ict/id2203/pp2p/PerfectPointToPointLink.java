package se.kth.ict.id2203.pp2p;

import se.sics.kompics.PortType;

public final class PerfectPointToPointLink extends PortType {
	{
		positive(Pp2pDeliver.class);
		negative(Pp2pSend.class);
	}
}
