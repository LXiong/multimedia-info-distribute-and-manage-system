package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.db.model.UserRole;
import com.yunling.mediacenter.web.Functions;

public interface UserRoleService {
	public void delByUserId(long userId);
	
	
	
	
	List<UserRole> getUserRoleByRoleId(long roleid);

	void addUserRole(UserRole userRole);

	List<RoleFunction> getRoleFunctionByUserId(long userid);
	
	List<Functions> getFunctionsByUserId(long userid);
	
}
