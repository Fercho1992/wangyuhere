package se.sics.kompics.p2p.peer;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import se.sics.kompics.Component;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;
import se.sics.kompics.network.Network;
import se.sics.kompics.p2p.bootstrap.BootstrapCompleted;
import se.sics.kompics.p2p.bootstrap.BootstrapRequest;
import se.sics.kompics.p2p.bootstrap.BootstrapResponse;
import se.sics.kompics.p2p.bootstrap.P2pBootstrap;
import se.sics.kompics.p2p.bootstrap.PeerEntry;
import se.sics.kompics.p2p.bootstrap.client.BootstrapClient;
import se.sics.kompics.p2p.bootstrap.client.BootstrapClientInit;
import se.sics.kompics.p2p.fd.FailureDetector;
import se.sics.kompics.p2p.fd.PeerFailureSuspicion;
import se.sics.kompics.p2p.fd.StartProbingPeer;
import se.sics.kompics.p2p.fd.StopProbingPeer;
import se.sics.kompics.p2p.fd.SuspicionStatus;
import se.sics.kompics.p2p.fd.ping.PingFailureDetector;
import se.sics.kompics.p2p.fd.ping.PingFailureDetectorInit;
import se.sics.kompics.p2p.simulator.snapshot.Snapshot;
import se.sics.kompics.timer.SchedulePeriodicTimeout;
import se.sics.kompics.timer.Timer;

public final class Peer extends ComponentDefinition {
	Negative<PeerPort> msPeerPort = negative(PeerPort.class);

	Positive<Network> network = positive(Network.class);
	Positive<Timer> timer = positive(Timer.class);

	private Component fd, bootstrap;
	
	private Random rand;
	private Address self;
	private PeerAddress peerSelf;
	private Vector<PeerAddress> friends;
	private int msgPeriod;
	private int viewSize;
	private boolean bootstrapped;
	
