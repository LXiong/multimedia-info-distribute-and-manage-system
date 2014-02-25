package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.AuthenticateRecord;

public interface AuthenticateRecordMapper {
	public void insert(@Param(value="stbIp")String stbIp, @Param(value="customerName")String customerName, @Param(value="stbMac")String stbMac, @Param(value="authStatus")String authStatus,
			@Param(value="watchServerIp")String watchServerIp, @Param(value="watchServerPort")String watchServerPort);
	
	public List<AuthenticateRecord> getLogs(Map<String, Object> map);
	public int count(
			@Param("authenticateRecord") AuthenticateRecord authenticateRecord, 
			@Param("from") String from, 
			@Param("to") String to);
	public List<String> getResults();
}
