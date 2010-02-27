package se.kth.ict.id2203.assignment4.rwac;

import se.sics.kompics.PortType;

public class AcLink extends PortType {

	{
		positive(AcDecide.class);
		negative(AcPropose.class);
	}
}
