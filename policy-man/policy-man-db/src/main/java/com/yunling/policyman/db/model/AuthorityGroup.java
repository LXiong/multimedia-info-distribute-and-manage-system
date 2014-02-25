package com.yunling.policyman.db.model;

import java.util.List;

import com.yunling.policyman.db.model.base.BaseAuthorityGroup;

public class AuthorityGroup extends BaseAuthorityGroup {
	
	private boolean checkAll;
	
	private List<Authority> authorities;
	
	public String getLocalName() {
		return getLocaleZhCn();
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

}