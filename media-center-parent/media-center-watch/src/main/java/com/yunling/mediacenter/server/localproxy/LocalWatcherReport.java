package com.yunling.mediacenter.server.localproxy;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.server.remote.WatcherReport;

public class LocalWatcherReport implements WatcherReport {
	WatcherReport reporter;
	Timer timer = new Timer();
	Logger log = LoggerFactory.getLogger(LocalWatcherReport.class);
	@Override
	public String refresh(String watcherIp, String watcherOutIp, int port) {
		try{
			log.info("begin refresh watcher state");
			reporter.refresh(watcherIp, watcherOutIp, port);
			log.info("end refresh watcher state");
		}catch(Exception e){
			// do nothing
			log.info("error occur when refresh watcher state");
		}
		return "s";
	}

	@Override
	public String register(String watcherIp, String watcherInIp, int port) {
		timer.schedule(new WatcherTask("register", watcherIp, watcherInIp, String
				.valueOf(port)) {
			@Override
			public void business() {
				reporter.register(params[1], params[2], Integer
						.valueOf(params[3]));
			}
		}, 0, 60 * 1000);
		return "s";
	}

	@Override
	public String stbLogin(String watcherIp, String mac, String stbIp,
			String stbPort) {
		timer.schedule(new WatcherTask("stbLogin",watcherIp, mac, stbIp, stbPort) {
			@Override
			public void business() {
				reporter.stbLogin(params[1], params[2], params[3], params[4]);
			}
		}, 0, 60 * 1000);
		return "s";
	}

	@Override
	public String stbLogoff(String watcherIp, String mac) {
		timer.schedule(new WatcherTask("stbLogoff", watcherIp, mac) {
			@Override
			public void business() {
				reporter.stbLogoff(params[1], params[2]);
			}
		}, 0, 60 * 1000);
		return "s";
	}

	@Override
	public String unregister(String watcherIp, int port) {
		timer.schedule(new WatcherTask("unregister", watcherIp, String.valueOf(port)) {
			@Override
			public void business() {
				reporter.unregister(params[1], Integer.valueOf(params[2]));
			}
		}, 0, 60 * 1000);
		return "s";
	}

	class WatcherTask extends TimerTask {
		String[] params;
		String action;
		boolean loop = true;

		public WatcherTask(String... strings) {
			params = strings;
		}

		@Override
		public void run() {
			if (loop) {
				try {
					log.info("begin invoke " + params[0]);
					business();
				} catch (Exception e) {
					log.info("error occur when invoke " + params[0]);
					return;
				}
				log.info("end invoke " + params[0]);
				this.cancel();
			}
		}

		public void business() {
		};
		
		public WatcherTask setAction(String action){
			this.action = action;
			return this;
		}
	}

	public WatcherReport getReporter() {
		return reporter;
	}

	public void setReporter(WatcherReport report) {
		this.reporter = report;
	}

}
