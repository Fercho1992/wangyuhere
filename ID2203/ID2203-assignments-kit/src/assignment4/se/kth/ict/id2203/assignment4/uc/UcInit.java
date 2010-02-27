package se.kth.ict.id2203.assignment4.uc;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class UcInit extends Init {
	private final Topology topology;
	public UcInit(Topology topology) {
		this.topology = topology;
	}
	public Topology getTopology() {
		return topology;
	}
}
