package com.yunling.mediacenter.db.model;

import java.util.Date;

public class BoxPerfLog {
	private long id;
	private String mac;
	private Date logTime;
	private long diskUsed;
	private long memUsed;
	private long cpuUsed;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public long getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}
	public long getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed;
	}
	public long getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(long cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
}
