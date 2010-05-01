package se.sics.kompics.p2p.peer;

import java.math.BigInteger;
import se.sics.kompics.address.Address;
import se.sics.kompics.p2p.overlay.OverlayAddress;

public final class PeerAddress extends OverlayAddress implements
		Comparable<PeerAddress> {
	private static final long serialVersionUID = -7582889514221620065L;
	private final BigInteger peerId;

	public PeerAddress(PeerAddress address) {
		super(address.getPeerAddress());
		this.peerId = address.peerId;
	}

	public PeerAddress(Address address, BigInteger peerId) {
		super(address);
		this.peerId = peerId;
	}

	public BigInteger getPeerId() {
		return this.peerId;
	}

	public final boolean belongsTo(PeerAddress from, PeerAddress to,
			IntervalBounds bounds, BigInteger ringSize) {
		BigInteger ny = ringSize.add(to.peerId).subtract(from.peerId).mod(
				ringSize);
		BigInteger nx = ringSize.add(this.peerId).subtract(from.peerId).mod(
				ringSize);

		if (bounds.equals(IntervalBounds.OPEN_OPEN))
			return ((from.peerId.equals(to.peerId)) && (!this.peerId
					.equals(from.peerId)))
					|| ((nx.compareTo(BigInteger.ZERO) > 0) && (nx
							.compareTo(ny) < 0));
		if (bounds.equals(IntervalBounds.OPEN_CLOSED))
			return (from.peerId.equals(to.peerId))
					|| ((nx.compareTo(BigInteger.ZERO) > 0) && (nx
							.compareTo(ny) <= 0));
		if (bounds.equals(IntervalBounds.CLOSED_OPEN))
			return (from.peerId.equals(to.peerId))
					|| ((nx.compareTo(BigInteger.ZERO) >= 0) && (nx
							.compareTo(ny) < 0));
		if (bounds.equals(IntervalBounds.CLOSED_CLOSED)) {
			return ((from.peerId.equals(to.peerId)) && (this.peerId
					.equals(from.peerId)))
					|| ((nx.compareTo(BigInteger.ZERO) >= 0) && (nx
							.compareTo(ny) <= 0));
		}
		throw new RuntimeException("Unknown interval bounds");
	}

	public int compareTo(PeerAddress that) {
		return this.peerId.compareTo(that.peerId);
	}

	public String toString() {
		return this.peerId.toString();
	}

	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result
				+ ((this.peerId == null) ? 0 : this.peerId.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (super.getClass() != obj.getClass())
			return false;
		PeerAddress other = (PeerAddress) obj;
		if (this.peerId == null)
			if (other.peerId != null)
				return false;
			else if (!this.peerId.equals(other.peerId))
				return false;
		return true;
	}

	public static enum IntervalBounds {
		OPEN_OPEN, OPEN_CLOSED, CLOSED_OPEN, CLOSED_CLOSED;
	}
}
