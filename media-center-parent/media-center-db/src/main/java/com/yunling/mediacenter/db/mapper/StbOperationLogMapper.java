/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbOperationLog;

/**
 * @author LingJun
 * @date 2010-11-24
 * 
 */
@SuppressWarnings("rawtypes")
public interface StbOperationLogMapper {
	public void addLog(StbOperationLog stbOperaLog);
	
	public List<StbOperationLog> getLogs(Map map);
	public Count count(Map map);
	
	public List<String> getCommands();
	public List<String> getResults();
}
