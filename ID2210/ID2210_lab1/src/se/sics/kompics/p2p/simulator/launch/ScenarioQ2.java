package se.sics.kompics.p2p.simulator.launch;

import se.sics.kompics.p2p.experiment.dsl.SimulationScenario;

@SuppressWarnings("serial")
public class ScenarioQ2 extends Scenario {
	private static SimulationScenario scenario = new SimulationScenario() {{
		
		// process1 -> one peer join		
		StochasticProcess process1 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(1, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		// process2 -> 50 other peers join	
		StochasticProcess process2 = new StochasticProcess() {{
			eventInterArrivalTime(constant(100));
			raise(49, Operations.peerJoin, uniform(Configuration.Log2Ring));
		}};

		// process3 -> churn in the system. 5 peers join and 5 peers fail.
		StochasticProcess process3 = new StochasticProcess() {{
			eventInterArrivalTime(exponential(500));
			raise(70, Operations.peerJoin, uniform(Configuration.Log2Ring));
			raise(30, Operations.peerFail, uniform(Configuration.Log2Ring));
		}};

		// process4 -> 100 lookups
		StochasticProcess process4 = new StochasticProcess() {{
			eventInterArrivalTime(constant(3000));
			raise(100, Operations.peerLookup, uniform(Configuration.Log2Ring));
		}};

		process1.start();
		process2.startAfterTerminationOf(2000, process1);
		process3.startAfterTerminationOf(80000, process2);
		process4.startAfterTerminationOf(80000, process2);
	}};
	
//-------------------------------------------------------------------
	public ScenarioQ2() {
		super(scenario);
	} 
}
