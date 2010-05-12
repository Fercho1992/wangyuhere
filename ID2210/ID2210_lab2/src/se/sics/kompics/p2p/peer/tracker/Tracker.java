package se.sics.kompics.p2p.peer.tracker;

import java.util.HashMap;

import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.p2p.peer.GetUploaderRequest;
import se.sics.kompics.p2p.peer.GetUploaderResponse;
import se.sics.kompics.p2p.peer.MessagePort;
import se.sics.kompics.p2p.peer.PeerAddress;
import se.sics.kompics.p2p.peer.PeerPort;
import se.sics.kompics.p2p.peer.RegisterPeer;
import se.sics.kompics.p2p.peer.UpdateBuffer;
import se.sics.kompics.p2p.simulator.launch.PeerType;
import se.sics.kompics.timer.Timer;

public final class Tracker extends ComponentDefinition {
	Negative<PeerPort> peerPort = negative(PeerPort.class);

	Positive<MessagePort> network = positive(MessagePort.class);
	Positive<Timer> timer = positive(Timer.class);

	private PeerAddress peerSelf;
	private int numOfPieces;
	private HashMap<PeerAddress, Boolean[]> peers = new HashMap<PeerAddress, Boolean[]>();

	// -------------------------------------------------------------------
	public Tracker() {
		subscribe(handleInit, control);
		subscribe(handleJoin, peerPort);
		subscribe(handleRegisterPeer, network);
		subscribe(handleUpdateBuffer, network);
		subscribe(handleGetUploader, network);
	}

	// -------------------------------------------------------------------
	Handler<TrackerInit> handleInit = new Handler<TrackerInit>() {
		public void handle(TrackerInit init) {
			peerSelf = init.getPeerSelf();
			numOfPieces = init.getNumOfPieces();
		}
	};

	// -------------------------------------------------------------------
	Handler<JoinTracker> handleJoin = new Handler<JoinTracker>() {
		public void handle(JoinTracker event) {
		}
	};

	// -------------------------------------------------------------------
	Handler<RegisterPeer> handleRegisterPeer = new Handler<RegisterPeer>() {
		public void handle(RegisterPeer event) {
			PeerType peerType = event.getPeerType();
			PeerAddress peer = event.getPeerSource();
			Boolean[] buffer = new Boolean[numOfPieces];
			boolean piece;

			if (peerType == PeerType.SEED)
				piece = true;
			else
				piece = false;

			for (int i = 0; i < numOfPieces; i++)
				buffer[i] = piece;

			peers.put(peer, buffer);
		}
	};

	// -------------------------------------------------------------------
	Handler<UpdateBuffer> handleUpdateBuffer = new Handler<UpdateBuffer>() {
		public void handle(UpdateBuffer event) {
			Boolean[] buffer = peers.get(event.getPeerSource());
			buffer[event.getPieceIndex()] = true;
		}
	};

	// -------------------------------------------------------------------
	Handler<GetUploaderRequest> handleGetUploader = new Handler<GetUploaderRequest>() {
		public void handle(GetUploaderRequest event) {
			int piece = -1;
			PeerAddress uploader = null;
			
			for (int i = 0; i < numOfPieces; i++) {
				if (!peers.get(event.getPeerSource())[i]) {
					piece = i;
					break;
				}
			}

			if (piece != -1) {
				
				for (PeerAddress node : peers.keySet()) {
					if (peers.get(node)[piece]) {
						uploader = node;
					}
				}
			}
			
			trigger(new GetUploaderResponse(peerSelf, event.getPeerSource(), uploader, piece), network);
		}
	};
}
