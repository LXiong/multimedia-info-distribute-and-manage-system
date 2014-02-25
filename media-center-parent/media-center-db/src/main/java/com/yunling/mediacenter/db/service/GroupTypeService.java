/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.GroupType;

/**
 * @author LingJun
 * @date 2010-10-22
 * 
 */
public interface GroupTypeService {
	
	List<GroupType> listWithChild(Long userId);
	
	public List<GroupType> getTypeList(Long userId);
	public void addGroupType(GroupType groupType);
	public GroupType getGroupType(GroupType groupType);
	public void deleteGroupType(GroupType groupType);
	public void update(GroupType groupType);
}
