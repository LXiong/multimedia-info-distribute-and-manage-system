package com.yunling.mediacenter.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.ModuleFileMapper;
import com.yunling.mediacenter.db.model.ModuleFile;
import com.yunling.mediacenter.db.service.ModuleFileService;

public class ModuleFileServiceImpl extends BaseServiceImpl implements
		ModuleFileService {
	private Integer size;
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	private ModuleFileMapper getMapper() {
		return getMapper(ModuleFileMapper.class);
	}
	public Integer selectModuleCount() {
		return getMapper().selectModuleCount();
	}

	public Map<String, Object> list(final Integer page, final Integer sum ,final String module,final String version,final Date beginTime,final Date endTime) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String, Object> map = new HashMap<String, Object>();
		Integer count = sum%size==0 ? sum/size :sum/size+1;
		Integer count2=count;
		Integer page2=page;
		if(page<1){
			 page2=1;
		}
		if(page>count){
			 page2=count;
		}
		Integer page3=page2;
		if(page2==1){
			map.put("begin", page2);
			map.put("end", page2*size);
		}else{
			map.put("begin",((page2-1)*size)+1);
			map.put("end",(page2*size));
		}
		map.put("module",module);
		map.put("version",version);
		map.put("beginTime",beginTime);
		map.put("endTime",endTime);
		List<ModuleFile> list  =  getMapper().list(map);
 		resultMap.put("list",list);
 		resultMap.put("page",page3);
 		resultMap.put("count",count2);
 		resultMap.put("size",size);
 		return resultMap;
	}

	@Override
	public void deleteById(final long id) {
		getMapper().deleteById(id);
	}

	@Override
	public List<ModuleFile> selectModuleByBoxId(final long id) {
		return getMapper().selectModuleByBoxId(id);
	}


	@Override
	public List<ModuleFile> getModuleFiles(final ModuleFile moduleFile, 
			final int begin, final int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("moduleFile", moduleFile);
		return getMapper().getModuleFiles(moduleFile, begin, end);
	}
	@Override
	public void addModuleFile(final ModuleFile moduleFile) {
		getMapper().addModuleFile(moduleFile);
	}

	@Override
	public void update(final ModuleFile moduleFile) {
		getMapper().update(moduleFile);
	}
}
