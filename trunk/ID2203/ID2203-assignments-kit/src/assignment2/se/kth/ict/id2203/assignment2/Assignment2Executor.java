package se.kth.ict.id2203.assignment2;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

@SuppressWarnings("serial")
public class Assignment2Executor {

	public static int fanout = 2;
	public static int maxrounds = 3;
	public static double storeThreshold = 0;
	public static int timeDelay = 3000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Topology topology1 = new Topology() {
			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);
				node(4, "127.0.0.1", 22034);

				defaultLinks(500, 0.5);
			}
		};

		Scenario scenario1 = new Scenario(Assignment2Main.class) {
			{
				command(1, "");
				command(2, "");
				command(3, "");
				command(4, "");
			}
		};

		scenario1.executeOn(topology1);
	}

}
