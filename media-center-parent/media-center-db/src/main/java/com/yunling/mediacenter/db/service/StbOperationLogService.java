/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbOperationLog;

/**
 * @author LingJun
 * @date 2010-11-23
 * 
 */
public interface StbOperationLogService {
	public void addLog(StbOperationLog stbOperaLog);
	/**
	 * get logs by time Descending
	 * @return
	 */
	public List<StbOperationLog> getLogs(StbOperationLog stbOperaLog, int begin, 
			int end, String from, String to);
	public Count count(StbOperationLog stbOperaLog, String from, String to);
	public Integer getPageSize();
	
	public List<String> getCommands();
	public List<String> getResults();
}
