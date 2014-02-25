package com.yunling.mediacenter.server.remote;

import java.util.Map;

public interface CommandService {
	public String shutDown(String mac);
	public String restart(String mac);
	public String queryProperty(String mac, String target, String property);
	public String stbOperation(String mac, String operation);
	public String playerOperation(String mac, String operation);
	public String downloadPolicy(String filePath, String md5, int fileSize, long timeMillis, String fileVersion);
	public Map<String, String> getQueryResultByKey(String key);
}
