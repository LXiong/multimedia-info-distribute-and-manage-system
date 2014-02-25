package com.yunling.mediacenter.db.model;

import java.util.HashMap;
import java.util.Map;

public class PolicyFile {
	private String filePath;
	private String md5;
	private int fileSize;
	private String fileVersion;
	private String updatedTime;
	public PolicyFile(String filePath, String md5, int fileSize,
			String fileVersion, String downloadTime) {
		super();
		this.filePath = filePath;
		this.md5 = md5;
		this.fileSize = fileSize;
		this.fileVersion = fileVersion;
		this.updatedTime = downloadTime;
	}
	
	public Map<String, String> getAsMapParams(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("file_path", filePath);
		map.put("md5", md5);
		map.put("size", String.valueOf(fileSize));
		map.put("update_time", updatedTime);
		map.put("policy_number", fileVersion);
		return map;
	}
	
	public boolean equals(Object policy){
		if(policy==null){
			return false;
		}
		return this.hashCode() == policy.hashCode();
	}
	public int hashCode(){
		return (filePath + md5 + fileSize + updatedTime + fileVersion).hashCode();
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

}
