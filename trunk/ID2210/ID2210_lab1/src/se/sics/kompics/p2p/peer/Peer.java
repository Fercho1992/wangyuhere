package se.sics.kompics.p2p.peer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

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
import se.sics.kompics.p2p.simulator.launch.Configuration;
import se.sics.kompics.p2p.simulator.snapshot.Snapshot;
import se.sics.kompics.timer.SchedulePeriodicTimeout;
import se.sics.kompics.timer.Timer;

public final class Peer extends ComponentDefinition {
	public static BigInteger RING_SIZE = new BigInteger("2")
			.pow(Configuration.Log2Ring);
	public static int FINGER_SIZE = Configuration.Log2Ring;
	public static int SUCC_SIZE = Configuration.Log2Ring;
	private static int WAIT_TIME_TO_REJOIN = 5;

	Negative<PeerPort> msPeerPort = negative(PeerPort.class);

	Positive<Network> network = positive(Network.class);
	Positive<Timer> timer = positive(Timer.class);
	private Component fd;
	private Component bootstrap;
	private Address self;
	private PeerAddress peerSelf;
	private PeerAddress pred;
	private PeerAddress succ;
	private PeerAddress[] fingers = new PeerAddress[FINGER_SIZE];
	private PeerAddress[] succList = new PeerAddress[SUCC_SIZE];

	private int fingerIndex = 0;
	private int joinCounter = 0;
	private boolean started = false;
	private boolean bootstrapped = false;
	private int stabilizePeriod;
	private HashMap<Address, UUID> fdRequests;
	private HashMap<Address, PeerAddress> fdPeers;
	Handler<PeerInit> handleInit = new Handler<PeerInit>() {
		public void handle(PeerInit init) {
			Peer.this.peerSelf = init.getMSPeerSelf();
			Peer.this.self = Peer.this.peerSelf.getPeerAddress();
			// TODO
			Peer.this.stabilizePeriod = 1000;

			Peer.this.trigger(new BootstrapClientInit(Peer.this.self, init
					.getBootstrapConfiguration()), Peer.this.bootstrap
					.getControl());
			Peer.this.trigger(new PingFailureDetectorInit(Peer.this.self, init
					.getFdConfiguration()), Peer.this.fd.getControl());
		}
	};

	Handler<JoinPeer> handleJoin = new Handler<JoinPeer>() {
		public void handle(JoinPeer event) {
			Snapshot.addPeer(Peer.this.peerSelf);
			BootstrapRequest request = new BootstrapRequest("chord", 1);
			Peer.this.trigger(request, Peer.this.bootstrap
					.getPositive(P2pBootstrap.class));
		}
	};

	Handler<BootstrapResponse> handleBootstrapResponse = new Handler<BootstrapResponse>() {
		public void handle(BootstrapResponse event) {
			if (!Peer.this.bootstrapped) {
				Peer.this.bootstrapped = true;
				Set<PeerEntry> somePeers = event.getPeers();

				if (somePeers.size() == 0) {
					Peer.this.pred = null;
					Peer.this.succ = Peer.this.peerSelf;
					Peer.this.succList[0] = succ;
					Snapshot.setPred(Peer.this.peerSelf, Peer.this.pred);
					Snapshot.setSucc(Peer.this.peerSelf, Peer.this.succ);
					Peer.this.joinCounter = -1;
					Peer.this.trigger(new BootstrapCompleted("chord",
							Peer.this.peerSelf), Peer.this.bootstrap
							.getPositive(P2pBootstrap.class));
				} else {
					Peer.this.pred = null;
					PeerAddress existingPeer = (PeerAddress) ((PeerEntry) somePeers
							.iterator().next()).getOverlayAddress();
					Peer.this.trigger(new FindSucc(Peer.this.peerSelf,
							existingPeer, Peer.this.peerSelf,
							Peer.this.peerSelf.getPeerId(), 0, 0, false),
							Peer.this.network);
					Snapshot.setPred(Peer.this.peerSelf, Peer.this.pred);
				}

				if (!Peer.this.started) {
					SchedulePeriodicTimeout spt = new SchedulePeriodicTimeout(
							Peer.this.stabilizePeriod,
							Peer.this.stabilizePeriod);
					spt.setTimeoutEvent(new PeriodicStabilization(spt));
					Peer.this.trigger(spt, Peer.this.timer);
					Peer.this.started = true;
				}
			}
		}
	};

