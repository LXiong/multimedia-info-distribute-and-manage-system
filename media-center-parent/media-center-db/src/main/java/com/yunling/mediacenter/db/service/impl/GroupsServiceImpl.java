/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.GroupsMapper;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.service.GroupsService;

/**
 * @author LingJun
 * @date 2010-10-25
 * 
 */
public class GroupsServiceImpl extends BaseServiceImpl implements GroupsService {

	private GroupsMapper getMapper() {
		return getMapper(GroupsMapper.class);
	}
	
	@Override
	public List<Groups> getGroupList(final Groups groups, final Long userId) {
		return getMapper().getGroupList(groups, userId);
	}

	@Override
	public void addGroups(final Groups groups) {
		getMapper().addGroups(groups);
	}

	@Override
	public Groups getGroup(final Groups groups) {
		return getMapper().getGroup(groups);
	}

	@Override
	public List<Groups> getGroupListByType(final Groups groups) {
		return getMapper().getGroupListByType(groups);
	}

	@Override
	public void deleteGroup(final Groups groups) {
		getMapper().deleteGroup(groups);
	}

	@Override
	public void update(final Groups groups) {
		getMapper().update(groups);
	}

}
