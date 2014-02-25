package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.User;
import com.yunling.policyman.db.service.base.BaseUserService;

public interface UserService
	extends BaseUserService
{
	void insert(User user, boolean withPassword);
	void update(User user, boolean withPassword);
	void updateRole(String userName, List<Long> roleIdList);
}