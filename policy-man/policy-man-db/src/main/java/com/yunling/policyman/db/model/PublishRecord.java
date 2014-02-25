package com.yunling.policyman.db.model;

import java.util.HashMap;
import java.util.Map;

import com.yunling.policyman.db.model.base.BasePublishRecord;

public class PublishRecord extends BasePublishRecord {
	
	public PublishRecord(){
		
	}

	public PublishRecord(Policy policy, StbGroup groupOne, StbGroupLevelTwo groupTwo){
		this.policyId = policy.getId();
		this.groupId = groupTwo.getGroupId();
		this.typeId = groupOne.getTypeId();
	}
	
	public Map<String, String> getAsParams(){
		Map<String, String> params = new HashMap<String, String>();
		//String updateTime = new SimpleDateFormat("yyyyMMddHHmm").format(getPublishedAt());
		params.put("file_path", getFilePath());
		params.put("md5", getMd5());
		params.put("size", String.valueOf(getSizeBytes()));
		//params.put("update_time", updateTime);
		params.put("update_time", getPolicyNumber());
		params.put("policy_number", getPolicyNumber());
		return params;
	}
}