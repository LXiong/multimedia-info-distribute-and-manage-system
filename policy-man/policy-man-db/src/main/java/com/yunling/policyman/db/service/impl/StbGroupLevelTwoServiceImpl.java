package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.service.StbGroupLevelTwoService;
import com.yunling.policyman.db.service.impl.base.BaseStbGroupLevelTwoServiceImpl;

public class StbGroupLevelTwoServiceImpl
	extends BaseStbGroupLevelTwoServiceImpl
	implements StbGroupLevelTwoService
{

	@Override
	public List<StbGroupLevelTwo> listByGroupType(StbGroup group) {
		
		return getMapper().listByGroupType(group);
	}

}