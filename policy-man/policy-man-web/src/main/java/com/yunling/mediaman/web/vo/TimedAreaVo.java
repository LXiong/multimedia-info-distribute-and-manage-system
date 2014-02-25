package com.yunling.mediaman.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.yunling.policyman.db.model.TimedArea;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.model.TimedList;

public class TimedAreaVo {

	private Long id;
	private String name;
	private String type;
	private String lcomment;
	private Long left;
	private Long top;
	private Long width;
	private Long height;
	private Long zindex;
	private String font;
	private String color;
	private String bgcolor;
	private String timeFormat;
	private List<TimedPlayListVo> plList;
	
	public TimedArea toTimedArea(TimedLayout layout) {
		TimedArea result = new TimedArea();
		result.setId(id);
		result.setName(name);
		result.setType(type);
		result.setPlayLists(collectionPlList(layout));
		result.setLeft(left);
		result.setTop(top);
		result.setWidth(width);
		result.setHeight(height);
		result.setZindex(zindex);
		result.setFont(font);
		result.setColor(color);
		result.setBgcolor(bgcolor);
		result.setTimeFormat(timeFormat);
		return result;
	}
	
	private List<TimedList> collectionPlList(TimedLayout layout) {
		int size = plList!=null ? plList.size():0;
		List<TimedList> result = new ArrayList<TimedList>(size);
		if (plList!=null) {
			for(TimedPlayListVo vo : plList) {
				result.add(vo.toPlayList(layout));
			}
		}
		return result;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<TimedPlayListVo> getPlList() {
		return plList;
	}

	public void setPlList(List<TimedPlayListVo> plList) {
		this.plList = plList;
	}

	public String getLcomment() {
		return lcomment;
	}

	public void setLcomment(String lcomment) {
		this.lcomment = lcomment;
	}

	public Long getLeft() {
		return left;
	}

	public void setLeft(Long left) {
		this.left = left;
	}

	public Long getTop() {
		return top;
	}

	public void setTop(Long top) {
		this.top = top;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getZindex() {
		return zindex;
	}

	public void setZindex(Long zindex) {
		this.zindex = zindex;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	
}
