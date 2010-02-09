package se.kth.ict.id2203.epfd;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class EpfdInit extends Init {

	private final Topology topology;
	private final int timeDelay;
	private final int delta;
	
	public EpfdInit(Topology t, int time, int d) {
		topology = t;
		timeDelay = time;
		delta = d;
	}

	public Topology getTopology() {
		return topology;
	}

	public int getTimeDelay() {
		return timeDelay;
	}

	public int getDelta() {
		return delta;
	}
}
