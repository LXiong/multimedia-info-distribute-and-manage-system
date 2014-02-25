package com.yunling.policyman.db.mapper;

import com.yunling.policyman.db.mapper.base.BasePolicyRecMapper;
import com.yunling.policyman.db.model.PolicyRec;

public interface PolicyRecMapper
	extends BasePolicyRecMapper
{

	void addPolicy(PolicyRec prec);

}