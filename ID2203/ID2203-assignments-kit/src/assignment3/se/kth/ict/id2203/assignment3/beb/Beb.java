package se.kth.ict.id2203.assignment3.beb;

import java.util.Set;

import se.kth.ict.id2203.assignment2.broadcast.UnInit;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class Beb extends ComponentDefinition {

	Negative<BebLink> bl = negative(BebLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	
	private Set<Address> all;
	
	public Beb() {
		subscribe(handleInit, control);
		subscribe(handleBebBroadcast, bl);
		subscribe(handleBebMessage, pp2p);
	}
	
	private Handler<UnInit> handleInit = new Handler<UnInit>() {

		@Override
		public void handle(UnInit event) {
			all = event.getTopology().getAllAddresses();
		}
		
	};
	
	private Handler<BebBroadcast> handleBebBroadcast = new Handler<BebBroadcast>() {

		@Override
		public void handle(BebBroadcast event) {
			for(Address a : all) {
				trigger(new Pp2pSend(a, event.getMessage()), pp2p);
			}
		}
		
	};
	
	private Handler<BebMessage> handleBebMessage = new Handler<BebMessage>(){

		@Override
		public void handle(BebMessage event) {
			trigger(new BebDeliver(), bl);
		}
	};
}
