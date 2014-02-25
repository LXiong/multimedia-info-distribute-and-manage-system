package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.service.base.BaseTimedLayoutService;

public interface TimedLayoutService
	extends BaseTimedLayoutService
{

	List<TimedLayout> listByPolicy(long id);

}