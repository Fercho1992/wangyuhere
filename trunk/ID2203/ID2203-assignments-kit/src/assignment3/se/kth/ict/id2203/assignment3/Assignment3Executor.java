package se.kth.ict.id2203.assignment3;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

public class Assignment3Executor {

	public static int Internal = 1000;
	public static int BoundDelay = 1000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Topology topology1 = new Topology() {
			private static final long serialVersionUID = -1474793335689789017L;

			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);

				defaultLinks(1000, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment3aMain.class) {
			{
				command(1, "D30000");
				command(2, "D500:W4:D25000");
				command(3, "D10000:R");
			}
		};

		scenario1.executeOn(topology1);

	}

}
