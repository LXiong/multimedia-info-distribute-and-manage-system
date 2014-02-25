package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.UserRoleMapper;
import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.db.model.UserRole;
import com.yunling.mediacenter.db.service.UserRoleService;
import com.yunling.mediacenter.web.Functions;

public class UserRoleServiceImpl extends BaseServiceImpl implements
		UserRoleService {
	private UserRoleMapper getMapper() {
		return getMapper(UserRoleMapper.class);
	}
	
	public void delByUserId(final long userId) {
		 getMapper().delByUserId(userId);
	}
	
	
	

	public List<UserRole> getUserRoleByRoleId(final long roleid) {
		return getMapper().getUserRoleByRoleId(roleid);
	}

	public void addUserRole(final UserRole userRole) {
		getMapper().addUserRole(userRole);
	}

	

	public List<RoleFunction> getRoleFunctionByUserId(final long userid) {
		return  getMapper().getRoleFunctionByUserId(userid);
	}

	@Override
	public List<Functions> getFunctionsByUserId(final long userid) {
		return  getMapper().listFunctionByUserId(userid);
	}
}
