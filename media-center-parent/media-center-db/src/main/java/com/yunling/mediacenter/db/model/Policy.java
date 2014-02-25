package com.yunling.mediacenter.db.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Policy {
	private long policyId;
	private long policy_number;
	private Date beginAt;
	private Date endAt;
	private String md5;
	private long size_bytes;
	private Date update_time;
	private Date createAt;
	private String file_path;
	public long getPolicy_number() {
		return policy_number;
	}
	public void setPolicy_number(long policy_number) {
		this.policy_number = policy_number;
	}
	
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSize_bytes() {
		return size_bytes;
	}
	public void setSize_bytes(long size_bytes) {
		this.size_bytes = size_bytes;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	public long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}
	public Date getBeginAt() {
		return beginAt;
	}
	public void setBeginAt(Date beginAt) {
		this.beginAt = beginAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Map<String, String> getAsMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("file_path", this.getFile_path());
		map.put("policy_number", String.valueOf(this.getPolicy_number()));
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmm");
		map.put("update_time", dateFm.format(this.getUpdate_time()));
		map.put("md5", this.getMd5());
		map.put("size", String.valueOf(this.getSize_bytes()));
		return map;
	}
	
	
}
