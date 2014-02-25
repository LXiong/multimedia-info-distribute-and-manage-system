package com.yunling.mediacenter.server.remote.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianServlet;
import com.yunling.mediacenter.db.model.WatchServerStatus;
import com.yunling.mediacenter.db.service.WatcherReportRecordService;
import com.yunling.mediacenter.server.remote.WatcherReport;
import com.yunling.mediacenter.utils.Statistics;

public class WatcherReportImpl extends HessianServlet implements WatcherReport {
	private Logger log = LoggerFactory.getLogger(WatcherReportImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient Statistics statistics;
	transient WatcherReportRecordService watcherReportRecordService;

	public WatcherReportImpl(Statistics s, WatcherReportRecordService watcherReportRecordService) {
		this.setStatistics(s);
		this.setWatcherReportRecordService(watcherReportRecordService);
	}

	@Override
	public String register(String watcherOutIp, String watcherInIp, int port) {
		return statistics.registerWatcher(watcherOutIp, watcherInIp, port);
	}

	@Override
	public String unregister(String watcherIp, int port) {
		return statistics.unregisterWatcher(watcherIp, port);
	}

	@Override
	public String stbLogin(String watcherIp, String mac, String stbIp, String stbPort) {
		//record
		WatchServerStatus wss = statistics.getWatchServerStatusByIp(watcherIp);
		watcherReportRecordService.insertRecord(mac, stbIp, stbPort, watcherIp, String.valueOf(wss.getWatchPort()));
		return statistics.increaseWatcherLogin(watcherIp, mac);
	}

	@Override
	public String stbLogoff(String watcherIp, String mac) {
		return statistics.decreaseWatcherLogin(watcherIp, mac);
	}

	@Override
	public String refresh(String watcherIp, String outIp, int port) {
		log.info(watcherIp + " is refresh it`s state");
		String result = statistics.refreshStatus(watcherIp, outIp, port);
		log.info(watcherIp + " has refreshed it`s state");
		return result;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public void setWatcherReportRecordService(
			WatcherReportRecordService watcherReportRecordService) {
		this.watcherReportRecordService = watcherReportRecordService;
	}

}
