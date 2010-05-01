package se.sics.kompics.p2p.peer;

import se.sics.kompics.Init;
import se.sics.kompics.p2p.bootstrap.BootstrapConfiguration;
import se.sics.kompics.p2p.fd.ping.PingFailureDetectorConfiguration;

public final class PeerInit extends Init {
	private final PeerAddress msPeerSelf;
	private final BootstrapConfiguration bootstrapConfiguration;
	private final PeerConfiguration msConfiguration;
	private final PingFailureDetectorConfiguration fdConfiguration;

	public PeerInit(PeerAddress msPeerSelf, PeerConfiguration msConfiguration,
			BootstrapConfiguration bootstrapConfiguration,
			PingFailureDetectorConfiguration fdConfiguration) {
		this.msPeerSelf = msPeerSelf;
		this.bootstrapConfiguration = bootstrapConfiguration;
		this.msConfiguration = msConfiguration;
		this.fdConfiguration = fdConfiguration;
	}

	public PeerAddress getMSPeerSelf() {
		return this.msPeerSelf;
	}

	public BootstrapConfiguration getBootstrapConfiguration() {
		return this.bootstrapConfiguration;
	}

	public PeerConfiguration getMSConfiguration() {
		return this.msConfiguration;
	}

	public PingFailureDetectorConfiguration getFdConfiguration() {
		return this.fdConfiguration;
	}
}