package com.yunling.policyman.db.service;

import java.util.List;
import java.util.Map;

import com.yunling.policyman.db.model.Video;
import com.yunling.policyman.db.service.base.BaseVideoService;

public interface VideoService extends BaseVideoService {
	public List<Video> listByPage(int begin, int end);

	public int count();

	public List<Video> search(String tag);

	public List<Video> listAllByCodes(String[] codes);

	public int countWaitingMoved();

	public List<Video> listWaitingMoved();

	public List<Video> listByPolicy(long policyId);

	public int countByNormal(Map<String, Object> params);

	public List<Video> listByNormal(Map<String, Object> params);

	public int countByReview();

	public List<Video> listByReview(Map<String, Object> params);
}