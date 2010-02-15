package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class UnInit extends Init {

	private final Topology topology;
	
	public UnInit(Topology t) {
		topology = t;
	}
	
	public Topology getTopology() {
		return topology;
	}
}
