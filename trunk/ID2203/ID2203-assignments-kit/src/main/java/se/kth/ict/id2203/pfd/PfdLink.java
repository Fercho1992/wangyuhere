package se.kth.ict.id2203.pfd;


import se.sics.kompics.PortType;

public class PfdLink extends PortType {
	{
		positive(CrashEvent.class);
		negative(null);
	}
}
