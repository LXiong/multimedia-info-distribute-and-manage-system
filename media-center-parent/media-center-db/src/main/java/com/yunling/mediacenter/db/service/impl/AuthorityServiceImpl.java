package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.AuthorityMapper;
import com.yunling.mediacenter.db.model.Authority;
import com.yunling.mediacenter.db.service.AuthorityService;

public class AuthorityServiceImpl extends BaseServiceImpl
	implements AuthorityService
{
	@Override
	public List<Authority> list() {
		return getMapper().list();
	}

	@Override
	public List<Authority> listByRole(Long roleId) {
		return getMapper().listByRole(roleId);
	}

	@Override
	public List<Authority> listGrantedByRole(Long roleId) {
		return getMapper().listGrantedByRole(roleId);
	}

	private AuthorityMapper getMapper() {
		return getMapper(AuthorityMapper.class);
	}
}
