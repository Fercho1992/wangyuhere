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

				// link(1, 2, 3000, 0.5).bidirectional();
				// link(1, 2, 3000, 0.5);
				// link(2, 1, 3000, 0.5);
				// link(3, 2, 3000, 0.5);
				// link(4, 2, 3000, 0.5);
				defaultLinks(1000, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment1aMain.class) {
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
