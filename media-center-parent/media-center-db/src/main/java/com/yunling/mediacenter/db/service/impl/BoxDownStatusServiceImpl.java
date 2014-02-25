/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.BoxDownStatusMapper;
import com.yunling.mediacenter.db.model.BoxDownStatus;
import com.yunling.mediacenter.db.model.GroupDownStatusReport;
import com.yunling.mediacenter.db.model.StbDownStatusReport;
import com.yunling.mediacenter.db.service.BoxDownStatusService;

/**
 * @author LingJun
 * @date 2010-12-28
 * 
 */
public class BoxDownStatusServiceImpl extends BaseServiceImpl implements
		BoxDownStatusService {
	
	private BoxDownStatusMapper getMapper() {
		return getMapper(BoxDownStatusMapper.class);
	}
//	@Override
//	public void insert(final BoxDownStatus downStatus) {
//		getMapper().insert(downStatus);
//	}
//
//	@Override
//	public void update(final BoxDownStatus downStatus) {
//		getMapper().update(downStatus);
//	}
	
	@Override
	public void insertOrUpdate(final BoxDownStatus downStatus) {
		getMapper().mergeStatus(downStatus);
	}

	@Override
	public Long getFinishedNum(final BoxDownStatus downStatus) {
		return getMapper().getFinishedNum(downStatus);
	}

	@Override
	public List<BoxDownStatus> getMacs(final BoxDownStatus downStatus) {
		return getMapper().getMacs(downStatus);
	}

	@Override
	public List<BoxDownStatus> getList(final BoxDownStatus downStatus) {
		return getMapper().getList(downStatus);
	}

	@Override
	public BoxDownStatus getDownStatus(final BoxDownStatus downStatus) {
		return getMapper().getDownStatus(downStatus);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#getReport(java.util.Map)
	 */
	@Override
	public List<GroupDownStatusReport> getReport(final Map<String, Object> map) {
		return getMapper().getReport(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#countBy(java.util.Map)
	 */
	@Override
	public int countBy(Map<String, Object> map) {
		return getMapper().countBy(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#countBy2(java.util.Map)
	 */
	@Override
	public int countBy2(Map<String, Object> map) {
		return getMapper().countBy2(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#getReport2(java.util.Map)
	 */
	@Override
	public List<GroupDownStatusReport> getReport2(Map<String, Object> map) {
		return getMapper().getReport2(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#countForDownStat(java.util.Map)
	 */
	@Override
	public int countForDownStat(Map<String, Object> map) {
		return getMapper().countForDownStat(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#stbDownStat(java.util.Map)
	 */
	@Override
	public List<StbDownStatusReport> stbDownStat(Map<String, Object> map) {
		return getMapper().stbDownStat(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#allDownStatus(java.util.Map)
	 */
	@Override
	public List<GroupDownStatusReport> allDownStatus(Map<String, Object> map) {
		return getMapper().allDownStatus(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#allDownStatus2(java.util.Map)
	 */
	@Override
	public List<GroupDownStatusReport> allDownStatus2(Map<String, Object> map) {
		return getMapper().allDownStatus2(map);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.BoxDownStatusService#stbAllDownStat(java.util.Map)
	 */
	@Override
	public List<StbDownStatusReport> stbAllDownStat(Map<String, Object> map) {
		return getMapper().stbAllDownStat(map);
	}

}
