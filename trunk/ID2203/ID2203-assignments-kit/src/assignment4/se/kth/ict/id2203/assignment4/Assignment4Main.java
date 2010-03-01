package se.kth.ict.id2203.assignment4;

import java.util.Set;

import org.apache.log4j.PropertyConfigurator;

import se.kth.ict.id2203.assignment3.beb.Beb;
import se.kth.ict.id2203.assignment3.beb.BebInit;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.assignment4.application.Application4;
import se.kth.ict.id2203.assignment4.application.Application4Init;
import se.kth.ict.id2203.assignment4.eld.ELD;
import se.kth.ict.id2203.assignment4.eld.EldInit;
import se.kth.ict.id2203.assignment4.eld.EldLink;
import se.kth.ict.id2203.assignment4.rwac.AcInit;
import se.kth.ict.id2203.assignment4.rwac.AcLink;
import se.kth.ict.id2203.assignment4.rwac.RWAC;
import se.kth.ict.id2203.assignment4.uc.PUC;
import se.kth.ict.id2203.assignment4.uc.UcInit;
import se.kth.ict.id2203.assignment4.uc.UcLink;
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

public class Assignment4Main extends ComponentDefinition {
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

		Kompics.createAndStart(Assignment4Main.class);
	}

	/**
	 * Instantiates a new assignment0 group0.
	 */
	public Assignment4Main() {
		// create components
		Component time = create(JavaTimer.class);
		Component network = create(MinaNetwork.class);
		Component pp2p = create(DelayLink.class);
		Component beb = create(Beb.class);
		Component eld = create(ELD.class);
		Component rwac = create(RWAC.class);
		Component puc = create(PUC.class);
		Component app = create(Application4.class);

		// handle possible faults in the components
		subscribe(handleFault, time.getControl());
		subscribe(handleFault, network.getControl());
		subscribe(handleFault, pp2p.getControl());
		subscribe(handleFault, beb.getControl());
		subscribe(handleFault, eld.getControl());
		subscribe(handleFault, rwac.getControl());
		subscribe(handleFault, puc.getControl());
		subscribe(handleFault, app.getControl());

		// initialize the components
		Address self = topology.getSelfAddress();
		Set<Address> neighborSet = topology.getNeighbors(self);

		trigger(new MinaNetworkInit(self, 5), network.getControl());
		trigger(new DelayLinkInit(topology), pp2p.getControl());
		trigger(new BebInit(topology), beb.getControl());
		trigger(new EldInit(topology, Assignment4Executor.TIME_DELAY), eld.getControl());
		trigger(new AcInit(topology), rwac.getControl());
		trigger(new UcInit(topology), puc.getControl());
		trigger(new Application4Init(commandScript, neighborSet, self), app.getControl());

		// connect the components
		connect(app.getNegative(Timer.class), time.getPositive(Timer.class));
		connect(app.getNegative(UcLink.class), puc
				.getPositive(UcLink.class));

		connect(puc.getNegative(BebLink.class), beb.getPositive(BebLink.class));
		connect(puc.getNegative(AcLink.class), rwac.getPositive(AcLink.class));
		connect(puc.getNegative(EldLink.class), eld
				.getPositive(EldLink.class));
		
		connect(rwac.getNegative(BebLink.class), beb.getPositive(BebLink.class));
		connect(rwac.getNegative(PerfectPointToPointLink.class), pp2p.getPositive(PerfectPointToPointLink.class));
		

		connect(beb.getNegative(PerfectPointToPointLink.class), pp2p
				.getPositive(PerfectPointToPointLink.class));
		
		connect(eld.getNegative(PerfectPointToPointLink.class), pp2p
				.getPositive(PerfectPointToPointLink.class));
		connect(eld.getNegative(Timer.class), time.getPositive(Timer.class));

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
