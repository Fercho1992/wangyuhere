package se.kth.ict.id2203.epfd;

import se.sics.kompics.Event;
import se.sics.kompics.address.Address;

public class Suspect extends Event {
	private Address node;
	private int period;

	public Suspect(Address a, int p) {
		this.node = a;
		this.period = p;
	}

	public Address getNode() {
		return node;
	}

	public void setNode(Address node) {
		this.node = node;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}
