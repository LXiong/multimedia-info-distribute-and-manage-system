package com.yunling.policyman.db.model;

import java.util.List;

import com.yunling.policyman.db.model.base.BaseTimedList;

public class TimedList
	extends BaseTimedList
{
	private List<TimedMedia> medias;

	public List<TimedMedia> getMedias() {
		return medias;
	}

	public void setMedias(List<TimedMedia> medias) {
		this.medias = medias;
	}
}