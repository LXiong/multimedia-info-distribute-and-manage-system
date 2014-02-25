/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Config;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public interface ConfigService {
	List<Config> getConfigs(Config config);
	List<String> getProperties();
	
	void addConfig(Config config);
	void editConfig(Config config);
	void deleteConfigs(Config config);
}
