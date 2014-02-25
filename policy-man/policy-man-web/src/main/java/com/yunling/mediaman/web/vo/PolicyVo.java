package com.yunling.mediaman.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.yunling.policyman.db.Constant;
import com.yunling.policyman.db.DateUtil;
import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.model.TimedLayout;

public class PolicyVo {
	
	protected Long id;
	protected String name;
	protected String comments;
	private String fmtStartTime;
	private String fmtEndTime;
	
	private List<TimedLayoutVo> layoutList;
	private List<PolicyMedia> mediaList;
	
	public void setFmtStartTime(String str) {
		this.fmtStartTime = str;
	}
	public void setFmtEndTime(String str) {
		this.fmtEndTime = str;
	}
	
	public String getFmtStartTime() {
		return fmtStartTime;
	}
	
	public String getFmtEndTime() {
		return fmtEndTime;
	}
	
	public Policy toPolicy() {
		Policy policy = new Policy();
		policy.setId(getId());
		policy.setName(getName());
		policy.setStartTime(DateUtil.parse(fmtStartTime, Constant.FULL_DATETIME));
		policy.setEndTime(DateUtil.parse(fmtEndTime, Constant.FULL_DATETIME));
		policy.setComments(getComments());
		policy.setLayoutList(collectLayoutList());
		policy.setMediaList(mediaList);
		return policy;
	}

	private List<TimedLayout> collectLayoutList() {
		if (layoutList == null || layoutList.size() == 0) {
			return null;
		}
		List<TimedLayout> newList = new ArrayList<TimedLayout>(layoutList.size());
		for(TimedLayoutVo tl : layoutList) {
			if (tl == null) continue;
			newList.add(tl.toTimedLayout());
		}
		return newList;
	}

	public List<PolicyMedia> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<PolicyMedia> mediaList) {
		this.mediaList = mediaList;
	}

	public List<TimedLayoutVo> getLayoutList() {
		return layoutList;
	}

	public void setLayoutList(List<TimedLayoutVo> layoutList) {
		this.layoutList = layoutList;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

}
