package com.yunling.mediacenter.db.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.ModuleFile;

public interface ModuleFileService {	
	Integer selectModuleCount();

	Map<String, Object> list(Integer page, Integer sum ,String module,String version,Date beginTime,Date endTime);

	void deleteById(long id);
	
	public List<ModuleFile> getModuleFiles(ModuleFile moduleFile, 
			int begin, int end);
	
	public Integer getSize();
	
	public void addModuleFile(ModuleFile moduleFile);
	
	public void update(ModuleFile moduleFile);
	
	List<ModuleFile> selectModuleByBoxId(long id);
	

}
