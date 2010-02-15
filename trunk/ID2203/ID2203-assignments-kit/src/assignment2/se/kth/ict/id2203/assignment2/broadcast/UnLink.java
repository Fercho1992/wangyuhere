package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.PortType;

public class UnLink extends PortType {

	{
		positive(UnDeliver.class);
		negative(UnBroadcast.class);
	}
}
