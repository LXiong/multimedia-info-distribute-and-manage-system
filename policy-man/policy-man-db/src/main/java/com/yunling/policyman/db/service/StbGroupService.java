package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.service.base.BaseStbGroupService;

public interface StbGroupService
	extends BaseStbGroupService
{

	List<StbGroup> listByPublishedPolicy(Long policyId);
}