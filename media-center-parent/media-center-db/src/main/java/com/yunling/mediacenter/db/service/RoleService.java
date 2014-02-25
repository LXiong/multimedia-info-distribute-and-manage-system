package com.yunling.mediacenter.db.service;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.Roles;

public interface RoleService {
	public List<Roles> getRoles();
	
	public void createRole(Roles role);

	public void updateRole(Roles role);
	
	public void delRoleById(long roleid);
	
	public Roles getRoleByName(String roleName);
	
	
	
	
	
	Map<String, Object> list(Integer page,Integer sum);

	void addRole(Roles role);

	Roles getRoleById(long roleid);

	List<Roles> getRoleByUserId(long userId);

	List<Roles> notRoleByUserId(long userId);

	Integer selectRoleCount();
	
	void updateAuthorities(Long roleId, List<Long> authorityIdList);
}
