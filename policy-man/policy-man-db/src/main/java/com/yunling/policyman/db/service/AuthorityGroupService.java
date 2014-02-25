package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.AuthorityGroup;
import com.yunling.policyman.db.service.base.BaseAuthorityGroupService;

public interface AuthorityGroupService
	extends BaseAuthorityGroupService
{
	List<AuthorityGroup> listWithAuth();
	List<AuthorityGroup> listByRole(int roleId);
}