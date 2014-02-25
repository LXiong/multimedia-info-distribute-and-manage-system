package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.AuthenticateRecord;

public interface AuthenticateRecordService {
	public void insertRecord(String stbIp, String customerName, String stbMac, String authStatus,
			String watchServerIp, String watchServerPort);
	
	/**
	 * get logs by time Descending
	 * @return
	 */
	public List<AuthenticateRecord> getLogs(AuthenticateRecord stbOperaLog, 
			int begin, int end, String from, String to);
	public int count(AuthenticateRecord authenticateRecord, String from, String to);
	public Integer getPageSize();
	public List<String> getResults();
}
