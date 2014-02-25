package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.Role;
import com.yunling.policyman.db.service.base.BaseRoleService;

public interface RoleService
	extends BaseRoleService
{
	void updateAuthorities(int roleId, List<Long> authIdList);

	List<Role> listByUser(String userName);
}