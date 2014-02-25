package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.AuthenticateRecordMapper;
import com.yunling.mediacenter.db.model.AuthenticateRecord;
import com.yunling.mediacenter.db.service.AuthenticateRecordService;

public class AuthenticateRecordServiceImpl extends BaseServiceImpl implements AuthenticateRecordService {
	
	private AuthenticateRecordMapper getMapper() {
		return getMapper(AuthenticateRecordMapper.class);
	}
	public void insertRecord(final String stbIp, final String customerName, final String stbMac, final String authStatus,
			final String watchServerIp, final String watchServerPort){
		getMapper().insert(stbIp, customerName, stbMac, authStatus, watchServerIp, watchServerPort);
	}

	@Override
	public int count(final AuthenticateRecord authenticateRecord, final String from, 
			final String to) {
		return getMapper().count(authenticateRecord, from, to );
	}

	@Override
	public List<AuthenticateRecord> getLogs(final AuthenticateRecord authenticateRecord,
			final int begin, final int end, final String from, final String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("from", from);
		map.put("to", to);
		map.put("authenticateRecord", authenticateRecord);
		return getMapper().getLogs(map);
	}
	
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