	Handler<FindSucc> handleFindSucc = new Handler<FindSucc>() {
		public void handle(FindSucc event) {
			BigInteger id = event.getID();
			PeerAddress initiator = event.getInitiator();
			int fingerIndex = event.getFingerIndex();
			int hopCount = event.getHopCount();
			boolean lookup = event.isItLookup();

			if ((Peer.this.succ != null)
					&& (RingKey.belongsTo(id, Peer.this.peerSelf.getPeerId(),
							Peer.this.succ.getPeerId(),
							RingKey.IntervalBounds.OPEN_CLOSED, Peer.RING_SIZE))) {
				Peer.this.trigger(new FindSuccReply(Peer.this.peerSelf,
						initiator, Peer.this.succ, id, fingerIndex, hopCount,
						lookup), Peer.this.network);
			} else {
				PeerAddress nextPeer = Peer.this.closestPrecedingNode(id);
				Peer.this.trigger(new FindSucc(Peer.this.peerSelf, nextPeer,
						initiator, id, fingerIndex, hopCount + 1, lookup),
						Peer.this.network);
			}
		}
	};

	Handler<FindSuccReply> handleFindSuccReply = new Handler<FindSuccReply>() {
		public void handle(FindSuccReply event) {
			PeerAddress responsible = event.getResponsible();
			int fingerIndex = event.getFingerIndex();
			boolean lookup = event.isItLookup();

			if (!lookup) {
				if (fingerIndex == 0) {
					Peer.this.succ = new PeerAddress(responsible);
					Peer.this.succList[0] = new PeerAddress(responsible);
					Snapshot.setSucc(Peer.this.peerSelf, Peer.this.succ);
					Peer.this.trigger(new BootstrapCompleted("chord",
							Peer.this.peerSelf), Peer.this.bootstrap
							.getPositive(P2pBootstrap.class));
					Peer.this.fdRegister(Peer.this.succ);
					Peer.this.joinCounter = -1;
				}

				Peer.this.fingers[fingerIndex] = new PeerAddress(responsible);
				Snapshot.setFingers(Peer.this.peerSelf, Peer.this.fingers);
			} else {
				BigInteger id = event.getID();
				int hopCount = event.getHopCount();
				Snapshot.setLookup(id, responsible, hopCount);
			}
		}
	};

	Handler<PeriodicStabilization> handlePeriodicStabilization = new Handler<PeriodicStabilization>() {
		public void handle(PeriodicStabilization event) {
			if ((Peer.this.succ == null) && (Peer.this.joinCounter != -1)) {
				if (Peer.this.joinCounter++ > Peer.WAIT_TIME_TO_REJOIN) {
					Peer.this.joinCounter = 0;
					Peer.this.bootstrapped = false;

					BootstrapRequest request = new BootstrapRequest("chord", 1);
					Peer.this.trigger(request, Peer.this.bootstrap
							.getPositive(P2pBootstrap.class));
				}
			}

			if (Peer.this.succ != null) {
				Peer.this.trigger(new WhoIsPred(Peer.this.peerSelf,
						Peer.this.succ), Peer.this.network);
				Snapshot.sentMsg();
			}

			if (Peer.this.succ == null) {
				return;
			}
			Peer.this.fingerIndex += 1;
			if (Peer.this.fingerIndex == Peer.FINGER_SIZE) {
				Peer.this.fingerIndex = 1;
			}
			BigInteger index = new BigInteger("2").pow(Peer.this.fingerIndex);
			BigInteger id = Peer.this.peerSelf.getPeerId().add(index).mod(
					Peer.RING_SIZE);

			if (RingKey.belongsTo(id, Peer.this.peerSelf.getPeerId(),
					Peer.this.succ.getPeerId(),
					RingKey.IntervalBounds.OPEN_CLOSED, Peer.RING_SIZE)) {
				Peer.this.fingers[Peer.this.fingerIndex] = new PeerAddress(
						Peer.this.succ);
			} else {
				PeerAddress nextPeer = Peer.this.closestPrecedingNode(id);
				Peer.this.trigger(
						new FindSucc(Peer.this.peerSelf, nextPeer,
								Peer.this.peerSelf, id, Peer.this.fingerIndex,
								0, false), Peer.this.network);
			}
		}
	};

	Handler<WhoIsPred> handleWhoIsPred = new Handler<WhoIsPred>() {
		public void handle(WhoIsPred event) {
			PeerAddress requester = event.getMSPeerSource();
			Peer.this.trigger(new WhoIsPredReply(Peer.this.peerSelf, requester,
					Peer.this.pred, Peer.this.succList), Peer.this.network);
		}
	};

