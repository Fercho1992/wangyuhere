package se.kth.ict.id2203.assignment2.broadcast;

import java.util.Set;

import se.kth.ict.id2203.flp2p.FairLossPointToPointLink;
import se.kth.ict.id2203.flp2p.Flp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class UnreliableBroadcast extends ComponentDefinition {

	Negative<UnLink> ul = negative(UnLink.class);
	Positive<FairLossPointToPointLink> flp2p = positive(FairLossPointToPointLink.class);
	
	private Set<Address> neighbors;
	private Address self;
	
	public UnreliableBroadcast() {
		subscribe(handleInit, control);
		subscribe(handleUnBroadcast, ul);
		subscribe(handleDataMessage, flp2p);
	}
	
	private Handler<UnInit> handleInit = new Handler<UnInit>() {

		@Override
		public void handle(UnInit event) {
			self = event.getTopology().getSelfAddress();
			neighbors = event.getTopology().getNeighbors(self);
		}
		
	};
	
	private Handler<UnBroadcast> handleUnBroadcast = new Handler<UnBroadcast>() {

		@Override
		public void handle(UnBroadcast event) {
			for(Address a : neighbors) {
				trigger(new Flp2pSend(a, event.getData()), flp2p);
			}
		}
		
	};
	
	private Handler<DataMessage> handleDataMessage = new Handler<DataMessage>(){

		@Override
		public void handle(DataMessage event) {
			trigger(new UnDeliver(self, event), ul);
		}
	};
	
	
}
