package se.kth.ict.id2203.assignment1;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

@SuppressWarnings("serial")
public class Assignment1bExecutor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Topology topology1 = new Topology() {
			{
				node(1, "127.0.0.1", 25031);
				node(2, "127.0.0.1", 25032);
				node(3, "127.0.0.1", 25033);
				node(4, "127.0.0.1", 25034);

				// link(1, 2, 3000, 0.5).bidirectional();
				// link(1, 2, 3000, 0.5);
				// link(2, 1, 3000, 0.5);
				// link(3, 2, 3000, 0.5);
				// link(4, 2, 3000, 0.5);
				defaultLinks(5000, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment1bMain.class) {
			{
				command(1, "S500");
				command(2, "S500");
				command(3, "S500");
				command(4, "S500");
			}
		};

		scenario1.executeOn(topology1);

	}

}
