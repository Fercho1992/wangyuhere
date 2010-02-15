package se.kth.ict.id2203.assignment2.broadcast;

import se.sics.kompics.Init;
import se.sics.kompics.launch.Topology;

public class LPBInit extends Init {

	private final Topology topology;
	private final int fanout;
	private final int maxrounds;
	private final double storeThreshold;
	private final int timeDelay;

	public LPBInit(Topology t, int f, int m, double s, int td) {
		topology = t;
		fanout = f;
		maxrounds = m;
		storeThreshold = s;
		timeDelay = td;
	}

	public Topology getTopology() {
		return topology;
	}

	public int getFanout() {
		return fanout;
	}

	public int getMaxrounds() {
		return maxrounds;
	}

	public double getStoreThreshold() {
		return storeThreshold;
	}

	public int getTimeDelay() {
		return timeDelay;
	}
	
	
}
