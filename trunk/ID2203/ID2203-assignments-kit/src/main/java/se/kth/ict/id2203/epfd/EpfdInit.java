package se.kth.ict.id2203.epfd;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class EpfdInit extends Init {

	public enum LinkType {
		Flp2p,
		Pp2p
	}
	
	private final Topology topology;
	private final int timeDelay;
	private final int delta;
	private final LinkType type;
	
	public EpfdInit(Topology t, int time, int d) {
		topology = t;
		timeDelay = time;
		delta = d;
		this.type = LinkType.Pp2p;
	}
	public EpfdInit(Topology t, int time, int d, LinkType type) {
		topology = t;
		timeDelay = time;
		delta = d;
		this.type = type;
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

	public LinkType getType() {
		return type;
	}
	
}
