package se.kth.ict.id2203.pfd;


import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.PortType;

public class PfdLink extends PortType {
	{
		positive(CrashEvent.class);
		positive(Pp2pDeliver.class);
		negative(Pp2pSend.class);
	}
}
