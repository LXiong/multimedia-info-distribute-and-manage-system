package com.yunling.logparse;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.commons.console.ConsoleExecutor;
import com.yunling.commons.console.ConsoleMain;

public class LogParse implements ConsoleMain {
	private Logger log = LoggerFactory.getLogger(LogParse.class);

	private String logBasePath;
	private int parserThreadCount;
	private MacLoop macLoop;
	private BlockingQueue<MacDir> macQueue;
	private MacHandler handler;

	private Thread mainThread;
	private Thread macLoopThread;
	private ParserRunner[] parserRunners;

	@Override
	public void init(String[] args, Map<String, Object> context)
			throws Exception {
		log.debug("[init]");
		mainThread = Thread.currentThread();
		macLoopThread = new Thread(macLoop);
		parserRunners = new ParserRunner[parserThreadCount];
		macLoop.setRunners(parserRunners);
		for(int i=0; i< parserThreadCount; ++i) {
			parserRunners[i] = new ParserRunner(macQueue, handler);
		}
	}

	@Override
	public void release() throws Exception {
		log.debug("[release]");
		macLoop.setRunFlag(false);
		for (ParserRunner runner : parserRunners) {
			runner.setRunFlag(false);
		}
		macQueue.clear();
	}

	@Override
	public void run(Map<String, Object> context) throws Exception {
		log.info("[start]run");
		macLoopThread.start();
		for (ParserRunner runner : parserRunners) {
			Thread th = new Thread(runner);
			th.start();
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			log.error("Main thread is interrupted");
		}
		log.info("[end]run");
	}

	public void interrupt() {
		if (this.mainThread != null) {
			mainThread.interrupt();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] paths = { "conf/applicationContextLogParse.xml",
				"conf/applicationContextService.xml" };
		ConsoleExecutor.execute("logParse", paths, args);
	}

	public String getLogBasePath() {
		return logBasePath;
	}

	public void setLogBasePath(String logBasePath) {
		this.logBasePath = logBasePath;
	}

	public int getParserThreadCount() {
		return parserThreadCount;
	}

	public void setParserThreadCount(int parserThreadCount) {
		this.parserThreadCount = parserThreadCount;
	}

	public MacLoop getMacLoop() {
		return macLoop;
	}

	public void setMacLoop(MacLoop macLoop) {
		this.macLoop = macLoop;
	}

	public BlockingQueue<MacDir> getMacQueue() {
		return macQueue;
	}

	public void setMacQueue(BlockingQueue<MacDir> macQueue) {
		this.macQueue = macQueue;
	}

	public MacHandler getHandler() {
		return handler;
	}

	public void setHandler(MacHandler handler) {
		this.handler = handler;
	}

}
