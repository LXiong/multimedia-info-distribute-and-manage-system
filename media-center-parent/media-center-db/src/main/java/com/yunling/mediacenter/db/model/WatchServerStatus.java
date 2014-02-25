package com.yunling.mediacenter.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WatchServerStatus implements Comparable<WatchServerStatus>,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1834201129772994481L;
	private Date startUpTime = new Date();
	private Date refreshTime = new Date();
	private String watcherIp;
	private String watcherInnetIp;
	private int watchPort;
	private Set<String> loginStbMac = new HashSet<String>();

	public WatchServerStatus(String ip, String innetIp, int port) {
		this.watcherIp = ip;
		this.watcherInnetIp = innetIp;
		this.watchPort = port;
	}

	public WatchServerStatus(String ip, int port) {
		this.watcherIp = ip;
		this.watchPort = port;
	}

	public Date getStartUpTime() {
		return startUpTime;
	}

	public void setStartUpTime(Date startUpTime) {
		this.startUpTime = startUpTime;
	}

	public String getWatcherIp() {
		return watcherIp;
	}

	public void setWatcherIp(String watcherIp) {
		this.watcherIp = watcherIp;
	}

	public int getWatchPort() {
		return watchPort;
	}

	public void setWatchPort(int watchPort) {
		this.watchPort = watchPort;
	}

	public int hashCode() {
		return (watcherIp + watchPort).hashCode();
	}

	public boolean equals(Object wss) {
		if (wss == null) {
			return false;
		}
		return wss.hashCode() == this.hashCode();
	}

	public int getStbCount() {
		// return stbCount;
		return loginStbMac.size();
	}

	@Override
	public int compareTo(WatchServerStatus wss) {
		return this.loginStbMac.size() - wss.loginStbMac.size();
	}

	public Set<String> getLoginStbMac() {
		return loginStbMac;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void updateRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getWatcherInnetIp() {
		return watcherInnetIp;
	}

	public void setWatcherInnetIp(String watcherInnetIp) {
		this.watcherInnetIp = watcherInnetIp;
	}

}
