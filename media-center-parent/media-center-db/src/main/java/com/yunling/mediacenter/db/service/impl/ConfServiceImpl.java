package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.ConfMapper;
import com.yunling.mediacenter.db.mapper.ConfigMapper;
import com.yunling.mediacenter.db.model.Conf;
import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.service.ConfService;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public class ConfServiceImpl extends BaseServiceImpl implements ConfService {
	private ConfMapper getMapper() {
		return getMapper(ConfMapper.class);
	}

	@Override
	public List<Conf> getConfs(final Conf conf) {
		return getMapper().getConfs(conf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yunling.mediacenter.db.service.ConfService#getConf(com.yunling.
	 * mediacenter.db.model.Conf)
	 */
	@Override
	public Conf getConf(final Conf conf) {
		return getMapper().getConf(conf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yunling.mediacenter.db.service.ConfService#addConf(com.yunling.
	 * mediacenter.db.model.Conf)
	 */
	@Override
	public void addConf(final Conf conf) {
		getMapper().addConf(conf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.ConfService#upateConf(com.yunling.
	 * mediacenter.db.model.Conf)
	 */
	@Override
	public void upateConf(final Conf conf) {
		getMapper().upateConf(conf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.ConfService#deleteConf(com.yunling
	 * .mediacenter.db.model.Conf)
	 */
	@Override
	public void deleteConf(final Config config, final Conf conf) {
		getMapper(ConfigMapper.class).deleteConfigs(config);
		getMapper().deleteConf(conf);
	}

	@Override
	public int countAll() {
		return getMapper().countAll();
	}

	@Override
	public List<Conf> list(int begin, int end) {
		return getMapper().list(begin, end);
	}
}
