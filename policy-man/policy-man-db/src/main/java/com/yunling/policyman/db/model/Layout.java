package com.yunling.policyman.db.model;

import java.util.ArrayList;
import java.util.List;

import com.yunling.policyman.db.model.base.BaseLayout;

public class Layout
	extends BaseLayout {
	private List<Area> areas;
	private int areaCount;
	
	public void merge(Layout newLayout) {
		if (newLayout == null) {
			return;
		}
		setHeight(newLayout.getHeight());
		setLcomment(newLayout.getLcomment());
		setName(newLayout.getName());
		setWidth(newLayout.getWidth());
		setUsePercent(newLayout.getUsePercent());
	}
	
	public List<Area> getAreas() {
		if (areas == null) {
			areas = new ArrayList<Area>();
		}
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	

	public int getAreaCount() {
		return areaCount;
	}

	public void setAreaCount(int areaCount) {
		this.areaCount = areaCount;
	}

}