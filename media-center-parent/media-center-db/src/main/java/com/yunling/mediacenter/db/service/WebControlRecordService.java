package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WebControlRecord;


public interface WebControlRecordService {
	public void insertRecord(String stbIp, String command, String customerName,
			String stbMac, String watchServerIp, String watchServerPort);
	
	/**
	 * get logs by time Descending
	 * @return
	 */
	public List<WebControlRecord> getLogs(WebControlRecord webControlRecord, 
			int begin, int end, String from, String to);
	public Count count(WebControlRecord webControlRecord, String from, String to);
	public Integer getPageSize();
	public List<String> getCommands();
}
