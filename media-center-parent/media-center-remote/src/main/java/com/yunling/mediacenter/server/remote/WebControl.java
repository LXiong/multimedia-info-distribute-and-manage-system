package com.yunling.mediacenter.server.remote;

import java.util.List;
import java.util.Map;

public interface WebControl {
	/**
	 * shutdown the stb 
	 * @param mac 
	 * @return
	 */
	public String shutDown(String mac);
	/**
	 * make the stb restart
	 * @param mac
	 * @return
	 */
	public String restart(String mac);
	/**
	 * 
	 * @param mac
	 * @param target
	 * @param property
	 * @return
	 */
	public String queryProperty(String mac, String target, String property);
	/**
	 * 
	 * @param mac
	 * @param operation
	 * @return
	 */
	public String stbOperation(String mac, String operation);
	/**
	 * 
	 * @param mac
	 * @param operation
	 * @return
	 */
	public String playerOperation(String mac, String operation);
	/**
	 * 
	 * @param filePath
	 * @param md5
	 * @param fileSize
	 * @param downloadTimeStr Formatted download time string
	 * @param fileVersion
	 * @return
	 */
	public String downloadPolicy(String cityCode, String filePath, String md5, int fileSize, String downloadTimeStr, String fileVersion);
	
	/**
	 * 
	 * @param filePath
	 * @param md5
	 * @param fileSize
	 * @param downloadTimeStr Formatted download time string
	 * @param fileVersion
	 * @return
	 */
	public String downloadSinglePolicy(String filePath, String md5, int fileSize, String downloadTimeStr, String fileVersion);
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getQueryResultByKey(String key);
	/**
	 * modify the stb`s configuration with configuration parameters
	 * @param mac : stb`s mac address
	 * @param params : configuration parameters
	 * @return
	 */
	public String modifyStbConfig(String mac, Map<String, String> params);
	
	
	/**
	 * update one stb or all stbs, 
	 * usage: updateSoftware(updateType, filePath, md5, version, mac) will update stb which it`s mac address is #{mac}
	 * usage: updateSoftware(updateType, filePath, md5, version) will update all stbs which connected to watcher. 
	 */
	//public String updateSoftware(String...paramsWithOrWithoutMac);
	/**
	 * you can put the parameters into a map, so you should map.put("update_type", updateType) and map.put("file_path", filePath) and .......
	 * @param params, 
	 * @param mac, if no mac, update all stbs, or update one
	 * @return
	 */
	public String updateSoftware(Map<String, String> params, String...mac);

	public String uploadLog(String mac);
	
	public String sendInstantMessage(Map<String, String> params, List<String> macs);
	
	public String updatePolicyForGroupType(long typeId, Map<String, String> params);
	
	public void configStream(String ip, String groupList, String startTime, String endTime);
	
	public void deleteMedia(String mac, String fileName);
	
	public String updatePolicyForGroup(long groupId, Map<String, String> params);
	//public String screenShot(String mac);
}
