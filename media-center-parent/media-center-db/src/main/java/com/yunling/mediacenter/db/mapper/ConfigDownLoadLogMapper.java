package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.ConfigDownLoadLog;

public interface ConfigDownLoadLogMapper {

	Object addConfigDownLoadLog(ConfigDownLoadLog log);

	Integer selectConfigDownLoadLogCount();

	List<ConfigDownLoadLog> list(Map<String, Object> map);
	
	
	
}
