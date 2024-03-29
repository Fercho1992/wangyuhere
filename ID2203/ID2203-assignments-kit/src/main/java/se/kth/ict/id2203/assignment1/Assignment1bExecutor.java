package se.kth.ict.id2203.assignment1;

import se.kth.ict.id2203.epfd.EpfdInit.LinkType;
import se.sics.kompics.launch.Scenario;
import se.sics.kompics.launch.Topology;

@SuppressWarnings("serial")
public class Assignment1bExecutor {

	public static LinkType LINKTYPE = LinkType.Flp2p;
	public static int TIMEDELAY = 1000;
	public static int DELTA = 500;
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

				defaultLinks(500, 0);
			}
		};

		Scenario scenario1 = new Scenario(Assignment1bMain.class) {
			{
				command(1, "S1000:X");
				command(2, "S6000:X");
				command(3, "S10000:X");
				command(4, "S500");
			}
		};
		
		Topology topology2 = new Topology() {
			{
				node(1, "127.0.0.1", 25031);
				node(2, "127.0.0.1", 25032);
				node(3, "127.0.0.1", 25033);
				node(4, "127.0.0.1", 25034);

				defaultLinks(3000, 0);
			}
		};
		
		Topology topology3 = new Topology() {
			{
				node(1, "127.0.0.1", 25031);
				node(2, "127.0.0.1", 25032);
				node(3, "127.0.0.1", 25033);
				node(4, "127.0.0.1", 25034);

				defaultLinks(500, 0.5);
			}
		};

		Scenario scenario2 = new Scenario(Assignment1bMain.class) {
			{
				command(1, "S500");
				command(2, "S500");
				command(3, "S500");
				command(4, "S500");
			}
		};
		
		Topology topology4 = new Topology() {
			{
				node(1, "127.0.0.1", 25031);
				node(2, "127.0.0.1", 25032);

				defaultLinks(500, 0);
			}
		};
		
		Scenario exercise4 = new Scenario(Assignment1bMain.class) {
			{
				command(1, "S2000:Phello:S2000:X").recover("R:PI am alive", 3000);
				command(2, "S2000");

			}
		};

		exercise4.executeOn(topology4);
		//scenario2.executeOn(topology3);
		//scenario2.executeOn(topology2);
		//scenario1.executeOn(topology1);

	}

}
