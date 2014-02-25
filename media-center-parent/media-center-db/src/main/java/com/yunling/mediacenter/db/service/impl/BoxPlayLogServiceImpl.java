package com.yunling.mediacenter.db.service.impl;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.BoxPlayLogMapper;
import com.yunling.mediacenter.db.model.BoxPlayLog;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.VideoPlayTimes;
import com.yunling.mediacenter.db.service.BoxPlayLogService;

public class BoxPlayLogServiceImpl extends BaseServiceImpl implements
		BoxPlayLogService {
	private BoxPlayLogMapper getMapper() {
		return getMapper(BoxPlayLogMapper.class);
	}

	public void add(final BoxPlayLog box) {
		getMapper().add(box);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxPlayLogService#countList(java.lang.Long)
	 */
	@Override
	public int countList(Long groupId) {
		return getMapper().countList(groupId);
	}

	public List<Statistics> list(Map<String, Object> map) {
		return getMapper().list(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.BoxPlayLogService#countBy(java.util
	 * .Map)
	 */
	@Override
	public int countBy(Map<String, Object> map) {
		return getMapper().countBy(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.BoxPlayLogService#getPlayHistReport
	 * (java.util.Map)
	 */
	@Override
	public List<BoxPlayLog> getPlayHistReport(Map<String, Object> map) {
		return getMapper().getPlayHistReport(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.BoxPlayLogService#countBy2(java.util
	 * .Map)
	 */
	@Override
	public int countBy2(Map<String, Object> map) {
		return getMapper().countBy2(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.BoxPlayLogService#playTimesReport(
	 * java.util.Map)
	 */
	@Override
	public List<VideoPlayTimes> playTimesReport(Map<String, Object> map) {
		return getMapper().playTimesReport(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxPlayLogService#getAllPlayHistReport(java.util.Map)
	 */
	@Override
	public List<BoxPlayLog> getAllPlayHistReport(Map<String, Object> map) {
		return getMapper().getAllPlayHistReport(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxPlayLogService#getAllPlayTimesReport(java.util.Map)
	 */
	@Override
	public List<VideoPlayTimes> getAllPlayTimesReport(Map<String, Object> map) {
		return getMapper().getAllPlayTimesReport(map);
	}

}
