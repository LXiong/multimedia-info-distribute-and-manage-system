package com.yunling.policyman.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseVideoMapper;
import com.yunling.policyman.db.model.Video;

public interface VideoMapper extends BaseVideoMapper {
	public List<Video> listByPage(@Param(value = "begin") int begin,
			@Param(value = "end") int end);

	public int count();

	public List<Video> search(String tag);

	public List<Video> listAllByCodes(String[] codes);

	public int countWaitingMoved();

	public List<Video> listWaitingMoved();

	public List<Video> listByPolicy(@Param("policy_id") long policyId);

	public int countByNormal(Map<String, Object> params);

	public List<Video> listByNormal(Map<String, Object> params);

	public int countByReview();

	public List<Video> listByReview(Map<String, Object> params);
}
