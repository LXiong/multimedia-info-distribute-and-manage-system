package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.WatcherReportRecordMapper;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WatcherReportRecord;
import com.yunling.mediacenter.db.service.WatcherReportRecordService;

public class WatcherReportRecordServiceImpl extends BaseServiceImpl implements
		WatcherReportRecordService {
	private WatcherReportRecordMapper getMapper() {
		return getMapper(WatcherReportRecordMapper.class);
	}
	@Override
	public void insertRecord(final String mac, final String stbIp, final String stbPort,
			final String watchServerIp, final String watchServerPort) {
		getMapper().insertRecord(mac, stbIp, stbPort, watchServerIp, watchServerPort);
	}
	
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.WatcherReportRecordService#count(com.yunling.mediacenter.db.model.WatcherReportRecord)
	 */
	@Override
	public Count count(final WatcherReportRecord watcherReportRecord, 
			final String from, final String to) {
		final Map<String,Object> map = new HashMap<String,Object> ();
		map.put("watcherReportRecord", watcherReportRecord);
		map.put("from", from);
		map.put("to", to);
		return getMapper().count(map);
	}


	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.WatcherReportRecordService#getLogs(com.yunling.mediacenter.db.model.WatcherReportRecord, int, int)
	 */
	@Override
	public List<WatcherReportRecord> getLogs(
			final WatcherReportRecord watcherReportRecord, final int begin, 
			final int end, final String from, final String to) {
		final Map<String,Object> map = new HashMap<String,Object> ();
		map.put("begin", begin);
		map.put("end", end);
		map.put("from", from);
		map.put("to", to);
		map.put("watcherReportRecord", watcherReportRecord);
		return getMapper().getLogs(map);
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
