package com.yunling.policyman.db.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.FastDateFormat;

import com.yunling.policyman.db.Constant;
import com.yunling.policyman.db.DateUtil;
import com.yunling.policyman.db.model.base.BasePolicy;
import com.yunling.policyman.db.model.helper.PolicyHelper;

public class Policy extends BasePolicy {
	private static FastDateFormat fdf = FastDateFormat.getInstance(Constant.FULL_DATETIME);
	
	private Set<Video> videos = new TreeSet<Video>();
	private List<TimedLayout> layoutList;
	private List<PolicyMedia> mediaList;

	private static String[] columnsInList = { "id", "name", "status" };
	private static String[] columnsInList2 = { "id", "name", "start_time","end_time","audit_at" };

	public String[] toStringArray() {
		String[] result = null;
		result = PolicyHelper.toSimpleArray(this, columnsInList);
		return result;
	}
	
	public String[] toStringArray2() {
		String[] result = null;
		result = PolicyHelper.toSimpleArray(this, columnsInList2);
		return result;
	}
	
	public void setFmtStartTime(String str) {
		setStartTime(DateUtil.parse(str, Constant.FULL_DATETIME));
	}
	public void setFmtEndTime(String str) {
		setEndTime(DateUtil.parse(str, Constant.FULL_DATETIME));
	}
	
	public String getFmtStartTime() {
		return getStartTime()!=null?fdf.format(getStartTime()):"";
	}
	
	public String getFmtEndTime() {
		return getEndTime()!=null?fdf.format(getEndTime()):null;
	}

	public Set<Video> getVideos(String type) {
		Set<Video> typeVideos = new HashSet<Video>();
		Iterator<Video> it = getVideos().iterator();
		while (it.hasNext()) {
			Video video = it.next();
			if (type.equalsIgnoreCase(video.getMediaType())) {
				typeVideos.add(video);
			}
		}
		return typeVideos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public List<TimedLayout> getLayoutList() {
		if (layoutList == null) {
			layoutList = new ArrayList<TimedLayout>();
		}
		return layoutList;
	}

	public void setLayoutList(List<TimedLayout> layoutList) {
		this.layoutList = layoutList;
	}

	public List<PolicyMedia> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<PolicyMedia> mediaList) {
		this.mediaList = mediaList;
	}

}