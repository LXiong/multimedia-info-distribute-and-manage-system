package com.yunling.policyman.db.mapper;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseTimedListMapper;

public interface TimedListMapper
	extends BaseTimedListMapper
{
	void deleteByLayout(@Param("layout_id") Long id);

	void deleteByArea(@Param("area_id")Long id);

	void deleteByPolicy(@Param("policy_id")Long id);
}
