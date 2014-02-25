package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.mapper.TimedLayoutMapper;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.service.TimedLayoutService;
import com.yunling.policyman.db.service.impl.base.BaseTimedLayoutServiceImpl;

public class TimedLayoutServiceImpl
	extends BaseTimedLayoutServiceImpl
	implements TimedLayoutService
{

	@Override
	public List<TimedLayout> listByPolicy(long id) {
		return getMapper(TimedLayoutMapper.class).listByPolicy(id);
	}
	
}