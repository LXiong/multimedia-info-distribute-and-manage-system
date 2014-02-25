/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Groups;

/**
 * @author LingJun
 * @date 2010-10-25
 * 
 */
public interface GroupsService {
	public List<Groups> getGroupList(Groups groups, Long userId);
	public void addGroups(Groups groups);
	public Groups getGroup(Groups groups);
	public List<Groups> getGroupListByType(Groups groups);
	public void deleteGroup(Groups groups);
	public void update(Groups groups);
}
