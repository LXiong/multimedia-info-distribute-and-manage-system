package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.StbLoginRecord;

@SuppressWarnings("rawtypes")
public interface StbLoginRecordMapper {
	public void insertRecord(@Param(value="type")String type, @Param(value = "mac") String mac,
			@Param(value = "stbIp") String stbIp,
			@Param(value = "stbPort") String stbPort,
			@Param(value = "watchIp") String watchIp,
			@Param(value = "watchPort") String watchPort,
			@Param(value = "status") String status);

	public List<StbLoginRecord> getLogs(Map map);
	
	public List<StbLoginRecord> lastLoginSuccessOn(String watchIp);

	public Count count(Map map);
	
	public List<String> getResults();
}
