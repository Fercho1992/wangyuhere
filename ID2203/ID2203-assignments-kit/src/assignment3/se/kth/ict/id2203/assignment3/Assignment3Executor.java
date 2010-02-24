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
		Topology topologyEx1Ex2 = new Topology() {
			private static final long serialVersionUID = -1474793335689789017L;

			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);

				defaultLinks(1000, 0);
			}
		};

		Scenario scenario1_1 = new Scenario(Assignment3aMain.class) {
			{
				command(1, "D30000");
				command(2, "D500:W4:D25000");
				command(3, "D10000:R");
			}
		};
		
		Scenario scenario1_2 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D30000");
				command(2, "D500:W4:D25000");
				command(3, "D10000:R");
			}
		};
		
		Scenario scenario2 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W5:R:D5000:R:D30000");
				command(2, "D500:W6:R:D5000:R:D30000");
				command(3, "D500:R:D500:R:D10000", 19000);
			}
		};
		
		Topology topologyEx3 = new Topology() {
			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);
				
				link(1, 2, 1000, 0).bidirectional();
				link(1, 3, 2000, 0).bidirectional();
				link(2, 3, 1750, 0).bidirectional();
			}
		};
		
		Scenario scenario3_123 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000");
				command(2, "D500:W2:R:D500:R:D8000", 100);
				command(3, "D500:W3:R:D500:R:D8000", 200);
			}
		};
		
		Scenario scenario3_132 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000");
				command(2, "D500:W2:R:D500:R:D8000", 200);
				command(3, "D500:W3:R:D500:R:D8000", 100);
			}
		};
		
		Scenario scenario3_213 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000", 100);
				command(2, "D500:W2:R:D500:R:D8000");
				command(3, "D500:W3:R:D500:R:D8000", 200);
			}
		};
		
		Scenario scenario3_231 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000", 200);
				command(2, "D500:W2:R:D500:R:D8000");
				command(3, "D500:W3:R:D500:R:D8000", 100);
			}
		};
		
		Scenario scenario3_312 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000", 100);
				command(2, "D500:W2:R:D500:R:D8000", 200);
				command(3, "D500:W3:R:D500:R:D8000");
			}
		};
		
		Scenario scenario3_321 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000", 200);
				command(2, "D500:W2:R:D500:R:D8000", 100);
				command(3, "D500:W3:R:D500:R:D8000");
			}
		};
		
		Scenario scenario4_1 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D3000:W1:R:D500:R:D8000");
				command(2, "D500:W2:R:D500:R:D8000", 100);
				command(3, "D500:W3:R:D500:R:D8000", 200);
			}
		};
		
		Scenario scenario4_2 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000");
				command(2, "D3000:W2:R:D500:R:D8000", 100);
				command(3, "D500:W3:R:D500:R:D8000", 200);
			}
		};
		
		Scenario scenario4_3 = new Scenario(Assignment3bMain.class) {
			{
				command(1, "D500:W1:R:D500:R:D8000");
				command(2, "D500:W2:R:D500:R:D8000", 100);
				command(3, "D3000:W3:R:D500:R:D8000", 200);
			}
		};
		
		Topology topologyEx4 = new Topology() {
			{
				node(1, "127.0.0.1", 22031);
				node(2, "127.0.0.1", 22032);
				node(3, "127.0.0.1", 22033);
				
				link(1, 2, 500, 0).bidirectional();
				link(1, 3, 500, 0).bidirectional();
				link(2, 3, 500, 0).bidirectional();
			}
		};

		// Exercise 1.1
		//scenario1_1.executeOn(topologyEx1Ex2);
		
		//Exercise 1.2
		//scenario1_2.executeOn(topologyEx1Ex2);
		
		//Exercise 2
		//scenario2.executeOn(topologyEx1Ex2);
		
		//Exercise 3
		//scenario3_123.executeOn(topologyEx3);
		//scenario3_132.executeOn(topologyEx3);
		//scenario3_213.executeOn(topologyEx3);
		//scenario3_231.executeOn(topologyEx3);
		//scenario3_312.executeOn(topologyEx3);
		//scenario3_321.executeOn(topologyEx3);
		
		//Exercise 4
		//scenario4_1.executeOn(topologyEx4);
		//scenario4_2.executeOn(topologyEx4);
		scenario4_3.executeOn(topologyEx4);
		
	}

}
