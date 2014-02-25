package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.service.StbGroupService;
import com.yunling.policyman.db.service.impl.base.BaseStbGroupServiceImpl;

public class StbGroupServiceImpl
	extends BaseStbGroupServiceImpl
	implements StbGroupService
{
	@Override
	public List<StbGroup> listByPublishedPolicy(Long policyId) {
		return getMapper().listByPublishedPolicy(policyId);
	}

}