package se.kth.ict.id2203.assignment1.applicatoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.application.ApplicationContinue;
import se.kth.ict.id2203.pfd.CrashEvent;
import se.kth.ict.id2203.pfd.PfdLink;
import se.kth.ict.id2203.pp2p.Pp2pSend;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Positive;
import se.sics.kompics.Start;
import se.sics.kompics.address.Address;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class Application1a extends ComponentDefinition {

	Positive<PfdLink> pfd = positive(PfdLink.class);
	Positive<Timer> timer = positive(Timer.class);

	private static final Logger logger = LoggerFactory
			.getLogger(Application1a.class);

	private String[] commands;
	private int lastCommand;
	private Set<Address> neighborSet;
	private Address self;

	/**
	 * Instantiates a new application0.
	 */
	public Application1a() {
		subscribe(handleInit, control);
		subscribe(handleStart, control);
		subscribe(handleContinue, timer);
		subscribe(handleCrashEvent, pfd);
		subscribe(handlePp2pMessage, pfd);
	}

	Handler<Application1aInit> handleInit = new Handler<Application1aInit>() {
		public void handle(Application1aInit event) {
			commands = event.getCommandScript().split(":");
			lastCommand = -1;
			neighborSet = event.getNeighborSet();
			self = event.getSelf();
		}
	};

	Handler<Start> handleStart = new Handler<Start>() {
		public void handle(Start event) {
			doNextCommand();
		}
	};

	Handler<ApplicationContinue> handleContinue = new Handler<ApplicationContinue>() {
		public void handle(ApplicationContinue event) {
			doNextCommand();
		}
	};

	Handler<CrashEvent> handleCrashEvent = new Handler<CrashEvent>() {
		public void handle(CrashEvent event) {
			logger.info("{} was crashed!", event.getCrashNode().getId());
		}
	};
	
	Handler<Pp2pMessage> handlePp2pMessage = new Handler<Pp2pMessage>() {
		public void handle(Pp2pMessage event) {
			logger.info("Received perfect message {}", event.getMessage());
		}
	};

	private final void doNextCommand() {
		lastCommand++;

		if (lastCommand > commands.length) {
			return;
		}
		if (lastCommand == commands.length) {
			logger.info("DONE ALL OPERATIONS");
			Thread applicationThread = new Thread("ApplicationThread") {
				public void run() {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(System.in));
					while (true) {
						try {
							String line = in.readLine();
							doCommand(line);
						} catch (Throwable e) {
							e.printStackTrace();
							break;
						}
					}
				}
			};
			applicationThread.start();
			return;
		}
		String op = commands[lastCommand];
		doCommand(op);
	}

	private void doCommand(String cmd) {
		if (cmd.startsWith("P")) {
			doPerfect(cmd.substring(1));
			doNextCommand();
		} else if (cmd.startsWith("L")) {
			doLossy(cmd.substring(1));
			doNextCommand();
		} else if (cmd.startsWith("S")) {
			doSleep(Integer.parseInt(cmd.substring(1)));
		} else if (cmd.startsWith("X")) {
			doShutdown();
		} else if (cmd.equals("help")) {
			doHelp();
			doNextCommand();
		} else if (cmd.equals("")) {
			doNextCommand();
		} else {
			logger.info("Bad command: '{}'. Try 'help'", cmd);
			doNextCommand();
		}
	}

	private final void doHelp() {
		logger.info("Available commands: P<m>, L<m>, S<n>, help, X");
		logger.info("Pm: sends perfect message 'm' to all neighbors");
		logger.info("Lm: sends lossy message 'm' to all neighbors");
		logger.info("Sn: sleeps 'n' milliseconds before the next command");
		logger.info("help: shows this help message");
		logger.info("X: terminates this process");
	}

	private final void doPerfect(String message) {
		for (Address neighbor : neighborSet) {
			logger.info("Sending perfect message {} to {}", message, neighbor);
			trigger(new Pp2pSend(neighbor, new Pp2pMessage(self, message)), pfd);
		}
	}

	private final void doLossy(String message) {
		for (Address neighbor : neighborSet) {
			logger.info("Sending lossy message {} to {}", message, neighbor);
			//trigger(new Flp2pSend(neighbor, new Flp2pMessage(self, message)),flp2p);
		}
	}

	private void doSleep(long delay) {
		logger.info("Sleeping {} milliseconds...", delay);

		ScheduleTimeout st = new ScheduleTimeout(delay);
		st.setTimeoutEvent(new ApplicationContinue(st));
		trigger(st, timer);
	}

	private void doShutdown() {
		System.out.close();
		System.err.close();
		System.exit(0);
	}
}
