package com.yunling.mediacenter.server.remote;

import java.util.List;

public interface AuthenticateQuery<T> {
	public int getAuthenticateSuccessCount();
	public int getAuthenticateFailureCount();
	public int getAuthenticateAuditCount();
	public String getAuthServerStartUpTime();
	public List<T> getAllWatchServerStatus();
	public T getWatchServerByIp(String ip);
}
