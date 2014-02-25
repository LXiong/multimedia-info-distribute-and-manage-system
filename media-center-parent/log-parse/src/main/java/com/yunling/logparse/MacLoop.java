package com.yunling.logparse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacLoop implements Runnable {
	private Logger log = LoggerFactory.getLogger(MacLoop.class);
	private String logPath;
	private BlockingQueue<MacDir> macQueue;
	private boolean runFlag;
	private ParserRunner[] runners;

	public void run() {
		if (macQueue == null) {
			throw new RuntimeException("The queue is not set");
		}
		log.debug("[start]MacLoop.run");
		runFlag = true;
		while (runFlag) {
			while (macQueue.size() > 0) {
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
				}
			}
			List<MacDir> macList = collectMacDir();

			if (macList.size() == 0) {
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
				}
			} else {
				macQueue.addAll(macList);
			}
		}
		log.debug("[end]MacLoop.run");
	}

	public List<MacDir> collectMacDir() {
		// log.debug("[start]loop");
		File dir = new File(logPath);
		File[] subList = dir.listFiles();
		List<MacDir> macDirList = new ArrayList<MacDir>();
		if (subList == null) {
			return macDirList;
		}
		for (File sub : subList) {
			if (sub.isDirectory()) {
				File[] filesInSub = sub.listFiles();
				if (filesInSub == null || filesInSub.length == 0) {
					continue;
				}
				String mac = sub.getName();
				boolean used = false;
				for (ParserRunner runner : runners) {
					if (runner.getMacDir() != null
							&& StringUtils.equals(runner.getMacDir().getMac(),
									mac)) {
						used = true;
						break;
					}
				}
				if (used) {
					continue;
				}
				MacDir macDir = new MacDir();
				macDir.setMac(mac);
				macDir.setMacDir(sub);
				macDirList.add(macDir);
			}
		}
		// log.debug("[end]loop");
		return macDirList;
	}

	public BlockingQueue<MacDir> getMacQueue() {
		return macQueue;
	}

	public void setMacQueue(BlockingQueue<MacDir> macQueue) {
		this.macQueue = macQueue;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public void setRunners(ParserRunner[] runners) {
		this.runners = runners;
	}
}
