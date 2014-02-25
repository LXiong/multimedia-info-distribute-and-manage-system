package com.yunling.mediacenter.server.remote;

public interface WatcherReport {
	public String register(String watcherIp, String watcherInIp, int port);
	public String unregister(String watcherIp, int port);
	public String stbLogin(String watcherIp, String mac, String stbIp, String stbPort);
	public String stbLogoff(String watcherIp, String mac);
	public String refresh(String watcherIp, String watcherOutIp, int port);
}
