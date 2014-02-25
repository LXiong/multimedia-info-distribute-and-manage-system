package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.StbLoginRecordMapper;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbLoginRecord;
import com.yunling.mediacenter.db.service.StbLoginRecordService;

public class StbLoginRecordServiceImpl extends BaseServiceImpl implements StbLoginRecordService {

	private StbLoginRecordMapper getMapper() {
		return getMapper(StbLoginRecordMapper.class);
	}
	@Override
	public void insertRecord(final String type, final String mac, final String stbIp, final String stbPort,
			final String watchServerIp, final String watchServerPort, final String status) {
		getMapper().insertRecord(type, mac, stbIp, stbPort, watchServerIp, watchServerPort, status);
	}
	
	@Override
	public List<StbLoginRecord> lastLoginSuccessOn(String watchIp){
		return getMapper().lastLoginSuccessOn(watchIp);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbLoginRecordService#count(com.yunling.mediacenter.db.model.StbLoginRecord)
	 */
	@Override
	public Count count(final StbLoginRecord stbLoginRecord, final String from, 
			final String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stbLoginRecord", stbLoginRecord);
		map.put("from", from);
		map.put("to", to);
		return getMapper().count(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbLoginRecordService#getLogs(com.yunling.mediacenter.db.model.StbLoginRecord, int, int)
	 */
	@Override
	public List<StbLoginRecord> getLogs(final StbLoginRecord stbLoginRecord,
			final int begin, final int end, final String from, final String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("from", from);
		map.put("to", to);
		map.put("stbLoginRecord", stbLoginRecord);
		return getMapper().getLogs(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.StbLoginRecordService#getResults()
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
