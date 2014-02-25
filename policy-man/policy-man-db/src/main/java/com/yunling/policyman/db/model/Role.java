package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BaseRole;

public class Role
	extends BaseRole
{
	private boolean assigned;
	
	public String toString(){
		return name;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
}