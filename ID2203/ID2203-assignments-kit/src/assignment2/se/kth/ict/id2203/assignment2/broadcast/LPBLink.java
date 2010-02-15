package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.PortType;

public class LPBLink extends PortType {

	{
		positive(LPBDeliver.class);
		negative(LPBBroadcast.class);
	}
}
