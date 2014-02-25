/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.GroupType;

/**
 * @author LingJun
 * @date 2010-10-22
 * 
 */
public interface GroupTypeMapper {
	public List<GroupType> getTypeList(@Param(value = "userId")Long userId);
	public void addGroupType(GroupType groupType);
	public GroupType getGroupType(GroupType groupType);
	public void deleteGroupType(GroupType groupType);
	public void update(GroupType groupType);
	public List<GroupType> listWithChild(@Param("userId") Long userId);
}
