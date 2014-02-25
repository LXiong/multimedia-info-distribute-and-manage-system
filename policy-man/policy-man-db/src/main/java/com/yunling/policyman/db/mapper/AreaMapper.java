package com.yunling.policyman.db.mapper;

import java.util.List;

import com.yunling.policyman.db.mapper.base.BaseAreaMapper;
import com.yunling.policyman.db.model.Area;

public interface AreaMapper
	extends BaseAreaMapper
{

	void deleteByLayoutId(long layoutid);

	List<Area> getByLayoutId(long layoutid);

}
