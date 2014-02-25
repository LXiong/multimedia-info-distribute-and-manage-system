package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.web.Functions;



public interface RoleFunctionService {

	void delRoleFunctionByRoleId(long roleid);
	
	public List<Functions> getFunctionsByRoleid(final long roleId);

	void addRoleFunction(RoleFunction rolefunction);
		
}
