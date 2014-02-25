package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.ConfigDownLoadLogMapper;
import com.yunling.mediacenter.db.model.ConfigDownLoadLog;
import com.yunling.mediacenter.db.service.ConfigDownLoadLogService;

public class ConfigDownLoadLogServiceImpl extends BaseServiceImpl implements ConfigDownLoadLogService {
	private Integer size;
	public void add(final ConfigDownLoadLog log) {
		 getMapper().addConfigDownLoadLog(log);
	}
	public Integer selectConfigDownLoadLogCount() {
		return getMapper().selectConfigDownLoadLogCount();
	}

	public Map<String, Object> list(final Integer page, final Integer sum) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String, Object> map = new HashMap<String, Object>();
		
		Integer count = sum%size==0 ? sum/size :sum/size+1;
		Integer page2=page;
		if(page<1){
			 page2=1;
		}
		if(page>count){
			 page2=count;
		}
		if(page2==1){
			map.put("begin", page2);
			map.put("end", page2*size);
		}else{
			map.put("begin",((page2-1)*size)+1);
			map.put("end",(page2*size));
		}
		List<ConfigDownLoadLog> list  = getMapper().list(map);
		
 		resultMap.put("list",list);
 		resultMap.put("page",page2);
 		resultMap.put("count",count);
 		resultMap.put("size",size);
 		return resultMap;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

	private ConfigDownLoadLogMapper getMapper() {
		return getMapper(ConfigDownLoadLogMapper.class);
	}
}
