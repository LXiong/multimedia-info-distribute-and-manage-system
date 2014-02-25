package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.PublishRecordMapper;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.PublishRecord;
import com.yunling.mediacenter.db.service.PublishRecordService;

public class PublishRecordServiceImpl extends BaseServiceImpl implements
		PublishRecordService {

	PublishRecordMapper getMapper() {
		return getMapper(PublishRecordMapper.class);
	}

	@Override
	public List<PublishRecord> listByGroupType(GroupType type) {
		return getMapper().listByGroupType(type.getTypeId());
	}

	@Override
	public List<PublishRecord> getLatestOfGroupType(GroupType type) {

		return getMapper().getLatestOfGroupType(type.getTypeId());
	}

	@Override
	public List<PublishRecord> listLatest(int num) {
		return getMapper().listLatest(num);
	}

	@Override
	public List<PublishRecord> getLatestOfGroups(Groups group) {
		return getMapper().listLatestByGroups(group.getGroupId());
	}

}