	Handler<WhoIsPredReply> handleWhoIsPredReply = new Handler<WhoIsPredReply>() {
		public void handle(WhoIsPredReply event) {
			PeerAddress succPred = event.getPred();
			PeerAddress[] succSuccList = event.getSuccList();

			if (Peer.this.succ == null) {
				return;
			}
			if ((succPred != null)
					&& (RingKey.belongsTo(succPred.getPeerId(),
							Peer.this.peerSelf.getPeerId(), Peer.this.succ
									.getPeerId(),
							RingKey.IntervalBounds.OPEN_OPEN, Peer.RING_SIZE))) {
				Peer.this.succ = new PeerAddress(succPred);
				Peer.this.fingers[0] = succ;
				Peer.this.succList[0] = succ;
				Snapshot.setSucc(Peer.this.peerSelf, Peer.this.succ);
				Snapshot.setFingers(Peer.this.peerSelf, Peer.this.fingers);
				Peer.this.fdRegister(Peer.this.succ);
				Peer.this.joinCounter = -1;
			}

			for (int i = 1; i < succSuccList.length; ++i) {
				if (succSuccList[(i - 1)] != null) {
					Peer.this.succList[i] = new PeerAddress(
							succSuccList[(i - 1)]);
				}
			}
			Snapshot.setSuccList(Peer.this.peerSelf, Peer.this.succList);

			if (Peer.this.succ != null)
				Peer.this.trigger(new Notify(Peer.this.peerSelf,
						Peer.this.succ, Peer.this.peerSelf), Peer.this.network);
		}
	};

	Handler<Notify> handleNotify = new Handler<Notify>() {
		public void handle(Notify event) {
			PeerAddress newPred = event.getID();

			if ((Peer.this.pred == null)
					|| (RingKey.belongsTo(newPred.getPeerId(), Peer.this.pred
							.getPeerId(), Peer.this.peerSelf.getPeerId(),
							RingKey.IntervalBounds.OPEN_OPEN, Peer.RING_SIZE))) {
				Peer.this.pred = new PeerAddress(newPred);
				Peer.this.fdRegister(Peer.this.pred);
				Snapshot.setPred(Peer.this.peerSelf, newPred);
			}
		}
	};

	Handler<LookupPeer> handleLookup = new Handler<LookupPeer>() {
		public void handle(LookupPeer event) {
			if (Peer.this.succ == null) {
				return;
			}
			BigInteger id = event.getID();
			Snapshot.startLookup();

			if ((Peer.this.succ != null)
					&& (RingKey.belongsTo(id, Peer.this.peerSelf.getPeerId(),
							Peer.this.succ.getPeerId(),
							RingKey.IntervalBounds.OPEN_CLOSED, Peer.RING_SIZE))) {
				Peer.this.trigger(new FindSuccReply(Peer.this.peerSelf,
						Peer.this.peerSelf, Peer.this.succ, id,
						Peer.this.fingerIndex, 0, true), Peer.this.network);
			} else {
				PeerAddress nextPeer = Peer.this.closestPrecedingNode(id);
				Peer.this.trigger(
						new FindSucc(Peer.this.peerSelf, nextPeer,
								Peer.this.peerSelf, id, Peer.this.fingerIndex,
								0, true), Peer.this.network);
			}
		}
	};

