package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Roles;

public interface RoleMapper {
	public List<Roles> getRoles();
	
	public void createRole(Roles role);

	public void updateRole(Roles role);

	public void delRoleById(long roleid);
	
	public Roles getRoleByName(String roleName);
	
	
	
	

	List<Roles> list(Map<String, Object> map);

	Object addRole(Roles role);

	Roles getRoleById(long roleid);

	List<Roles> getRoleByUserId(long userId);

	List<Roles> notRoleByUserId(long userId);

	Integer selectRoleCount();
	
	void unsetAuthority(@Param("role_id")Long roleId, @Param("auth_id") Long authId);
	
	void setAuthority(@Param("role_id")Long roleId, @Param("auth_id") Long authId);
}
