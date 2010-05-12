package se.sics.kompics.p2p.peer;

public class HandshakeRequest extends ControlMessage {
	private static final long serialVersionUID = 314947423942404050L;
	private final int piece;
	
//-------------------------------------------------------------------	
	public HandshakeRequest(PeerAddress source, PeerAddress destination, int piece) {
		super(source, destination);
		this.piece = piece;
	}

//-------------------------------------------------------------------	
	public int getPiece() {
		return this.piece;
	}
}

