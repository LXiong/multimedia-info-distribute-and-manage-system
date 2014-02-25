package com.yunling.mediacenter.db.model;

import java.util.Date;

public class WebControlRecord {
	String command;
	String watchIp;
	String watchPort;
	String stbMac;
	String stbIp;
	String stbPort;
	String customerName;
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
	public String getStbMac() {
		return stbMac;
	}
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
}
