package com.yunling.policyman.db.service.impl;

import java.util.List;
import java.util.Map;

import com.yunling.policyman.db.mapper.VideoMapper;
import com.yunling.policyman.db.model.Video;
import com.yunling.policyman.db.service.VideoService;
import com.yunling.policyman.db.service.impl.base.BaseVideoServiceImpl;

public class VideoServiceImpl extends BaseVideoServiceImpl implements
		VideoService {

	@Override
	public List<Video> listByPage(int begin, int end) {
		return getMapper(VideoMapper.class).listByPage(begin, end);
	}

	@Override
	public int count() {
		return getMapper(VideoMapper.class).count();
	}

	@Override
	public List<Video> search(String tag) {
		return getMapper(VideoMapper.class).search(tag);
	}

	@Override
	public List<Video> listAllByCodes(String[] codes) {
		return getMapper(VideoMapper.class).listAllByCodes(codes);
	}

	@Override
	public int countWaitingMoved() {
		return getMapper(VideoMapper.class).countWaitingMoved();
	}

	@Override
	public List<Video> listWaitingMoved() {
		return getMapper(VideoMapper.class).listWaitingMoved();
	}

	@Override
	public List<Video> listByPolicy(long policyId) {
		return getMapper(VideoMapper.class).listByPolicy(policyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.policyman.db.service.VideoService#countByNormal(java.util
	 * .Map)
	 */
	@Override
	public int countByNormal(Map<String, Object> params) {
		return getMapper(VideoMapper.class).countByNormal(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.policyman.db.service.VideoService#listByNormal(java.util.Map)
	 */
	@Override
	public List<Video> listByNormal(Map<String, Object> params) {
		return getMapper(VideoMapper.class).listByNormal(params);
	}

	@Override
	public int countByReview() {
		return getMapper(VideoMapper.class).countByReview();
	}

	@Override
	public List<Video> listByReview(Map<String, Object> params) {
		return getMapper(VideoMapper.class).listByReview(params);
	}
}