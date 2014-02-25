package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.WebControlRecord;

@SuppressWarnings("rawtypes")
public interface WebControlRecordMapper {
	public void insertRecord(@Param(value = "stbIp") String stbIp,
			@Param(value = "command") String command,
			@Param(value = "customerName") String customerName,
			@Param(value = "stbMac") String stbMac,
			@Param(value = "watchIp") String watchServerIp,
			@Param(value = "watchPort") String watchServerPort);
	
	public List<WebControlRecord> getLogs(Map map);
	public Count count(Map map);
	public List<String> getCommands();
}
