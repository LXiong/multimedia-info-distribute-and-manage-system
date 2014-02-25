package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.service.base.BaseStbGroupLevelTwoService;

public interface StbGroupLevelTwoService
	extends BaseStbGroupLevelTwoService
{
	public List<StbGroupLevelTwo> listByGroupType(StbGroup group);
}