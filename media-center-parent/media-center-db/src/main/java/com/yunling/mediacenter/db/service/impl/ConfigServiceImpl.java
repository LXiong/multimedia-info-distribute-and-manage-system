package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.ConfigMapper;
import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.service.ConfigService;

public class ConfigServiceImpl extends BaseServiceImpl implements ConfigService {
	private ConfigMapper getMapper() {
		return getMapper(ConfigMapper.class);
	}
	@Override
	public List<Config> getConfigs(final Config config) {
		return getMapper().getConfigs(config);
	}

	@Override
	public List<String> getProperties() {
		return getMapper().getProperties();
	}

	@Override
	public void addConfig(final Config config) {
		getMapper().addConfig(config);
	}

	@Override
	public void editConfig(final Config config) {
		getMapper().editConfig(config);
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.ConfigService#deleteConfigs(com.yunling.mediacenter.db.model.Config)
	 */
	@Override
	public void deleteConfigs(final Config config) {
		getMapper().deleteConfigs(config);
	}

}
