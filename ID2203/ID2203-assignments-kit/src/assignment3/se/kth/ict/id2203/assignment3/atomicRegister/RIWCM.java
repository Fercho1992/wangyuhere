package se.kth.ict.id2203.assignment3.atomicRegister;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.assignment3.beb.BebBroadcast;
import se.kth.ict.id2203.assignment3.beb.BebLink;
import se.kth.ict.id2203.pp2p.PerfectPointToPointLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Negative;
import se.sics.kompics.Positive;
import se.sics.kompics.address.Address;

public class RIWCM extends ComponentDefinition {

	Negative<AtomicRegisterLink> arl = negative(AtomicRegisterLink.class);
	Positive<BebLink> bl = positive(BebLink.class);
	Positive<PerfectPointToPointLink> pp2p = positive(PerfectPointToPointLink.class);

	private static final Logger logger = LoggerFactory
	.getLogger(RIWCM.class);
	public static final int REGNUM = 10;

	private Address self;
	private int N;
	private int i;
	private ArrayList<Set<Address>> writeSet = new ArrayList<Set<Address>>(
			REGNUM);
	private ArrayList<Set<ReadItem>> readSet = new ArrayList<Set<ReadItem>>(
			REGNUM);
	private boolean[] reading = new boolean[REGNUM];
	private int[] reqid = new int[REGNUM];
	private int[] readval = new int[REGNUM];
	private int[] writeval = new int[REGNUM];
	private int[] v = new int[REGNUM];
	private int[] ts = new int[REGNUM];
	private int[] mrank = new int[REGNUM];

	public RIWCM() {
		subscribe(handleInit, control);
		subscribe(handleReadRequest, arl);
		subscribe(handleWriteRequest, arl);
		subscribe(handleReadMessage, bl);
		subscribe(handleWriteMessage, bl);
		subscribe(handleReadValueMessage, pp2p);
		subscribe(handleAckMessage, pp2p);
	}
	// Init
	private Handler<RiwcInit> handleInit = new Handler<RiwcInit>() {

		@Override
		public void handle(RiwcInit event) {
			self = event.getTopology().getSelfAddress();
			i = self.getId();
			N = event.getTopology().getNodeCount();
			
			for(int in = 0; in < REGNUM; in++) {
				writeSet.add(new LinkedHashSet<Address>());
				readSet.add(new LinkedHashSet<ReadItem>());
				reading[in] = false;
				reqid[in] = 0;
				readval[in] = 0;
				writeval[in] = 0;
				v[in] = 0;
				ts[in] = 0;
				mrank[in] = 0;				
			}
		}

	};
	// nn-aRegRead | r
	private Handler<ReadRequest> handleReadRequest = new Handler<ReadRequest>() {

		@Override
		public void handle(ReadRequest event) {
			int r = event.getRegister();
			reqid[r] = reqid[r] + 1;
			reading[r] = true;
			readSet.get(r).clear();
			writeSet.get(r).clear();
			ReadMessage rm = new ReadMessage(self, r, reqid[r]);
			trigger(new BebBroadcast(rm), bl);
			logger.debug("Read Request broadcast {}", rm);
		}

	};
	// nn-aRegWrite | r, val
	private Handler<WriteRequest> handleWriteRequest = new Handler<WriteRequest>() {

		@Override
		public void handle(WriteRequest event) {
			int r = event.getRegister();
			int val = event.getValue();
			reqid[r] = reqid[r] + 1;
			writeval[r] = val;
			readSet.get(r).clear();
			writeSet.get(r).clear();
			ReadMessage rm = new ReadMessage(self, r, reqid[r]);
			trigger(new BebBroadcast(rm), bl);
			logger.debug("Write Request broadcast {}", rm);
		}

	};
	// bebDeliver | pj, [Read, r, reqid[r]]
	private Handler<ReadMessage> handleReadMessage = new Handler<ReadMessage>() {

		@Override
		public void handle(ReadMessage event) {
			int r = event.getRegister();
			int id = event.getReqid();
			ReadValueMessage rvm = new ReadValueMessage(self, r, id, ts[r], mrank[r], v[r]);
			trigger(new Pp2pSend(event.getSource(), rvm), pp2p);
			logger.debug("Send ReadValueMessage to {} {}", event.getSource(), rvm);
		}

	};
	// pp2pDeliver | pj, [ReadVal, r, id (t, rk), val]
	private Handler<ReadValueMessage> handleReadValueMessage = new Handler<ReadValueMessage>() {

		@Override
		public void handle(ReadValueMessage event) {
			logger.debug("Receive ReadValueMessage from {}", event.getSource());
			int r = event.getRegister();
			int id = event.getReqid();
			int t = event.getTs();
			int rk = event.getRank();
			int val = event.getValue();
			if(id == reqid[r]) {
				ReadItem i = new ReadItem(t, rk, val);
				readSet.get(r).add(i);
				doCheckReadSet();
			}
		}

	};
	// check exists r such that |readSet[r]| > N/2 and do...
	private void doCheckReadSet() {
		for(int r = 0; r < readSet.size(); r++) {
			if(readSet.get(r).size() > N/2) {
				ReadItem item = getHighestValue(readSet.get(r));
				int t = item.t;
				int rk = item.rk;
				int v = item.val;
				readval[r] = v;
				if(reading[r]) {
					WriteMessage wm = new WriteMessage(self, r, reqid[r], t, rk, readval[r]);
					trigger(new BebBroadcast(wm), bl);
					logger.debug("Check Read Set for reading broadcast {}", wm);
				} else {
					WriteMessage wm = new WriteMessage(self, r, reqid[r], t+1, i, writeval[r]);
					trigger(new BebBroadcast(wm), bl);
					logger.debug("Check Read Set for writing broadcast {}", wm);
				}
				readSet.get(r).clear();
			}
		}
	}
	// Get the ReadItem with the highest value in the Set
	private ReadItem getHighestValue(Set<ReadItem> set) {
		if(set.size() == 0) return null;
		ReadItem max = set.iterator().next();
		for(ReadItem item : set) {
			if(item.t > max.t && item.rk > max.rk) {
				max = item;
			}
		}
		return max;
	}
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
				logger.debug("Change value to {}", val);
			}
			AckMessage am = new AckMessage(self, r, id);
			trigger(new Pp2pSend(event.getSource(), am), pp2p);
			logger.debug("Send Ack to {} {}", event.getSource(), am);
		}

	};
	// pp2pDeliver | pj, [ACK, r, id]
	private Handler<AckMessage> handleAckMessage = new Handler<AckMessage>() {

		@Override
		public void handle(AckMessage event) {
			int r = event.getRegister();
			int id = event.getReqid();
			if(id == reqid[r]) {
				writeSet.get(r).add(event.getSource());
				doCheckWriteSet();
			}
		}
	};
	// check exists r such that |writeSet[r]| > N/2 do ...
	private void doCheckWriteSet() {
		for(int r = 0; r < writeSet.size(); r++) {
			if(writeSet.get(r).size() > N/2) {
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
