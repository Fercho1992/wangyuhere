package se.kth.ict.id2203.assignment4.rwac;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.sics.kompics.address.Address;

public class NAck extends Pp2pDeliver {

	private final int id;
	protected NAck(Address source, int id) {
		super(source);
		this.id = id;
	}
	
	

	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n==========NAck===========\n");
		sb.append("Source = " + getSource() + "\n");
		sb.append("id = " + id + "\n");
		sb.append("===========================\n");
		return sb.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3970739103020126739L;

}
