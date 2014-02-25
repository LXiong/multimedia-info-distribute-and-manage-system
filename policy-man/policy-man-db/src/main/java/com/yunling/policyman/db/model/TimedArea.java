package com.yunling.policyman.db.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yunling.policyman.db.model.base.BaseTimedArea;

public class TimedArea
	extends BaseTimedArea
{
	private List<TimedList> playLists;
	
	public void merge(TimedArea newArea) {
		setName(newArea.getName());
		setLcomment(newArea.getLcomment());
		setType(newArea.getType());
		setFont(newArea.getFont());
		setColor(newArea.getColor());
		setBgcolor(newArea.getBgcolor());
	}
	
	public String getLevel() {
		if (getZindex() == null) {
			return "bottom";
		} else if (getZindex() == 1) {
			return "top";
		} else if (getZindex() == 2) {
			return "middle";
		} else if (getZindex() == 3) {
			return "bottom";
		}
		
		return "bottom";
	}
	
	public String getPlayType() {
		if (StringUtils.equals(getType(), "1")) {
			return "video";
		} else if (StringUtils.equals(getType(), "2")) {
			return "image";
		} else if (StringUtils.equals(getType(), "3")) {
			return "text";
		} else if (StringUtils.equals(getType(), "4")) {
			return "time";
		} else if (StringUtils.equals(getType(), "5")) {
			return "weather";
		}
		return "";
	}

	public List<TimedList> getPlayLists() {
		return playLists;
	}

	public void setPlayLists(List<TimedList> playLists) {
		this.playLists = playLists;
	}

}