package se.kth.ict.id2203.pfd;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class PfdInit extends Init {
	private final Topology topology;
	private final int interval;
	private final int bound_delay;

	public PfdInit(Topology topology, int i, int bd) {
		this.topology = topology;
		interval = i;
		bound_delay = bd;
	}

	public final Topology getTopology() {
		return topology;
	}

	public int getInterval() {
		return interval;
	}

	public int getBound_delay() {
		return bound_delay;
	}
	
	
}
