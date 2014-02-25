package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.service.PolicyMediaService;
import com.yunling.policyman.db.service.impl.base.BasePolicyMediaServiceImpl;

public class PolicyMediaServiceImpl
	extends BasePolicyMediaServiceImpl
	implements PolicyMediaService
{

	@Override
	public List<PolicyMedia> listByPolicy(long id) {
		return getMapper().listByPolicy(id);
	}
	
	@Override
	public List<PolicyMedia> listVideoByPolicy(long id) {
		return getMapper().listVideoByPolicy(id);
	}
	
}