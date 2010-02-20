package se.kth.ict.id2203.assignment3.atomicRegister;

import se.kth.ict.id2203.assignment3.beb.BebDeliver;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;

public class RIWCM extends ComponentDefinition {

	Negative<AtomicRegisterLink> arl = negative(AtomicRegisterLink.class);
	Positive<BebLink> bl = positive(BebLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	
	public RIWCM() {
		subscribe(handleInit, control);
		subscribe(handleReadRequest, arl);
		subscribe(handleWriteRequest, arl);
		subscribe(handleBebDeliver, bl);
		subscribe(handleAckMessage, pp2p);
	}
	
	private Handler<RiwcInit> handleInit = new Handler<RiwcInit>() {

		@Override
		public void handle(RiwcInit event) {
		}
	
	};
	
	private Handler<ReadRequest> handleReadRequest = new Handler<ReadRequest>() {

		@Override
		public void handle(ReadRequest event) {
			
		}
		
	};
	
	private Handler<BebDeliver> handleBebDeliver = new Handler<BebDeliver>() {

		@Override
		public void handle(BebDeliver event) {
			
		}
		
	};
	
	private Handler<AckMessage> handleAckMessage = new Handler<AckMessage>() {

		@Override
		public void handle(AckMessage event) {
			
		}
		
	};
	private Handler<WriteRequest> handleWriteRequest = new Handler<WriteRequest>() {

		@Override
		public void handle(WriteRequest event) {
			
		}
		
	};
}
