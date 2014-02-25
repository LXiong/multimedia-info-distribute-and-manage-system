package com.yunling.policyman.db.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunling.policyman.db.mapper.ManUserRoleMapper;
import com.yunling.policyman.db.mapper.RoleMapper;
import com.yunling.policyman.db.mapper.UserMapper;
import com.yunling.policyman.db.model.ManUserRole;
import com.yunling.policyman.db.model.Role;
import com.yunling.policyman.db.model.User;
import com.yunling.policyman.db.service.UserService;
import com.yunling.policyman.db.service.impl.base.BaseUserServiceImpl;

public class UserServiceImpl
	extends BaseUserServiceImpl
	implements UserService
{

	@Override
	public void update(User user, boolean withPassword) {
		getMapper(UserMapper.class).update(user);
		if (withPassword) {
			getMapper(UserMapper.class).updatePassword(user);
		}
	}

	@Override
	public void insert(User user, boolean withPassword) {
		getMapper(UserMapper.class).insert(user);
		if (withPassword) {
			getMapper(UserMapper.class).updatePassword(user);
		}
	}

	@Override
	public void updateRole(String userName, List<Long> roleIdList) {
		User user = getByKey(userName);
		if (user==null) {
			return ;
		}
		List<Role> roleList = getMapper(RoleMapper.class).listByUser(userName);
		List<Long> newList = new ArrayList<Long>();
		List<Long> delList = new ArrayList<Long>();
		for(Long id : roleIdList) {
			for (Iterator<Role> it = roleList.iterator(); it.hasNext();) {
				Role role = it.next();
				if (role.getId().equals(id)) {
					if (!role.isAssigned()) {
						newList.add(id);
					}
					it.remove();
					break;
				}
			}
		}
		for(Role role : roleList) {
			if (role.isAssigned()) {
				delList.add(role.getId());
			}
		}
		
		ManUserRoleMapper urMapper = getMapper(ManUserRoleMapper.class);
		if (delList.size()>0) {
			for(Long roleId : delList) {
				urMapper.deleteByKey(userName, roleId);
			}
		}
		if (newList.size()>0) {
			for(Long roleId : newList) {
				ManUserRole ur = new ManUserRole(userName, roleId);
				urMapper.insert(ur);
			}
		}
	}
	
}