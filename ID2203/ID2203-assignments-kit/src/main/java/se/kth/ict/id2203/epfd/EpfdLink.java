package se.kth.ict.id2203.epfd;

import se.kth.ict.id2203.pp2p.Pp2pDeliver;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.PortType;

public class EpfdLink extends PortType {
	{
		positive(Suspect.class);
		positive(Restore.class);
		positive(Pp2pDeliver.class);
		negative(Pp2pSend.class);
	}
}
