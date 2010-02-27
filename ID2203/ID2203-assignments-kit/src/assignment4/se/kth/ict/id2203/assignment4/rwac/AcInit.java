package se.kth.ict.id2203.assignment4.rwac;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class AcInit extends Init {

	private final Topology topology;
	public AcInit(Topology topology) {
		this.topology = topology;
	}
	public Topology getTopology() {
		return topology;
	}

}
