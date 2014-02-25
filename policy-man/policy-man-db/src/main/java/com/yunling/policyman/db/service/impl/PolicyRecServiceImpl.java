package com.yunling.policyman.db.service.impl;

import com.yunling.policyman.db.model.PolicyRec;
import com.yunling.policyman.db.service.PolicyRecService;
import com.yunling.policyman.db.service.impl.base.BasePolicyRecServiceImpl;

public class PolicyRecServiceImpl
	extends BasePolicyRecServiceImpl
	implements PolicyRecService
{

	@Override
	public void addNew(PolicyRec prec) {
		getMapper().addPolicy(prec);
	}

}