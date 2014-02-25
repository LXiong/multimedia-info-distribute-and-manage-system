package com.yunling.mediacenter.db.model;

import java.util.Date;

public class AuthenticateRecord {
	private String stbIp;
	private String stbMac;
	private String customerName;
	private String authStatus;
	private String watchServerIp;
	private String watchServerPort;
	private Date createdAt;
	public String getStbIp() {
		return stbIp;
	}
	public void setStbIp(String stbIp) {
		this.stbIp = stbIp;
	}
	public String getStbMac() {
		return stbMac;
	}
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getWatchServerIp() {
		return watchServerIp;
	}
	public void setWatchServerIp(String watchServerIp) {
		this.watchServerIp = watchServerIp;
	}
	public String getWatchServerPort() {
		return watchServerPort;
	}
	public void setWatchServerPort(String watchServerPort) {
		this.watchServerPort = watchServerPort;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
