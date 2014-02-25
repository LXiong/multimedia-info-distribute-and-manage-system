/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.StbOperationLogMapper;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbOperationLog;
import com.yunling.mediacenter.db.service.StbOperationLogService;

/**
 * @author LingJun
 * @date 2010-11-24
 * 
 */
public class StbOperationLogServiceImpl extends BaseServiceImpl implements
		StbOperationLogService {
	private StbOperationLogMapper getMapper() {
		return getMapper(StbOperationLogMapper.class);
	}
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbOperaLogService#addLog(com.yunling.mediacenter.db.model.StbOperationLog)
	 */
	@Override
	public void addLog(final StbOperationLog stbOperaLog) {
		getMapper().addLog(stbOperaLog);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbOperationLogService#getLogs()
	 */
	@Override
	public List<StbOperationLog> getLogs(final StbOperationLog stbOperaLog, 
			final int begin, final int end, final String from, final String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("from", from);
		map.put("to", to);
		map.put("stbOperaLog", stbOperaLog);
		return getMapper().getLogs(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbOperationLogService#countStbs(com.yunling.mediacenter.db.model.StbOperationLog)
	 */
	@Override
	public Count count(final StbOperationLog stbOperaLog, final String from, 
			final String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stbOperaLog", stbOperaLog);
		map.put("from", from);
		map.put("to", to);
		return getMapper().count(map);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbOperationLogService#getCommands()
	 */
	@Override
	public List<String> getCommands() {
		return getMapper().getCommands();
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbOperationLogService#getResults()
	 */
	@Override
	public List<String> getResults() {
		return getMapper().getResults();
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	private Integer pageSize;
}
