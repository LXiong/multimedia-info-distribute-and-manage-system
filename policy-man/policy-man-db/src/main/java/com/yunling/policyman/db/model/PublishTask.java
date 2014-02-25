package com.yunling.policyman.db.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yunling.policyman.db.model.base.BasePublishTask;

public class PublishTask extends BasePublishTask {
	
	public PublishTask(){
		
	}

	public PublishTask(Policy policy, String[] stbGroupIdArr, Date publishTime) {
		this.policyId = policy.getId();
		this.publishAreas = StringUtils.join(stbGroupIdArr, "-");
		this.publishTime = publishTime;
	}

}