package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Event;

public class UnBroadcast extends Event {

	private final DataMessage data;
	
	public UnBroadcast(DataMessage d) {
		super();
		data = d;
	}

	public DataMessage getData() {
		return data;
	}
	
}
