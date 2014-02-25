package com.yunling.policyman.db.mapper;

import com.yunling.policyman.db.mapper.base.BaseUserMapper;
import com.yunling.policyman.db.model.User;

public interface UserMapper
	extends BaseUserMapper
{
	void updatePassword(User user);
}