	Handler<PeerFailureSuspicion> handlePeerFailureSuspicion = new Handler<PeerFailureSuspicion>() {
		public void handle(PeerFailureSuspicion event) {
			Address suspectedPeerAddress = event.getPeerAddress();

			if (event.getSuspicionStatus().equals(SuspicionStatus.SUSPECTED)) {
				if ((!Peer.this.fdPeers.containsKey(suspectedPeerAddress))
						|| (!Peer.this.fdRequests
								.containsKey(suspectedPeerAddress))) {
					return;
				}
				PeerAddress suspectedPeer = (PeerAddress) Peer.this.fdPeers
						.get(suspectedPeerAddress);
				Peer.this.fdUnregister(suspectedPeer);

				if (suspectedPeer.equals(Peer.this.pred)) {
					Peer.this.pred = null;
				}
				if (suspectedPeer.equals(Peer.this.succ)) {
					int i = 1;
					for (i = 1; i < Peer.SUCC_SIZE; ++i) {
						if ((Peer.this.succList[i] != null)
								&& (!Peer.this.succList[i]
										.equals(Peer.this.peerSelf))
								&& (!Peer.this.succList[i]
										.equals(suspectedPeer))) {
							Peer.this.succ = Peer.this.succList[i];
							Peer.this.fingers[0] = succ;
							Peer.this.joinCounter = -1;
							Peer.this.fdRegister(Peer.this.succ);
							break;
						}
						Peer.this.succ = null;
					}

					if (Peer.this.succ == null) {
						Peer.this.joinCounter = 0;
						Peer.this.bootstrapped = false;

						BootstrapRequest request = new BootstrapRequest(
								"chord", 1);
						Peer.this.trigger(request, Peer.this.bootstrap
								.getPositive(P2pBootstrap.class));
					}

					Snapshot.setSucc(Peer.this.peerSelf, Peer.this.succ);
					Snapshot.setFingers(Peer.this.peerSelf, Peer.this.fingers);

					for (; i > 0; --i) {
						Peer.this.succList = Peer.this
								.leftshift(Peer.this.succList);
					}
				}
				for (int i = 1; i < Peer.SUCC_SIZE; ++i)
					if ((Peer.this.succList[i] != null)
							&& (Peer.this.succList[i].equals(suspectedPeer)))
						Peer.this.succList[i] = null;
			}
		}
		/* 308 */
	};

	public Peer() {
		this.fdRequests = new HashMap<Address, UUID>();
		this.fdPeers = new HashMap<Address, PeerAddress>();

		for (int i = 0; i < SUCC_SIZE; ++i) {
			this.succList[i] = null;
		}
		for (int i = 0; i < FINGER_SIZE; ++i) {
			this.fingers[i] = null;
		}
		this.fd = create(PingFailureDetector.class);
		this.bootstrap = create(BootstrapClient.class);

		connect(this.network, this.fd.getNegative(Network.class));
		connect(this.network, this.bootstrap.getNegative(Network.class));
		connect(this.timer, this.fd.getNegative(Timer.class));
		connect(this.timer, this.bootstrap.getNegative(Timer.class));

		subscribe(this.handleJoin, this.msPeerPort);
		subscribe(this.handleLookup, this.msPeerPort);

		subscribe(this.handleFindSucc, this.network);
		subscribe(this.handleFindSuccReply, this.network);
		subscribe(this.handleWhoIsPred, this.network);
		subscribe(this.handleWhoIsPredReply, this.network);
		subscribe(this.handleNotify, this.network);

		subscribe(this.handleInit, this.control);
		subscribe(this.handlePeriodicStabilization, this.timer);
		subscribe(this.handleBootstrapResponse, this.bootstrap
				.getPositive(P2pBootstrap.class));
		subscribe(this.handlePeerFailureSuspicion, this.fd
				.getPositive(FailureDetector.class));
	}

	private PeerAddress closestPrecedingNode(BigInteger id) {
		for (int i = FINGER_SIZE - 1; i >= 0; --i) {
			if ((this.fingers[i] != null)
					&& (RingKey.belongsTo(this.fingers[i].getPeerId(),
							this.peerSelf.getPeerId(), id,
							RingKey.IntervalBounds.OPEN_OPEN, RING_SIZE))) {
				return this.fingers[i];
			}
		}
		return this.peerSelf;
	}

	private PeerAddress[] leftshift(PeerAddress[] list) {
		PeerAddress[] newList = new PeerAddress[list.length];

		for (int i = 1; i < list.length; ++i) {
			newList[(i - 1)] = list[i];
		}
		newList[(list.length - 1)] = null;

		return newList;
	}

	private void fdRegister(PeerAddress peer) {
		Address peerAddress = peer.getPeerAddress();
		StartProbingPeer spp = new StartProbingPeer(peerAddress, peer);
		this.fdRequests.put(peerAddress, spp.getRequestId());
		trigger(spp, this.fd.getPositive(FailureDetector.class));

		this.fdPeers.put(peerAddress, peer);
	}

	private void fdUnregister(PeerAddress peer) {
		if (peer == null) {
			return;
		}
		Address peerAddress = peer.getPeerAddress();
		trigger(new StopProbingPeer(peerAddress, (UUID) this.fdRequests
				.get(peerAddress)), this.fd.getPositive(FailureDetector.class));
		this.fdRequests.remove(peerAddress);

		this.fdPeers.remove(peerAddress);
	}
}
