package com.yunling.policyman.db.model;

import java.util.List;

import com.yunling.policyman.db.model.base.BaseTimedLayout;

public class TimedLayout
	extends BaseTimedLayout
{
	private List<TimedArea> areas;

	public List<TimedArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TimedArea> areas) {
		this.areas = areas;
	}
}