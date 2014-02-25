package com.yunling.mediaman.web.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.model.TimedList;
import com.yunling.policyman.db.model.TimedMedia;

public class TimedPlayListVo {

	private Long id;
	private String startTime;
	private String endTime;
	private boolean loop = false;
	private String type;
	private List<TimedMediaVo> timedMedias;
	private String content;
	
	public TimedList toPlayList(TimedLayout layout) {
		TimedList tl = new TimedList();
		tl.setId(id);
		tl.setStartTime(startTime);
		tl.setEndTime(endTime);
		tl.setLoop(loop);
		tl.setType(type);
		tl.setMedias(collectMedias(layout));
		tl.setContent(content);
		return tl;
	}
	
	public List<TimedMedia> collectMedias(TimedLayout layout){
		List<TimedMedia> results = new ArrayList<TimedMedia>();
		Iterator<TimedMediaVo> it = timedMedias.iterator();
		while(it.hasNext()){
			results.add(it.next().toTimedMedia());
		}
		return results;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public List<TimedMediaVo> getTimedMedias() {
		return timedMedias;
	}

	public void setTimedMedias(List<TimedMediaVo> timedMedias) {
		this.timedMedias = timedMedias;
	}
	
}
