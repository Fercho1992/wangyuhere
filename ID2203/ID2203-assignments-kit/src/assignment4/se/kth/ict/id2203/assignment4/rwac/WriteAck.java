package se.kth.ict.id2203.assignment4.rwac;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class WriteAck extends Pp2pDeliver {

	private final int id;
	private final int ts;
	
	protected WriteAck(Address source, int id, int ts) {
		super(source);
		this.id = id;
		this.ts = ts;
	}

	public int getId() {
		return id;
	}



	public int getTs() {
		return ts;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n======WriteAck=========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("id = " + id + "\n");
		sb.append("ts = " + ts + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4560872058269616457L;

}
