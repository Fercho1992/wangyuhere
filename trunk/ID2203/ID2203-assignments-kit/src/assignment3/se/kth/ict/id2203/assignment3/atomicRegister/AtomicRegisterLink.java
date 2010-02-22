package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.PortType;

public class AtomicRegisterLink extends PortType {

	{
		positive(ReadResponse.class);
		positive(WriteResponse.class);
		negative(ReadRequest.class);
		negative(WriteRequest.class);
	}
}