	private HashMap<Address, UUID> fdRequests;
	private HashMap<Address, PeerAddress> fdPeers;

//-------------------------------------------------------------------
	public Peer() {
		fdRequests = new HashMap<Address, UUID>();
		fdPeers = new HashMap<Address, PeerAddress>();
		rand = new Random(System.currentTimeMillis());

		fd = create(PingFailureDetector.class);
		bootstrap = create(BootstrapClient.class);
		
		connect(network, fd.getNegative(Network.class));
		connect(network, bootstrap.getNegative(Network.class));
		connect(timer, fd.getNegative(Timer.class));
		connect(timer, bootstrap.getNegative(Timer.class));
		
		subscribe(handleInit, control);
		subscribe(handleSendMessage, timer);
		subscribe(handleRecvMessage, network);
		subscribe(handleJoin, msPeerPort);
		subscribe(handleBootstrapResponse, bootstrap.getPositive(P2pBootstrap.class));
		subscribe(handlePeerFailureSuspicion, fd.getPositive(FailureDetector.class));
	}

//-------------------------------------------------------------------
// This handler initiates the Peer component.	
//-------------------------------------------------------------------
	Handler<PeerInit> handleInit = new Handler<PeerInit>() {
		public void handle(PeerInit init) {
			peerSelf = init.getMSPeerSelf();
			self = peerSelf.getPeerAddress();
			friends = new Vector<PeerAddress>();
			msgPeriod = init.getMSConfiguration().getSnapshotPeriod();

			viewSize = init.getMSConfiguration().getViewSize();

			trigger(new BootstrapClientInit(self, init.getBootstrapConfiguration()), bootstrap.getControl());
			trigger(new PingFailureDetectorInit(self, init.getFdConfiguration()), fd.getControl());
		}
	};

//-------------------------------------------------------------------
// Whenever a new node joins the system, this handler is triggered
// by the simulator.
// In this method the node sends a request to the bootstrap server
// to get a pre-defined number of existing nodes.
// You can change the number of requested nodes through peerConfiguration
// defined in Configuration.java.
// Here, the node adds itself to the Snapshot.
//-------------------------------------------------------------------
	Handler<JoinPeer> handleJoin = new Handler<JoinPeer>() {
		public void handle(JoinPeer event) {
			Snapshot.addPeer(peerSelf);
			BootstrapRequest request = new BootstrapRequest("Lab0", viewSize);
			trigger(request, bootstrap.getPositive(P2pBootstrap.class));			
		}
	};

//-------------------------------------------------------------------
// Whenever a node receives a response from the bootstrap server
// this handler is triggered.
// In this handler, the nodes adds the received list to its friend
// list and registers them in the failure detector.
// In addition, it sets a periodic scheduler to call the
// SendMessage handler periodically.	
//-------------------------------------------------------------------
	Handler<BootstrapResponse> handleBootstrapResponse = new Handler<BootstrapResponse>() {
		public void handle(BootstrapResponse event) {
			if (!bootstrapped) {
				bootstrapped = true;
				PeerAddress peer;
				Set<PeerEntry> somePeers = event.getPeers();

				for (PeerEntry peerEntry : somePeers) {
					peer = (PeerAddress)peerEntry.getOverlayAddress();
					friends.addElement(peer);
					fdRegister(peer);
				}
				
				trigger(new BootstrapCompleted("Lab0", peerSelf), bootstrap.getPositive(P2pBootstrap.class));
				Snapshot.addFriends(peerSelf, friends);
				
				SchedulePeriodicTimeout spt = new SchedulePeriodicTimeout(msgPeriod, msgPeriod);
				spt.setTimeoutEvent(new SendMessage(spt));
				trigger(spt, timer);
			}
		}
	};
	
//-------------------------------------------------------------------
// This handler is called periodically, every msgPeriod milliseconds.
//-------------------------------------------------------------------
	Handler<SendMessage> handleSendMessage = new Handler<SendMessage>() {
		public void handle(SendMessage event) {
			sendMessage();
		}
	};

//-------------------------------------------------------------------
// Whenever a node receives a PeerMessage from another node, this
// handler is triggered.
// In this handler the node, add the address of the sender and the
// address of another nodes, which has been sent by PeerMessage
// to its friend list, and updates its state in the Snapshot.
// The node registers the nodes added to its friend list and
// unregisters the node removed from the list.
//-------------------------------------------------------------------
	Handler<PeerMessage> handleRecvMessage = new Handler<PeerMessage>() {
		public void handle(PeerMessage event) {
			PeerAddress oldFriend;
			PeerAddress sender = event.getMSPeerSource();
			PeerAddress newFriend = event.getNewFriend();

			// add the sender address to the list of friends
			if (!friends.contains(sender)) {
				if (friends.size() == viewSize) {
					oldFriend = friends.get(rand.nextInt(viewSize));
					friends.remove(oldFriend);
					fdUnregister(oldFriend);
					Snapshot.removeFriend(peerSelf, oldFriend);
				}

				friends.addElement(sender);
				fdRegister(sender);
				Snapshot.addFriend(peerSelf, sender);
			}

			// add the received new friend from the sender to the list of friends
			if (!friends.contains(newFriend) && !peerSelf.equals(newFriend)) {
				if (friends.size() == viewSize) {
					oldFriend = friends.get(rand.nextInt(viewSize));
					friends.remove(oldFriend);
					fdUnregister(oldFriend);
					Snapshot.removeFriend(peerSelf, oldFriend);
				}

				friends.addElement(newFriend);
				fdRegister(newFriend);
				Snapshot.addFriend(peerSelf, newFriend);				
			}			
		}
	};
	
//-------------------------------------------------------------------	
// If a node has registered for another node, e.g. P, this handler
// is triggered if P fails.
//-------------------------------------------------------------------	
	Handler<PeerFailureSuspicion> handlePeerFailureSuspicion = new Handler<PeerFailureSuspicion>() {
		public void handle(PeerFailureSuspicion event) {
			Address suspectedPeerAddress = event.getPeerAddress();
			
			if (event.getSuspicionStatus().equals(SuspicionStatus.SUSPECTED)) {
				if (!fdPeers.containsKey(suspectedPeerAddress) || !fdRequests.containsKey(suspectedPeerAddress))
					return;
				
				PeerAddress suspectedPeer = fdPeers.get(suspectedPeerAddress);
				fdUnregister(suspectedPeer);
				
				friends.removeElement(suspectedPeer);
				System.out.println(peerSelf + " detects failure of " + suspectedPeer);
			}
		}
	};
	
//-------------------------------------------------------------------
// In this method a node selects a random node, e.g. randomDest,
// and sends it the address of another random node from its friend
// list, e.g. randomFriend.
//-------------------------------------------------------------------
	private void sendMessage() {
		if (friends.size() == 0)
			return;
		
		PeerAddress randomDest = friends.get(rand.nextInt(friends.size()));
		PeerAddress randomFriend = friends.get(rand.nextInt(friends.size()));
		
		if (randomFriend != null)
			trigger(new PeerMessage(peerSelf, randomDest, randomFriend), network);
	}
	
//-------------------------------------------------------------------
// This method shows how to register the failure detector for a node.
//-------------------------------------------------------------------
	private void fdRegister(PeerAddress peer) {
		Address peerAddress = peer.getPeerAddress();
		StartProbingPeer spp = new StartProbingPeer(peerAddress, peer);
		fdRequests.put(peerAddress, spp.getRequestId());
		trigger(spp, fd.getPositive(FailureDetector.class));
		
		fdPeers.put(peerAddress, peer);
	}

//-------------------------------------------------------------------	
// This method shows how to unregister the failure detector for a node.
//-------------------------------------------------------------------
	private void fdUnregister(PeerAddress peer) {
		if (peer == null)
			return;
			
		Address peerAddress = peer.getPeerAddress();
		trigger(new StopProbingPeer(peerAddress, fdRequests.get(peerAddress)), fd.getPositive(FailureDetector.class));
		fdRequests.remove(peerAddress);
		
		fdPeers.remove(peerAddress);
	}
}
