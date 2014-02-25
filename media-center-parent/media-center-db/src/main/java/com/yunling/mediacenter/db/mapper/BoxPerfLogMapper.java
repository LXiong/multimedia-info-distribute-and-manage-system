package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.BoxPerfLog;

public interface BoxPerfLogMapper {

	Object add(BoxPerfLog box);

	List<BoxPerfLog> list(Map<String, Object> map);

	Integer selectPerfCount();

}
