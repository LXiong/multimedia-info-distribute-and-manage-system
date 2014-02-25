package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BaseAuthority;

public class Authority
	extends BaseAuthority
{
	private boolean granted;
	
	public String getLocalName() {
		return getLocaleZhCn();
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}
}