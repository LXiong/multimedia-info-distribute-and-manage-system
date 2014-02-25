package com.yunling.policyman.db.model;

import com.yunling.policyman.db.model.base.BaseTimedMedia;

public class TimedMedia extends BaseTimedMedia {
	public TimedMedia(){}
	public TimedMedia(String code, String startTime, String endTime){
		this.code = Long.valueOf(code);
		this.startTime = startTime;
		this.endTime = endTime;
	}
}