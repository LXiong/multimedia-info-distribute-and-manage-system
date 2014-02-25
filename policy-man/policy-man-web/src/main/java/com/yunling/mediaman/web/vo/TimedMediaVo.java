package com.yunling.mediaman.web.vo;

import com.yunling.policyman.db.model.TimedMedia;

public class TimedMediaVo {
	private String code;
	private String startTime;
	private String endTime;
	private String name;
	private String playCount;
	private String type;
	private String content;
	public TimedMedia toTimedMedia() {
		if(code == null || "".equals(code)){
			code = "0";
		}
		return new TimedMedia(code, startTime, endTime);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
