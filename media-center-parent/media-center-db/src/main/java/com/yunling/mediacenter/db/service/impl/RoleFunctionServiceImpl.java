package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.RoleFunctionMapper;
import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.db.service.RoleFunctionService;
import com.yunling.mediacenter.web.Functions;

public class RoleFunctionServiceImpl extends BaseServiceImpl implements
		RoleFunctionService {
	
	private RoleFunctionMapper getMapper() {
		return getMapper(RoleFunctionMapper.class);
	}

	public void delRoleFunctionByRoleId(final long roleid) {
		getMapper().delRoleFunctionByRoleId(roleid);
	}

	@Override
	public List<Functions> getFunctionsByRoleid(final long roleId) {
		return getMapper().getFunctionsByRoleid(roleId);
	}

	@Override
	public void addRoleFunction(final RoleFunction fid) {
		getMapper().addRoleFunction(fid);
	}
}
