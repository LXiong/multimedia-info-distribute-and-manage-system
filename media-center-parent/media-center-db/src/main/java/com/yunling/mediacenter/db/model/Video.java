package com.yunling.mediacenter.db.model;

public class Video {
	private long videoId;
	private String readablename;
	private String name;
	public long getVideoId() {
		return videoId;
	}
	public void setVideoId(long videoId) {
		this.videoId = videoId;
	}
	public String getReadablename() {
		return readablename;
	}
	public void setReadablename(String readablename) {
		this.readablename = readablename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	private long policyId;
}
