package com.yunling.commons.log;

public interface WatchLog {
	public void logRegister(String response);
	public void logCommand(String mac, String opertation, String param);
}
