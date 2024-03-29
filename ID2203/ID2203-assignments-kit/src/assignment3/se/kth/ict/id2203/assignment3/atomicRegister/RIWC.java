package se.kth.ict.id2203.assignment3.atomicRegister;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.assignment3.beb.BebBroadcast;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.pfd.CrashEvent;
import se.kth.ict.id2203.pfd.PfdLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class RIWC extends ComponentDefinition {

	Negative<AtomicRegisterLink> arl = negative(AtomicRegisterLink.class);
	Positive<BebLink> bl = positive(BebLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);
	Positive<PfdLink> pl = positive(PfdLink.class);
	
	private static final Logger logger = LoggerFactory
	.getLogger(RIWC.class);
	
	public static final int REGNUM = 10;
	
	private Address self;
	private Set<Address> correct;
	private int i;
	private ArrayList<Set<Address>> writeSet = new ArrayList<Set<Address>>(REGNUM);
	private boolean[] reading = new boolean[REGNUM];
	private int[] reqid = new int[REGNUM];
	private int[] readval = new int[REGNUM];
	private int[] v = new int[REGNUM];
	private int[] ts = new int[REGNUM];
	private int[] mrank = new int[REGNUM];
	
	
	public RIWC() {
		subscribe(handleInit, control);
		subscribe(handleReadRequest, arl);
		subscribe(handleWriteRequest, arl);
		subscribe(handleWriteMessage, bl);
		subscribe(handleAckMessage, pp2p);
		subscribe(handleCrashEvent, pl);
	}
	
	// init
	private Handler<RiwcInit> handleInit = new Handler<RiwcInit>() {

		@Override
		public void handle(RiwcInit event) {
			self = event.getTopology().getSelfAddress();
			correct = event.getTopology().getAllAddresses();
			i = self.getId();
			
			for(int in = 0; in < REGNUM; in++) {
				writeSet.add(new LinkedHashSet<Address>());
				reading[in] = false;
				reqid[in] = 0;
				readval[in] = 0;
				v[in] = 0;
				ts[in] = 0;
				mrank[in] = 0;				
			}
		}
	
	};
	// crash
	private Handler<CrashEvent> handleCrashEvent = new Handler<CrashEvent>() {

		@Override
		public void handle(CrashEvent event) {
			correct.remove(event.getCrashNode());
			checkWriteSet();
			logger.debug(event.toString());
		}
		
	};
	// nn-aRegRead | r
	private Handler<ReadRequest> handleReadRequest = new Handler<ReadRequest>() {

		@Override
		public void handle(ReadRequest event) {
			int r = event.getRegister();
			reqid[r] = reqid[r] + 1;
			reading[r] = true;
			writeSet.get(r).clear();
			readval[r] = v[r];
			WriteMessage wm = new WriteMessage(self, r, reqid[r], ts[r], mrank[r], v[r]);
			trigger(new BebBroadcast(wm), bl);
			logger.debug("Read Request send", wm);
		}
		
	};
	// nn-aRegWrite
	private Handler<WriteRequest> handleWriteRequest = new Handler<WriteRequest>() {

		@Override
		public void handle(WriteRequest event) {
			int r = event.getRegister();
			int val = event.getValue();
			reqid[r] = reqid[r]+1;
			writeSet.get(r).clear();
			WriteMessage wm = new WriteMessage(self, r, reqid[r], ts[r]+1, i, val);
			trigger(new BebBroadcast(wm), bl);
			logger.debug("Write Request send", wm);
		}
	};
	// bebDeliver | pj, [Write, r, id, (t, j), val]
	private Handler<WriteMessage> handleWriteMessage = new Handler<WriteMessage>() {

		@Override
		public void handle(WriteMessage event) {
			int r = event.getRegister();
			int id = event.getReqid();
			int t = event.getTs();
			int j = event.getRank();
			int val = event.getValue();
			if(t>ts[r] || (t == ts[r] && j>mrank[r])) {
				v[r] = val;
				ts[r] = t;
				mrank[r] = j;
			}
			AckMessage am = new AckMessage(self, r, id);
			trigger(new Pp2pSend(event.getSource(), am), pp2p);
			logger.debug(am.toString());
		}
		
	};
	// pp2pDeliver | pj, [ACK, r, id]
	private Handler<AckMessage> handleAckMessage = new Handler<AckMessage>() {

		@Override
		public void handle(AckMessage event) {
			logger.debug("Receive Ack:{}", event);
			int id = event.getReqid();
			int r = event.getRegister();
			if(id == reqid[r]) {
				writeSet.get(r).add(event.getSource());
				checkWriteSet();
			}
		}
	};
	// whether sub is a sub set of full
	private boolean isSubSet(Set<Address> sub, Set<Address> full) {
		for(Address a : sub) {
			if(!full.contains(a)) return false;
		}
		return true;
	}
	// exists r such that correct <= writeSet[r]
	private void checkWriteSet() {
		for(int r = 0; r < REGNUM; r++) {
			if(isSubSet(correct, writeSet.get(r))) {
				if(reading[r]) {
					reading[r] = false;
					trigger(new ReadResponse(r, readval[r]), arl);
				} else {
					trigger(new WriteResponse(r), arl);
				}
				writeSet.get(r).clear();
			}
		}
	}
	
}
