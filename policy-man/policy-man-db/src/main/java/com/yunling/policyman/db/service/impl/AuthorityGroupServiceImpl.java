package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.mapper.AuthorityGroupMapper;
import com.yunling.policyman.db.model.AuthorityGroup;
import com.yunling.policyman.db.service.AuthorityGroupService;
import com.yunling.policyman.db.service.impl.base.BaseAuthorityGroupServiceImpl;

public class AuthorityGroupServiceImpl
	extends BaseAuthorityGroupServiceImpl
	implements AuthorityGroupService
{
	public List<AuthorityGroup> listWithAuth() {
		return getMapper(AuthorityGroupMapper.class).listWithAuth();
	}

	@Override
	public List<AuthorityGroup> listByRole(int roleId) {
		return getMapper(AuthorityGroupMapper.class).listByRole(roleId);
	}
}