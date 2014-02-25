package com.yunling.commons.log;

public interface AuthLog {
	public void logConnect(String remoteIp);
	public void logVerify(String mac, String response);
	public void logWatchConnect(String remoteIp, String remotePort, String externalIp, String externalPort);
}
