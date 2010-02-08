package se.kth.ict.id2203.pfd;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class CrashEvent extends Event {

	private Address crashNode;
	
	public CrashEvent(Address a) {
		this.crashNode = a;
	}
	
	public Address getCrashNode() {
		return crashNode;
	}
}
