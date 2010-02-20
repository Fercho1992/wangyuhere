package se.kth.ict.id2203.assignment3.beb;

import se.sics.kompics.PortType;

public class BebLink extends PortType {

	{
		positive(BebDeliver.class);
		negative(BebBroadcast.class);
	}
}
