package se.kth.ict.id2203.assignment4.eld;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class EldInit extends Init {

	private final Topology topology;
	private final int timeDelay;
	
	public EldInit(Topology topology, int delay) {
		this.topology = topology;
		timeDelay = delay;
	}
	public Topology getTopology() {
		return topology;
	}
	public int getTimeDelay() {
		return timeDelay;
	}

}
