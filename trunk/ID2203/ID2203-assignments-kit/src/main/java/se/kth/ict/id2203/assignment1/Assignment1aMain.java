package se.kth.ict.id2203.assignment1;

import java.util.Set;

import org.apache.log4j.PropertyConfigurator;

import se.kth.ict.id2203.assignment1.applicatoin.Application1a;
import se.kth.ict.id2203.assignment1.applicatoin.Application1aInit;
import se.kth.ict.id2203.pfd.PerfectFailureDetector;
import se.kth.ict.id2203.pfd.PfdInit;
import se.kth.ict.id2203.pfd.PfdLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.delay.DelayLink;
import se.kth.ict.id2203.pp2p.delay.DelayLinkInit;
import se.sics.kompics.Component;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Fault;
import se.sics.kompics.Handler;
import se.sics.kompics.Kompics;
import se.sics.kompics.address.Address;
import se.sics.kompics.launch.Topology;
import se.sics.kompics.network.Network;
import se.sics.kompics.network.mina.MinaNetwork;
import se.sics.kompics.network.mina.MinaNetworkInit;
import se.sics.kompics.timer.Timer;
import se.sics.kompics.timer.java.JavaTimer;

public class Assignment1aMain extends ComponentDefinition {
	static {
		PropertyConfigurator.configureAndWatch("log4j.properties");
	}
	private static int selfId;
	private static String commandScript;
	Topology topology = Topology.load(System.getProperty("topology"), selfId);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		selfId = Integer.parseInt(args[0]);
		commandScript = args[1];

		Kompics.createAndStart(Assignment1aMain.class);
	}

	/**
	 * Instantiates a new assignment0 group0.
	 */
	public Assignment1aMain() {
		// create components
		Component time = create(JavaTimer.class);
		Component network = create(MinaNetwork.class);
		Component pp2p = create(DelayLink.class);
		Component pfd = create(PerfectFailureDetector.class);
		Component app = create(Application1a.class);

		// handle possible faults in the components
		subscribe(handleFault, time.getControl());
		subscribe(handleFault, network.getControl());
		subscribe(handleFault, pp2p.getControl());
		subscribe(handleFault, pfd.getControl());
		subscribe(handleFault, app.getControl());

		// initialize the components
		Address self = topology.getSelfAddress();
		Set<Address> neighborSet = topology.getNeighbors(self);

		trigger(new MinaNetworkInit(self, 5), network.getControl());
		trigger(new DelayLinkInit(topology), pp2p.getControl());
		trigger(new PfdInit(topology, 1000, 1000), pfd.getControl());
		trigger(new Application1aInit(commandScript, neighborSet, self), app
				.getControl());

		// connect the components
		connect(app.getNegative(Timer.class), time.getPositive(Timer.class));
		connect(app.getNegative(PfdLink.class), pfd
				.getPositive(PfdLink.class));
		
		connect(pfd.getNegative(PerfectPointToPointLink.class), pp2p
				.getPositive(PerfectPointToPointLink.class));
		connect(pfd.getNegative(Timer.class), time.getPositive(Timer.class));
		
		
		connect(pp2p.getNegative(Timer.class), time.getPositive(Timer.class));
		connect(pp2p.getNegative(Network.class), network
				.getPositive(Network.class));
	
	}

	Handler<Fault> handleFault = new Handler<Fault>() {
		public void handle(Fault fault) {
			fault.getFault().printStackTrace(System.err);
		}
	};
}