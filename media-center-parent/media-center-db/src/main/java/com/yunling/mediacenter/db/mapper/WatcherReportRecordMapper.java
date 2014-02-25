package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WatcherReportRecord;

@SuppressWarnings("rawtypes")
public interface WatcherReportRecordMapper {
	public void insertRecord(@Param(value="mac")String mac, @Param(value = "stbIp") String stbIp,
			@Param(value = "stbPort") String stbPort,
			@Param(value = "watchIp") String watchServerIp,
			@Param(value = "watchPort") String watchServerPort);
	
	public List<WatcherReportRecord> getLogs(Map map);
	public Count count(Map map);
}
