package se.sics.kompics.p2p.peer.btpeer;

import java.util.ArrayList;
import java.util.Hashtable;

import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.p2p.peer.CloseConnection;
import se.sics.kompics.p2p.peer.CompleteUploaders;
import se.sics.kompics.p2p.peer.DataMessage;
import se.sics.kompics.p2p.peer.DownloadRequest;
import se.sics.kompics.p2p.peer.GetUploaderRequest;
import se.sics.kompics.p2p.peer.GetUploaderResponse;
import se.sics.kompics.p2p.peer.HandshakeRequest;
import se.sics.kompics.p2p.peer.HandshakeResponse;
import se.sics.kompics.p2p.peer.HandshakeStatus;
import se.sics.kompics.p2p.peer.MessagePort;
import se.sics.kompics.p2p.peer.PeerAddress;
import se.sics.kompics.p2p.peer.PeerPort;
import se.sics.kompics.p2p.peer.RegisterPeer;
import se.sics.kompics.p2p.peer.UpdateBuffer;
import se.sics.kompics.p2p.simulator.launch.PeerType;
import se.sics.kompics.p2p.simulator.snapshot.Snapshot;
import se.sics.kompics.timer.SchedulePeriodicTimeout;
import se.sics.kompics.timer.Timer;

public final class BTPeer extends ComponentDefinition {
	Negative<PeerPort> peerPort = negative(PeerPort.class);

	Positive<MessagePort> network = positive(MessagePort.class);
	Positive<Timer> timer = positive(Timer.class);
	
	private PeerAddress peerSelf;
	private PeerAddress tracker;
	private int indegree;
	private int outdegree;
	private int numOfPieces;
	private int pieceSize;
    
    private Hashtable<Integer, PeerAddress> uploaders;
    private int numOfDownloaders = 0;
    private ArrayList<Integer> downloadPieces;
	
//-------------------------------------------------------------------
	public BTPeer() {
		subscribe(handleInit, control);
		subscribe(handleJoin, peerPort);
		subscribe(handleCompleteUploaders, timer);
		subscribe(handleGetUploaderResponse, network);
		subscribe(handleHandshakeRequest, network);
		subscribe(handleHandshakeResponse, network);
		subscribe(handleDownloadRequest, network);
		subscribe(handleRecvDataMessage, network);
		subscribe(handleCloseConnection, network);
	}

//-------------------------------------------------------------------
	Handler<BTPeerInit> handleInit = new Handler<BTPeerInit>() {
		public void handle(BTPeerInit init) {
			peerSelf = init.getPeerSelf();
			indegree = init.getIndegree();
			outdegree = init.getOutdegree();
			numOfPieces = init.getNumOfPieces();
			pieceSize = init.getPieceSize();
			tracker = init.getTracker();
			
			uploaders = new Hashtable<Integer, PeerAddress>();
			downloadPieces = new ArrayList<Integer>();
		}
	};

//-------------------------------------------------------------------
	Handler<JoinBTPeer> handleJoin = new Handler<JoinBTPeer>() {
		public void handle(JoinBTPeer event) {
			PeerType peerType = event.getPeerType();
			Snapshot.addPeer(peerSelf, numOfPieces, peerType);
			
			trigger(new RegisterPeer(peerSelf, tracker, peerType), network);
			
			if (peerType != PeerType.SEED) {
				SchedulePeriodicTimeout spt = new SchedulePeriodicTimeout(5000, 5000);
				spt.setTimeoutEvent(new CompleteUploaders(spt));
				trigger(spt, timer);
			} else {
				//finishedCounter = numOfPieces;
			}
		}
	};

//-------------------------------------------------------------------
	Handler<CompleteUploaders> handleCompleteUploaders = new Handler<CompleteUploaders>() {
		public void handle(CompleteUploaders event) {
			// not a seed
			if(downloadPieces.size() != numOfPieces) {
				int freeDownloadSlots = indegree-uploaders.size();
				if(freeDownloadSlots>0) {
					for(int i = 0; i < freeDownloadSlots; i++) {
						trigger(new GetUploaderRequest(peerSelf, tracker), network);
					}
				}
			}
		}
	};

//-------------------------------------------------------------------
	Handler<GetUploaderResponse> handleGetUploaderResponse = new Handler<GetUploaderResponse>() {
		public void handle(GetUploaderResponse event) {
			if(event.getPiece() == -1 || event.getUploader() == null) {
				return;
			}
			// P is not already connected to Q
			if(!uploaders.containsKey(event.getUploader())) {
				trigger(new HandshakeRequest(peerSelf, event.getUploader(), event.getPiece()), network);
			}
		}
	};
	
//-------------------------------------------------------------------
	Handler<HandshakeRequest> handleHandshakeRequest = new Handler<HandshakeRequest>() {
		public void handle(HandshakeRequest event) {
			int freeUploadSlot = outdegree - numOfDownloaders;
			if(freeUploadSlot > 0) { // accept
				trigger(new HandshakeResponse(peerSelf, event.getPeerSource(), event.getPiece(), HandshakeStatus.ACCEPT), network);
				numOfDownloaders++;
			} else { // reject
				trigger(new HandshakeResponse(peerSelf, event.getPeerSource(), event.getPiece(), HandshakeStatus.REJECT), network);
			}
		}
	};

//-------------------------------------------------------------------
	Handler<HandshakeResponse> handleHandshakeResponse = new Handler<HandshakeResponse>() {
		public void handle(HandshakeResponse event) {
			// Q accepts P's request
			if(event.getHandshakeStatus() == HandshakeStatus.ACCEPT && ! downloadPieces.contains(event.getPiece())) {
				uploaders.put(event.getPiece(), event.getPeerSource());
				trigger(new DownloadRequest(peerSelf, event.getPeerSource(), event.getPiece()), network);
				downloadPieces.add(event.getPiece());
			}
			
		}
	};

//-------------------------------------------------------------------
	Handler<DownloadRequest> handleDownloadRequest = new Handler<DownloadRequest>() {
		public void handle(DownloadRequest event) {
			trigger(new DataMessage(peerSelf, event.getPeerSource(), event.getPiece(), pieceSize), network);
		}
	};

//-------------------------------------------------------------------
	Handler<DataMessage> handleRecvDataMessage = new Handler<DataMessage>() {
		public void handle(DataMessage event) {
			int piece = event.getPiece();
			Snapshot.addPiece(peerSelf, piece);
			
			trigger(new CloseConnection(peerSelf, event.getPeerSource()), network);
			trigger(new UpdateBuffer(peerSelf, tracker, piece), network);
			
			uploaders.remove(piece);
			
		}
	};

//-------------------------------------------------------------------
	Handler<CloseConnection> handleCloseConnection = new Handler<CloseConnection>() {
		public void handle(CloseConnection event) {
			numOfDownloaders --;
		}
	};
}
