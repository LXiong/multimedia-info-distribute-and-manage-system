package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WatcherReportRecord;


public interface WatcherReportRecordService {
	public void insertRecord(String mac, String stbIp, String stbPort,
			String watchServerIp, String watchServerPort);
	
	/**
	 * get logs by time Descending
	 * @return
	 */
	public List<WatcherReportRecord> getLogs(WatcherReportRecord watcherReportRecord, 
			int begin, int end, String from, String to);
	public Count count(WatcherReportRecord watcherReportRecord, String from, String to);
	public Integer getPageSize();
}
