package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.ModuleFile;

public interface ModuleFileMapper {

	Integer selectModuleCount();

	List<ModuleFile> list(Map<String, Object> map);

	Object deleteById(long id);
	
	public List<ModuleFile> getModuleFiles(ModuleFile moduleFile, 
			int begin, int end);
	
	public void addModuleFile(ModuleFile moduleFile);
	
	public void update(ModuleFile moduleFile);
	
	List<ModuleFile> selectModuleByBoxId(long id);

}