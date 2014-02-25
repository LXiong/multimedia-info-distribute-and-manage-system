package com.yunling.mediacenter.db.model;

import java.util.Date;

public class WatcherReportRecord {
	private String mac;
	private String watchIp;
	private String watchPort;
	private String stbIp;
	private String stbPort;
	private Date createdAt;
	public String getWatchIp() {
		return watchIp;
	}
	public void setWatchIp(String watchIp) {
		this.watchIp = watchIp;
	}
	public String getWatchPort() {
		return watchPort;
	}
	public void setWatchPort(String watchPort) {
		this.watchPort = watchPort;
	}
	public String getStbIp() {
		return stbIp;
	}
	public void setStbIp(String stbIp) {
		this.stbIp = stbIp;
	}
	public String getStbPort() {
		return stbPort;
	}
	public void setStbPort(String stbPort) {
		this.stbPort = stbPort;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
}
