package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.db.model.UserRole;
import com.yunling.mediacenter.web.Functions;

public interface UserRoleMapper {
	
	List<Functions> listFunctionByUserId(long userId);

	List<UserRole> getUserRoleByRoleId(long roleid);

	Object addUserRole(UserRole userRole);

	Object delByUserId(long userid);
	
	List<RoleFunction> getRoleFunctionByUserId(long userid);
	
}
