package se.sics.kompics.p2p.simulator.launch;

import se.sics.kompics.p2p.experiment.dsl.SimulationScenario;

@SuppressWarnings("serial")
public class ScenarioQ6 extends Scenario {
	private static SimulationScenario scenario = new SimulationScenario() {{
		
		// process1 -> one peer join		
		StochasticProcess process1 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(1, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		// process2 -> 50 other peers join	
		StochasticProcess process2 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(511, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		// process3 -> 1000 lookups
		StochasticProcess process3 = new StochasticProcess() {{
			eventInterArrivalTime(constant(200));
			raise(1000, Operations.peerLookup, uniform(Configuration.Log2Ring));
		}};

		process1.start();
		process2.startAfterTerminationOf(2000, process1);
		process3.startAfterTerminationOf(400000, process2);
	}};
	
//-------------------------------------------------------------------
	public ScenarioQ6() {
		super(scenario);
	} 
}
