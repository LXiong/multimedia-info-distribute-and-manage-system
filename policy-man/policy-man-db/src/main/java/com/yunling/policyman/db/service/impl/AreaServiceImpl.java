package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.mapper.AreaMapper;
import com.yunling.policyman.db.model.Area;
import com.yunling.policyman.db.service.AreaService;
import com.yunling.policyman.db.service.impl.base.BaseAreaServiceImpl;

public class AreaServiceImpl
	extends BaseAreaServiceImpl
	implements AreaService
{

	@Override
	public void deleteByLayoutId(long layoutid) {
		getMapper(AreaMapper.class).deleteByLayoutId(layoutid);
	}

	@Override
	public List<Area> getByLayoutId(long layoutid) {
		return getMapper(AreaMapper.class).getByLayoutId(layoutid);
	}
}