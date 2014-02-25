package com.yunling.logparse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserRunner implements Runnable {

	private Logger log = LoggerFactory.getLogger(ParserRunner.class);
	private BlockingQueue<MacDir> macQueue;
	private MacHandler macHandler;
	private boolean runFlag;
	private MacDir macDir; 
	
	public ParserRunner(BlockingQueue<MacDir> queue, MacHandler handler) {
		this.macQueue = queue;
		this.macHandler = handler;
	}
	
	@Override
	public void run() {
		runFlag = true;
		log.debug("[start]ParserRunner.run");
		while (runFlag) {
			macDir = null;
			try {
				macDir = macQueue.poll(10, TimeUnit.SECONDS);
				if (macDir != null) {
					macHandler.processLog(macDir);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.debug("[end]ParserRunner.run");
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public MacDir getMacDir() {
		return macDir;
	}

}
