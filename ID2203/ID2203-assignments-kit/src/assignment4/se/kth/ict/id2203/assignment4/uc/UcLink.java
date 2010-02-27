package se.kth.ict.id2203.assignment4.uc;

import se.sics.kompics.PortType;

public class UcLink extends PortType {

	{
		positive(UcDecide.class);
		negative(UcPropose.class);
	}
}
