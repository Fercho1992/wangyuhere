package se.sics.kompics.p2p.simulator.launch;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import se.sics.kompics.address.Address;
import se.sics.kompics.network.NetworkConfiguration;
import se.sics.kompics.network.Transport;
import se.sics.kompics.p2p.bootstrap.BootstrapConfiguration;
import se.sics.kompics.p2p.fd.ping.PingFailureDetectorConfiguration;
import se.sics.kompics.p2p.peer.PeerConfiguration;

public class Configuration {
	public static BigInteger TRACKER_ID = BigInteger.ZERO;
	public static int Log2Ring = 13;
	public static int SNAPSHOT_PERIOD = 1000;
	
	public InetAddress ip = null;
	{
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
	}
	
	int networkPort = 8081;
	int webPort = 8080;
	int bootId = Integer.MAX_VALUE;

	Address bootServerAddress = new Address(ip, networkPort, bootId);
	Address peer0Address = new Address(ip, networkPort, 0);

	BootstrapConfiguration bootConfiguration = new BootstrapConfiguration(bootServerAddress, 60000, 4000, 3, 30000, webPort, webPort);
	PingFailureDetectorConfiguration fdConfiguration = new PingFailureDetectorConfiguration(10000, 50000, 5000, 1000, Transport.TCP);
	NetworkConfiguration networkConfiguration = new NetworkConfiguration(ip, networkPort, 0);
	
	// you can define the peer parameters through the peerConfiguration(A, B, C, D, E, F)
	// A: Download bandwidth (Kbps)
	// B: Upload bandwidth (Kbps)
	// C: Node indegree that shows the maximum number of nodes that this node can download from them
	// D: Node outdegree that shows the maximum number of nodes that can download from this node
	// E: The number of pieces
	// F: The piece size (Kb)
	PeerConfiguration peerConfiguration = new PeerConfiguration(32, 32, 4, 4, 30, 16);

//-------------------------------------------------------------------	
	public void set() throws IOException {
		String c = File.createTempFile("bootstrap.", ".conf").getAbsolutePath();
		bootConfiguration.store(c);
		System.setProperty("bootstrap.configuration", c);

		c = File.createTempFile("ping.fd.", ".conf").getAbsolutePath();
		fdConfiguration.store(c);
		System.setProperty("ping.fd.configuration", c);
		
		c = File.createTempFile("network.", ".conf").getAbsolutePath();
		networkConfiguration.store(c);
		System.setProperty("network.configuration", c);

		c = File.createTempFile("ms.", ".conf").getAbsolutePath();
		peerConfiguration.store(c);
		System.setProperty("ms.configuration", c);
	}
}
