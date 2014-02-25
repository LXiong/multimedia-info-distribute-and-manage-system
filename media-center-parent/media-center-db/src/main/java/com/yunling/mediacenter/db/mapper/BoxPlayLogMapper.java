package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.BoxPlayLog;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.VideoPlayTimes;

public interface BoxPlayLogMapper {

	Object add(BoxPlayLog box);

	public int countList(Long groupId);
	
	public List<Statistics> list(Map<String, Object> map);

	public int countBy(Map<String, Object> map);
	
	public List<BoxPlayLog> getPlayHistReport(Map<String, Object> map);
	
	public int countBy2(Map<String, Object> map);
	
	public List<VideoPlayTimes> playTimesReport(Map<String, Object> map);
	
	public List<BoxPlayLog> getAllPlayHistReport(Map<String, Object> map);
	
	public List<VideoPlayTimes> getAllPlayTimesReport(Map<String, Object> map);
}
