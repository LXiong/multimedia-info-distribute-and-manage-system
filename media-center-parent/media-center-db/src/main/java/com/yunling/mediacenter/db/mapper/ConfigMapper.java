package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.Config;

public interface ConfigMapper {
	public List<Config> getConfigs(Config config);
	public List<String> getProperties();
	
	public void addConfig(Config config);
	public void editConfig(Config config);
	public void deleteConfigs(Config config);
}
