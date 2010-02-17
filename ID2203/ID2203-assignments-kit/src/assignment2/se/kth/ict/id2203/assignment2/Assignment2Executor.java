package se.kth.ict.id2203.assignment2;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

@SuppressWarnings("serial")
public class Assignment2Executor {

	public static int fanout = 3;
	public static int maxrounds = 3;
	public static double storeThreshold = 0.3;
	public static int timeDelay = 5000;
	
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

				defaultLinks(500, 0.6);
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
		
		Topology topology2 = new Topology() {
			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);
				node(4, "127.0.0.1", 22034);
				node(5, "127.0.0.1", 22035);
				node(6, "127.0.0.1", 22036);
				node(7, "127.0.0.1", 22037);
				node(8, "127.0.0.1", 22038);

				defaultLinks(500, 0.5);
			}
		};

		Scenario scenario2 = new Scenario(Assignment2Main.class) {
			{
				command(1, "");
				command(2, "");
				command(3, "");
				command(4, "");
				command(5, "");
				command(6, "");
				command(7, "");
				command(8, "");
			}
		};
		//scenario2.executeOn(topology2);
	}

}
