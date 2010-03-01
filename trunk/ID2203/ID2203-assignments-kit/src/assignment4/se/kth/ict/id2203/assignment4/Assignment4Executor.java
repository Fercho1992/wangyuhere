package se.kth.ict.id2203.assignment4;

import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

public class Assignment4Executor {

	public static int TIME_DELAY = 100;
	public static void main(String[] args) {
		Topology topology1 = new Topology() {
			private static final long serialVersionUID = -1474793335689789017L;

			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);

				defaultLinks(100, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment4Main.class) {
			{
				command(1, "D3000");
				command(2, "D1000:P1-1");
				command(3, "D1000");
			}
		};
		scenario1.executeOn(topology1);
		
	}
}
