/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Groups;

/**
 * @author LingJun
 * @date 2010-10-25
 * 
 */
public interface GroupsMapper {
	public List<Groups> getGroupList(@Param(value = "groups") Groups groups, 
			@Param(value = "userId") Long userId);
	public void addGroups(Groups groups);
	public Groups getGroup(Groups groups);
	public List<Groups> getGroupListByType(Groups groups);
	public void deleteGroup(Groups groups);
	public void update(Groups groups);
}
