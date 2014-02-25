package com.yunling.policyman.db.model;

import org.apache.commons.lang.StringUtils;

import com.yunling.policyman.db.model.base.BaseArea;

public class Area extends BaseArea {
	
	public String getTimeformatJs() {
		if (timeFormat == null) {
			return null;
		}
		return timeFormat.replace("\\", "\\\\");
	}

	public String getMediaType() {
		if(StringUtils.equals(type, "1")){
			return "video";
		}else if(StringUtils.equals(type, "2")){
			return "picture";
		}else if(StringUtils.equals(type, "3")){
			return "text";
		}else if(StringUtils.equals(type, "4")){
			return "time";
		}else if(StringUtils.equals(type, "5")){
			return "weather";
		}
		return "video";
//		if (StringUtils.equals(type, "1")) {
//			return "audio";
//		} else if (StringUtils.equals(type, "0")) {
//			return "video";
//		} else if (StringUtils.equals(type, "2")) {
//			return "picture";
//		} else {
//			return "text";
//		}
	}
	
	public String getLevel() {
		String result = "top";
		if (getZindex() == null){
			return "top";
		}
		switch (getZindex().intValue()) {
		case 1:
			result = "top";
			break;
		case 2:
			result = "middle";
			break;
		case 3:
			result = "bottom";
			break;
		default:
			break;
		}
		return result;
	}

	public void merge(Area area) {
		if (area == null)
			return;
		this.setHeight(area.getHeight());
		this.setLcomment(area.getLcomment());
		this.setLeft(area.getLeft());
		this.setName(area.getName());
		this.setTop(area.getTop());
		this.setType(area.getType());
		this.setWidth(area.getWidth());
		this.setTimeFormat(area.getTimeFormat());
	}

}