package se.kth.ict.id2203.assignment2.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.application.ApplicationContinue;
import se.kth.ict.id2203.assignment1.applicatoin.Application1bInit;
import se.kth.ict.id2203.assignment2.broadcast.LPBBroadcast;
import se.kth.ict.id2203.assignment2.broadcast.LPBDeliver;
import se.kth.ict.id2203.assignment2.broadcast.LPBLink;
import se.kth.ict.id2203.epfd.Restore;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Positive;
import se.sics.kompics.Start;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class Application2 extends ComponentDefinition {

	Positive<Timer> timer = positive(Timer.class);
	Positive<LPBLink> lpb = positive(LPBLink.class);

	private static final Logger logger = LoggerFactory
			.getLogger(Application2.class);

	private String[] commands;
	private int lastCommand;

	/**
	 * Instantiates a new application0.
	 */
	public Application2() {
		subscribe(handleInit, control);
		subscribe(handleStart, control);
		subscribe(handleContinue, timer);
		subscribe(handleLPBDeliver, lpb);
	}

	Handler<Application1bInit> handleInit = new Handler<Application1bInit>() {
		public void handle(Application1bInit event) {
			commands = event.getCommandScript().split(":");
			lastCommand = -1;
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

	Handler<Restore> handleRestore = new Handler<Restore>() {
		public void handle(Restore event) {
			logger.info("Node {} was restored! Period={}", event.getNode()
					.getId(), event.getPeriod());
		}
	};

	Handler<LPBDeliver> handleLPBDeliver = new Handler<LPBDeliver>() {
		public void handle(LPBDeliver event) {
			logger.info("Message {}({}) was delivered!", event.getSource(), event.getMessage());
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
		if (cmd.startsWith("B")) {
			doLPB(cmd.substring(1));
			doNextCommand();
		} else if (cmd.startsWith("S")) {
			doSleep(Integer.parseInt(cmd.substring(1)));
		} else if (cmd.startsWith("X")) {
			doShutdown();
		} else if (cmd.equals("help")) {
			doHelp();
			doNextCommand();
		} else if(cmd.startsWith("A")) {
			final int TIME = Integer.parseInt(cmd.substring(1));
			Thread sender = new Thread("ApplicationThread") {
				public void run() {
					int i = 1;
					while (true) {
						doLPB(String.valueOf(i));
						try {
							Thread.sleep(TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
						i++;
						if(i > 30) break;
					}
				}
			};
			sender.start();
		} else if (cmd.trim().equals("")) {
			doNextCommand();
		} else {
			logger.info("Bad command: '{}'. Try 'help'", cmd);
			doNextCommand();
		}
	}

	private final void doHelp() {
		logger.info("Available commands: B<m>, S<n>, help, X");
		logger.info("Bm: LPB message 'm'");
		logger.info("Axxx to repeated 30 broadcast every xxx milisec");
		logger.info("Sn: sleeps 'n' milliseconds before the next command");
		logger.info("help: shows this help message");
		logger.info("X: terminates this process");
	}

	private final void doLPB(String message) {
		trigger(new LPBBroadcast(message), lpb);
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
