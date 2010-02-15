package se.kth.ict.id2203.assignment2.broadcast;

import se.kth.ict.id2203.flp2p.Flp2pDeliver;
import se.sics.kompics.address.Address;

public class RequestMessage extends Flp2pDeliver {

	private final Address sm;
	private final int seqnb;
	private final int maxrounds;
	
	protected RequestMessage(Address source, Address s, int seq, int r) {
		super(source);
		sm = s;
		seqnb = seq;
		maxrounds = r;
	}
	
	

	public Address getSm() {
		return sm;
	}



	public int getSeqnb() {
		return seqnb;
	}



	public int getMaxrounds() {
		return maxrounds;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 3910437076179624552L;

}
