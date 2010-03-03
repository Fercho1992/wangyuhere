package se.kth.ict.id2203.assignment4.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.application.ApplicationContinue;
import se.kth.ict.id2203.assignment4.uc.UcDecide;
import se.kth.ict.id2203.assignment4.uc.UcLink;
import se.kth.ict.id2203.assignment4.uc.UcPropose;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Positive;
import se.sics.kompics.Start;
import se.sics.kompics.address.Address;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class Application4 extends ComponentDefinition {
	Positive<Timer> timer = positive(Timer.class);
	Positive<UcLink> uc = positive(UcLink.class);

	private static final Logger logger = LoggerFactory
			.getLogger(Application4.class);

	private String[] commands;
	private int lastCommand;
	private Set<Address> all;
	
	public Application4() {
		subscribe(handleInit, control);
		subscribe(handleStart, control);
		subscribe(handleContinue, timer);
		subscribe(handleUcDecide, uc);
	}

	Handler<Application4Init> handleInit = new Handler<Application4Init>() {
		public void handle(Application4Init event) {
			commands = event.getCommandScript().split(":");
			lastCommand = -1;
			all = event.getNeighborSet();
			all.add(event.getSelf());
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
	
	Handler<UcDecide> handleUcDecide = new Handler<UcDecide>() {

		@Override
		public void handle(UcDecide event) {
			logger.info("UcDecide: decide value {} in {}", event.getVal(), event.getId());
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
			String[] ij = cmd.substring(1).split("-");
			int i = Integer.parseInt(ij[0]);
			int j = Integer.parseInt(ij[1]);
			doPropose(i, j);
			doNextCommand();
		} else if (cmd.startsWith("D")) {
			doSleep(Integer.parseInt(cmd.substring(1)));
		} else if (cmd.startsWith("X")) {
			doShutdown();
		} else if (cmd.equals("help")) {
			doHelp();
			doNextCommand();
		} else if(cmd.startsWith("W")) {
			printDecisions();
			doNextCommand();
		} else if (cmd.trim().equals("")) {
			doNextCommand();
		} else {
			logger.info("Bad command: '{}'. Try 'help'", cmd);
			doNextCommand();
		}
	}

	private final void doHelp() {
		logger.info("Available commands: W<m>, R, D<n>, help, X");
		logger.info("Wm: write m to register");
		logger.info("R: read from register");
		logger.info("Dn: sleeps 'n' milliseconds before the next command");
		logger.info("help: shows this help message");
		logger.info("X: terminates this process");
	}

	private void doPropose(int i, int j) {
		logger.info("Propose: propose {} on {}", j, i);
		trigger(new UcPropose(i, j), uc);
	}
	
	private void printDecisions() {
		
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
