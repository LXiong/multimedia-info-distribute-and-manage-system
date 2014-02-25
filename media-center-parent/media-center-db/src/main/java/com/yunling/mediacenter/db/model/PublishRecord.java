package com.yunling.mediacenter.db.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PublishRecord {
	protected Long id;
	protected Date publishedAt;
	protected String policyNumber;
	protected Long policyId;
	protected Long typeId;
	protected Long groupId;
	protected String md5;
	protected Long sizeBytes;
	protected String filePath;

	private Date createdAt;
	private Date updatedAt;
	private Date startTime;
	private Date endTime;
	private String policyName;
	private String groupName;
	private String typeName;

	public Long getId() {
		return id;
	}

	public void setId(Long val) {
		this.id = val;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date val) {
		this.publishedAt = val;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String val) {
		this.policyNumber = val;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long val) {
		this.policyId = val;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long val) {
		this.typeId = val;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String val) {
		this.md5 = val;
	}

	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long val) {
		this.sizeBytes = val;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String val) {
		this.filePath = val;
	}

	public Map<String, String> getAsParams() {
		Map<String, String> params = new HashMap<String, String>();
		// String updateTime = new
		// SimpleDateFormat("yyyyMMddHHmm").format(getPublishedAt());
		params.put("file_path", getFilePath());
		params.put("md5", getMd5());
		params.put("size", String.valueOf(getSizeBytes()));
		// params.put("update_time", updateTime);
		params.put("update_time", getPolicyNumber());
		params.put("policy_number", getPolicyNumber());
		return params;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the policyName
	 */
	public String getPolicyName() {
		return policyName;
	}

	/**
	 * @param policyName
	 *            the policyName to set
	 */
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
