package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.PublishRecord;

public interface PublishRecordService {
	public List<PublishRecord> listByGroupType(GroupType type);
	
	public List<PublishRecord> getLatestOfGroupType(GroupType type);
	
	public List<PublishRecord> getLatestOfGroups(Groups group);
	
	public List<PublishRecord> listLatest(int num);
}
