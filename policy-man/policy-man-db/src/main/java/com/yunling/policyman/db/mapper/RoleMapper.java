package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseRoleMapper;
import com.yunling.policyman.db.model.Role;

public interface RoleMapper
	extends BaseRoleMapper
{
	List<Role> listByUser(@Param("username")String userName);
}
