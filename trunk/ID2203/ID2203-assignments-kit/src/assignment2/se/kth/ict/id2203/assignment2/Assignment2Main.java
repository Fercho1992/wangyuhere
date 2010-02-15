package se.kth.ict.id2203.assignment2;

import java.util.Set;

import org.apache.log4j.PropertyConfigurator;

import se.kth.ict.id2203.assignment1.applicatoin.Application1bInit;
import se.kth.ict.id2203.assignment2.application.Application2;
import se.kth.ict.id2203.assignment2.broadcast.LPB;
import se.kth.ict.id2203.assignment2.broadcast.LPBInit;
import se.kth.ict.id2203.assignment2.broadcast.LPBLink;
import se.kth.ict.id2203.assignment2.broadcast.UnInit;
import se.kth.ict.id2203.assignment2.broadcast.UnLink;
import se.kth.ict.id2203.assignment2.broadcast.UnreliableBroadcast;
import se.kth.ict.id2203.flp2p.FairLossPointToPointLink;
import se.kth.ict.id2203.flp2p.delay.DelayDropLink;
import se.kth.ict.id2203.flp2p.delay.DelayDropLinkInit;
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

public class Assignment2Main extends ComponentDefinition {
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

		Kompics.createAndStart(Assignment2Main.class);
	}

	/**
	 * Instantiates a new assignment0 group0.
	 */
	public Assignment2Main() {
		// create components
		Component time = create(JavaTimer.class);
		Component network = create(MinaNetwork.class);
		Component flp2p = create(DelayDropLink.class);
		Component sub = create(UnreliableBroadcast.class);
		Component lpb = create(LPB.class);
		Component app = create(Application2.class);

		// handle possible faults in the components
		subscribe(handleFault, time.getControl());
		subscribe(handleFault, network.getControl());
		subscribe(handleFault, sub.getControl());
		subscribe(handleFault, flp2p.getControl());
		subscribe(handleFault, lpb.getControl());
		subscribe(handleFault, app.getControl());

		// initialize the components
		Address self = topology.getSelfAddress();
		Set<Address> neighborSet = topology.getNeighbors(self);

		trigger(new MinaNetworkInit(self, 5), network.getControl());
		trigger(new DelayDropLinkInit(topology, 0), flp2p.getControl());
		trigger(new UnInit(topology), sub.getControl());
		trigger(new LPBInit(topology, Assignment2Executor.fanout, Assignment2Executor.maxrounds, 
				Assignment2Executor.storeThreshold, Assignment2Executor.timeDelay), lpb.getControl());
		trigger(new Application1bInit(commandScript, neighborSet, self), app
				.getControl());

		// connect the components
		connect(app.getNegative(Timer.class), time.getPositive(Timer.class));
		connect(app.getNegative(LPBLink.class), lpb.getPositive(LPBLink.class));
		
		connect(lpb.getNegative(UnLink.class), sub.getPositive(UnLink.class));
		connect(lpb.getNegative(FairLossPointToPointLink.class), flp2p.getPositive(FairLossPointToPointLink.class));
		connect(lpb.getNegative(Timer.class), time.getPositive(Timer.class));
		
		connect(sub.getNegative(FairLossPointToPointLink.class), flp2p.getPositive(FairLossPointToPointLink.class));
		
		connect(flp2p.getNegative(Timer.class), time.getPositive(Timer.class));
		connect(flp2p.getNegative(Network.class), network
				.getPositive(Network.class));
	
	}

	Handler<Fault> handleFault = new Handler<Fault>() {
		public void handle(Fault fault) {
			fault.getFault().printStackTrace(System.err);
		}
	};
}
