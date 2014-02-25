package com.yunling.mediacenter.web;

import java.util.HashSet;
import java.util.Set;

public enum Functions {
	Basic(false),
	CustomerDeviceEdit,
	CustomerDeviceView,
	CustomerDeviceManage,
	ServerLogView,
	DeviceLogView,
	DeviceReport,
	CustomerUserAdmin,
	CustomerRoleEdit,
	UsbDeploy,
	UpdateManage,
	SiteUserAdmin(true),
	SiteRoleAdmin(true),
	SiteCustomerAdmin(true),
	;
	
	static Set<Functions> userFunctions;
	
	static {
		Set<Functions> result = new HashSet<Functions>();
		for(Functions f : Functions.values()) {
			if (!f.isForSite()) {
				result.add(f);
			}
		}
		userFunctions= result;
	}
	
	private boolean forSite;
	
	Functions() {
		this.forSite = false;
	}
	Functions(boolean flag) {
		this.forSite = flag;
	}
	
	public static Set<Functions> getUserFunctions() {
		return userFunctions;
	}
	
	public boolean isForSite() {
		return forSite;
	}
}
