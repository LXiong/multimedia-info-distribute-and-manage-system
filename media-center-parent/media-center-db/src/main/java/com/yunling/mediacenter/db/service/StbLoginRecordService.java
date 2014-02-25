package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbLoginRecord;


public interface StbLoginRecordService {
	public void insertRecord(String type, String mac, String stbIp, String stbPort,
			String watchServerIp, String watchServerPort, String status);
	
	public List<StbLoginRecord> lastLoginSuccessOn(String watchIp);
	/**
	 * get logs by time Descending
	 * @return
	 */
	public List<StbLoginRecord> getLogs(StbLoginRecord stbLoginRecord, 
			int begin, int end, String from, String to);
	public Count count(StbLoginRecord stbLoginRecord, String from, String to);
	public Integer getPageSize();
	
	public List<String> getResults();
}
