package se.sics.kompics.p2p.simulator.launch;

import se.sics.kompics.p2p.experiment.dsl.SimulationScenario;

@SuppressWarnings("serial")
public class ScenarioQ1 extends Scenario {
	private static SimulationScenario scenario = new SimulationScenario() {{
		
		// process1 -> one peer join		
		StochasticProcess process1 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(1, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		// process2 -> 1990 other peers join	
		StochasticProcess process2 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(199, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		process1.start();
		process2.startAfterTerminationOf(2000, process1);
	}};
	
//-------------------------------------------------------------------
	public ScenarioQ1() {
		super(scenario);
	} 

}
