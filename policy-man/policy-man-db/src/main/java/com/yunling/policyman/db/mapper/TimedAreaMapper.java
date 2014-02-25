package com.yunling.policyman.db.mapper;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseTimedAreaMapper;

public interface TimedAreaMapper
	extends BaseTimedAreaMapper
{

	void deleteByLayout(@Param("layout_id") Long id);

	void deleteByPolicy(@Param("policy_id") long id);

}
