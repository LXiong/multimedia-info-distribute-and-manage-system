package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.WebControlRecordMapper;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WebControlRecord;
import com.yunling.mediacenter.db.service.WebControlRecordService;

public class WebControlRecordServiceImpl extends BaseServiceImpl implements WebControlRecordService {
	private WebControlRecordMapper getMapper() {
		return getMapper(WebControlRecordMapper.class);
	}
	@Override
	public void insertRecord(final String stbIp, final String command, final String customerName,
			final String stbMac, final String watchServerIp, final String watchServerPort) {
		getMapper().insertRecord(stbIp, command, customerName, stbMac, watchServerIp, watchServerPort);

	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.WebControlRecordService#count(com.yunling.mediacenter.db.model.WebControlRecord)
	 */
	@Override
	public Count count(final WebControlRecord webControlRecord, 
			final String from, final String to) {
		final Map<String,Object> map = new HashMap<String,Object> ();
		map.put("webControlRecord", webControlRecord);
		map.put("from", from);
		map.put("to", to);
		return getMapper().count(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.WebControlRecordService#getLogs(com.yunling.mediacenter.db.model.WebControlRecord, int, int)
	 */
	@Override
	public List<WebControlRecord> getLogs(final WebControlRecord webControlRecord,
			final int begin, final int end, final String from, final String to) {
		final Map<String,Object> map = new HashMap<String,Object> ();
		map.put("begin", begin);
		map.put("end", end);
		map.put("from", from);
		map.put("to", to);
		map.put("webControlRecord", webControlRecord);
		return getMapper().getLogs(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.WebControlRecordService#getCommands()
	 */
	@Override
	public List<String> getCommands() {
		return getMapper().getCommands();
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
