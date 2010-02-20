package se.kth.ict.id2203.assignment3.beb;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class BebInit extends Init {

	private final Topology topology;
	
	public BebInit(Topology t) {
		topology = t;
	}

	public Topology getTopology() {
		return topology;
	}
}
