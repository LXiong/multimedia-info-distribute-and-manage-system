package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.GroupTypeMapper;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.service.GroupTypeService;

public class GroupTypeServiceImpl extends BaseServiceImpl implements
		GroupTypeService {

	private GroupTypeMapper getMapper() {
		return getMapper(GroupTypeMapper.class);
	}
	
	@Override
	public List<GroupType> getTypeList(final Long userId) {
		return getMapper().getTypeList(userId);
	}

	@Override
	public void addGroupType(final GroupType groupType) {
		getMapper().addGroupType(groupType);
	}

	@Override
	public GroupType getGroupType(final GroupType groupType) {
		return getMapper().getGroupType(groupType);
	}

	@Override
	public void deleteGroupType(final GroupType groupType) {
		getMapper().deleteGroupType(groupType);
	}

	@Override
	public void update(final GroupType groupType) {
		getMapper().update(groupType);
	}

	@Override
	public List<GroupType> listWithChild(Long userId) {
		return getMapper().listWithChild(userId);
	}

}
