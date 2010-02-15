package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class LPBDeliver extends Event {

	final private Address source;
	final private String message;
	
	public LPBDeliver(Address s, String m) {
		source = s;
		message = m;
	}

	public Address getSource() {
		return source;
	}

	public String getMessage() {
		return message;
	}

}
