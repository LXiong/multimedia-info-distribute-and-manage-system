package com.yunling.mediaman.uploadwatch;

import java.util.Map;

import org.eclipse.jetty.server.Server;

import com.yunling.commons.console.ConsoleExecutor;
import com.yunling.commons.console.ConsoleMain;

public class UploadWatch implements ConsoleMain {
	private WatchRunner watchRunner;
	private MoveRunner moveRunner;
	private int port = 2096;
	private Server server;

	@Override
	public void init(String[] args, Map<String, Object> context)
			throws Exception {
	}

	@Override
	public void run(Map<String, Object> context) throws Exception {
		Thread th = new Thread(watchRunner);
		th.start();
		th = new Thread(moveRunner);
		th.start();
		server.start();
		server.join();
	}

	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void release() throws Exception {
	}

	public static void main(String[] args) {
		String[] paths = { "conf/applicationContextUploadWatch.xml" };
		ConsoleExecutor.execute("uploadWatch", paths, args);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public WatchRunner getWatchRunner() {
		return watchRunner;
	}

	public void setWatchRunner(WatchRunner watchRunner) {
		this.watchRunner = watchRunner;
	}

	public MoveRunner getMoveRunner() {
		return moveRunner;
	}

	public void setMoveRunner(MoveRunner moveRunner) {
		this.moveRunner = moveRunner;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
