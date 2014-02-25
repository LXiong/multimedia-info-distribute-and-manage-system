package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BaseManUserRole;

public class ManUserRole
	extends BaseManUserRole
{
	public ManUserRole() {
	}
	public ManUserRole(String name, Role role){
		this.username = name;
		this.roleId = role.getId();
	}
	public ManUserRole(String name, Long roleId){
		this.username = name;
		this.roleId = roleId;
	}
}