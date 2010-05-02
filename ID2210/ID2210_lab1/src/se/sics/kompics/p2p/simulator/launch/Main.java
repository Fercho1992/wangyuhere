package se.sics.kompics.p2p.simulator.launch;

public class Main {
	public static void main(String[] args) throws Throwable {
		Configuration.STABALIZATION_PERIOD = 1000;
		Configuration configuration = new Configuration();
		configuration.set();
		
		//Scenario scenario = new SampleScenario();
		//Scenario scenario = new ScenarioQ1();
		//Scenario scenario = new ScenarioQ2();
		Scenario scenario = new ScenarioQ4();
		//Scenario scenario = new ScenarioQ6();
		scenario.setSeed(System.currentTimeMillis());
		scenario.simulate();
	}
}
