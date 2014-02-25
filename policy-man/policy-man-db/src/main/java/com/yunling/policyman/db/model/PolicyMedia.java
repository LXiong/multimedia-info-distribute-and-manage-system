package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BasePolicyMedia;

public class PolicyMedia
	extends BasePolicyMedia
{
	private Video video;
	private String startTime;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	//empty method for jackson
	public void setStartTime(String t){
		this.startTime = t;
	}
	public String getStartTime(){
		return this.startTime;
	}
}