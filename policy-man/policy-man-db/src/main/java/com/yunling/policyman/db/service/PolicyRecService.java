package com.yunling.policyman.db.service;

import com.yunling.policyman.db.model.PolicyRec;
import com.yunling.policyman.db.service.base.BasePolicyRecService;

public interface PolicyRecService
	extends BasePolicyRecService
{

	void addNew(PolicyRec prec);
}