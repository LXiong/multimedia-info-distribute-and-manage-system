package com.yunling.mediaman.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.yunling.policyman.db.model.TimedArea;
import com.yunling.policyman.db.model.TimedLayout;

public class TimedLayoutVo {
	
	private Long id;
	private Long layoutId;
	private String name;
	private String lcomment;
	private String startTime;
	private String endTime;
	private List<TimedAreaVo> areas;
	
	public TimedLayout toTimedLayout() {
		TimedLayout tl = new TimedLayout();
		tl.setId(id);
		tl.setLayoutId(layoutId);
		tl.setName(name);
		tl.setLcomment(lcomment);
		tl.setStartTime(startTime);
		tl.setEndTime(endTime);
		tl.setAreas(collectAreas(tl));
		return tl;
	}
	
	private List<TimedArea> collectAreas(TimedLayout tl) {
		int size = areas == null ? 0 : areas.size();
		List<TimedArea> areaList = new ArrayList<TimedArea>(size);
		for(TimedAreaVo vo : areas) {
			areaList.add(vo.toTimedArea(tl));
		}
		return areaList;
	}

	public List<TimedAreaVo> getAreas() {
		return areas;
	}
	public void setAreas(List<TimedAreaVo> areas) {
		this.areas = areas;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLcomment() {
		return lcomment;
	}
	public void setLcomment(String lcomment) {
		this.lcomment = lcomment;
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

	public Long getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
