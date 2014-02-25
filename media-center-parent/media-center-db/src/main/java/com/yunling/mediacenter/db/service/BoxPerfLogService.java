package com.yunling.mediacenter.db.service;

import java.util.Map;

import com.yunling.mediacenter.db.model.BoxPerfLog;

public interface BoxPerfLogService {

	void add(BoxPerfLog box);

	Integer selectPerfCount();

	Map<String, Object> list(Integer page, Integer sum);

}
