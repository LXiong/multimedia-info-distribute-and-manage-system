package com.yunling.mediacenter.db.model;

import java.util.Date;

public class StbLoginRecord {
	String type;
	String mac;
	String watchIp;
	String watchPort;
	String stbIp;
	String stbPort;
	String status;
	Date createdAt;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.type + this.mac + this.watchIp + this.watchPort
				+ this.stbIp + this.stbPort + this.status + this.createdAt;
	}
}
