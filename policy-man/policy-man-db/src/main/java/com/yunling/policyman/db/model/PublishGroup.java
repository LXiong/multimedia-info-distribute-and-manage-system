package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BasePublishGroup;

public class PublishGroup extends BasePublishGroup {

	public PublishGroup() {
	}
	
	public PublishGroup(Long policyId, Long typeId) {
		this.policyId = policyId;
		this.typeId = typeId;
	}
}