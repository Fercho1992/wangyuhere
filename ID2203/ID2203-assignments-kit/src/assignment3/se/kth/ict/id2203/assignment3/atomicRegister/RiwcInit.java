package se.kth.ict.id2203.assignment3.atomicRegister;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class RiwcInit extends Init {

	final Topology topology;
	
	public RiwcInit(Topology t) {
		topology = t;
	}

	public Topology getTopology() {
		return topology;
	}
	
	
}
