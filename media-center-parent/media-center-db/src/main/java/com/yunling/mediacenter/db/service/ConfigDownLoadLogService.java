package com.yunling.mediacenter.db.service;

import java.util.Map;

import com.yunling.mediacenter.db.model.ConfigDownLoadLog;


public interface ConfigDownLoadLogService {
	
	void add(ConfigDownLoadLog log);

	Integer selectConfigDownLoadLogCount();

	Map<String, Object> list(Integer page, Integer sum);
	

}
