package com.yunling.mediacenter.db.model;

import java.util.Date;
import java.util.Map;

public class StbWarningInfo {
	private long id;
	private String status;
	private String stbMac;
	private String warningType;
	private String details;
	private Date createdAt;
	private String severity;
	
	//help attributes
	private String warningTypeCn;
	private String statusCn;
	private int page;

	public StbWarningInfo() {
	}

	public StbWarningInfo(Map<String, String> map) {
		setWarningType(map.get("type"));
		setDetails(map.get("detail"));
		setSeverity(map.get("severity"));
	}

	public String getWarningTypeCn() {
		return warningTypeCn;
	}

	public void setWarningTypeCn(String warningTypeCn) {
		this.warningTypeCn = warningTypeCn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStbMac() {
		return stbMac;
	}

	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isWaitingForProcessing(){
		return "waiting_for_processing".equalsIgnoreCase(status);
	}

	public String getStatusCn() {
		return statusCn;
	}

	public void setStatusCn(String statusCn) {
		this.statusCn = statusCn;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

}
