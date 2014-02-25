package com.yunling.mediaman.web.vo;

import org.apache.commons.lang.StringUtils;

import com.yunling.policyman.db.model.Role;

public class RoleVo {
	protected String id;
	protected String name;
	protected String description;
	protected Boolean enabled;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Role toRole() {
		Role role = new Role();
		role.setName(getName());
		role.setDescription(getDescription());
		role.setEnabled(getEnabled());
		if (!StringUtils.equals(getId(), "_empty")) {
			role.setId(Long.parseLong(getId()));
		}
		return role;
	}
}
