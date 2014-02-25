package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseTimedLayoutMapper;
import com.yunling.policyman.db.model.TimedLayout;

public interface TimedLayoutMapper
	extends BaseTimedLayoutMapper
{

	List<TimedLayout> listByPolicy(@Param("policy_id") long id);

	void deleteByPolicy(@Param("policy_id") long id);

}
