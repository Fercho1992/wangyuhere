package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class UnDeliver extends Event {

	final private DataMessage data;
	final private Address source;
	
	public UnDeliver(Address s, DataMessage d) {
		super();
		source = s;
		data = d;
	}
	
	

	public Address getSource() {
		return source;
	}

	public DataMessage getData() {
		return data;
	}

}
