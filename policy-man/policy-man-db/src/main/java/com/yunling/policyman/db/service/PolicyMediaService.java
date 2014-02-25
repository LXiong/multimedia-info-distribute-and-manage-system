package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.service.base.BasePolicyMediaService;

public interface PolicyMediaService
	extends BasePolicyMediaService
{

	List<PolicyMedia> listByPolicy(long id);
	List<PolicyMedia> listVideoByPolicy(long id);
}