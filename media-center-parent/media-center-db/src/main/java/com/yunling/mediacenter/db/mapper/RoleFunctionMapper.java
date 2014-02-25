package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.web.Functions;

public interface RoleFunctionMapper {

	public Object delRoleFunctionByRoleId(long roleid);

	public Object getRoleFunctionByUserId(long userid);

	public List<RoleFunction> getRoleFunctionByRoleid(long roleid);

	public List<RoleFunction> notRoleFunctionByRoleid(long roleid);

	public List<Functions> getFunctionsByRoleid(long roleid);

	public Object addRoleFunction(RoleFunction rolefunction);


	
}


