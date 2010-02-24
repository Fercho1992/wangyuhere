package se.kth.ict.id2203.assignment3.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.kth.ict.id2203.application.ApplicationContinue;
import se.kth.ict.id2203.assignment3.atomicRegister.AtomicRegisterLink;
import se.kth.ict.id2203.assignment3.atomicRegister.ReadRequest;
import se.kth.ict.id2203.assignment3.atomicRegister.ReadResponse;
import se.kth.ict.id2203.assignment3.atomicRegister.WriteRequest;
import se.kth.ict.id2203.assignment3.atomicRegister.WriteResponse;
import se.sics.kompics.ComponentDefinition;
import se.sics.kompics.Handler;
import se.sics.kompics.Positive;
import se.sics.kompics.Start;
import se.sics.kompics.timer.ScheduleTimeout;
import se.sics.kompics.timer.Timer;

public class Application3 extends ComponentDefinition {

	Positive<Timer> timer = positive(Timer.class);
	Positive<AtomicRegisterLink> arl = positive(AtomicRegisterLink.class);

	private static final Logger logger = LoggerFactory
			.getLogger(Application3.class);

	private String[] commands;
	private int lastCommand;
	
	public Application3() {
		subscribe(handleInit, control);
		subscribe(handleStart, control);
		subscribe(handleContinue, timer);
		subscribe(handleReadResponse, arl);
		subscribe(handleWriteResponse, arl);
	}

	Handler<Application3Init> handleInit = new Handler<Application3Init>() {
		public void handle(Application3Init event) {
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
	
	Handler<ReadResponse> handleReadResponse = new Handler<ReadResponse>() {

		@Override
		public void handle(ReadResponse event) {
			logger.info("Response: Read register[{}]={}", event.getRegister(), event.getValue());
			doNextCommand();
		}
		
	};
	
	Handler<WriteResponse> handleWriteResponse = new Handler<WriteResponse>() {

		@Override
		public void handle(WriteResponse event) {
			logger.info("Response: Write register[{}]", event.getRegister());
			doNextCommand();
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
		if (cmd.startsWith("W")) {
			doWrite(0, Integer.parseInt(cmd.substring(1)));
		} else if (cmd.startsWith("D")) {
			doSleep(Integer.parseInt(cmd.substring(1)));
		} else if (cmd.startsWith("X")) {
			doShutdown();
		} else if (cmd.equals("help")) {
			doHelp();
			doNextCommand();
		} else if(cmd.startsWith("R")) {
			doRead(0);
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

	private final void doWrite(int r, int v) {
		logger.info("Request: Write register[{}]={}", r, v);
		trigger(new WriteRequest(r, v), arl);
	}
	
	private final void doRead(int r) {
		logger.info("Request: Read register[{}]", r);
		trigger(new ReadRequest(r), arl);
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
