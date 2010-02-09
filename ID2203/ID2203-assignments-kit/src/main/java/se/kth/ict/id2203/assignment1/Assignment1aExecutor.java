package se.kth.ict.id2203.assignment1;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

@SuppressWarnings("serial")
public class Assignment1aExecutor {

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

				defaultLinks(500, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment1aMain.class) {
			{
				command(1, "S1000:X");
				command(2, "S6000:X");
				command(3, "S10000:X");
				command(4, "S500");
			}
		};

		scenario1.executeOn(topology1);

	}

}
