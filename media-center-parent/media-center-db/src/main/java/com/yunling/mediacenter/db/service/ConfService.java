package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Conf;
import com.yunling.mediacenter.db.model.Config;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public interface ConfService {
	List<Conf> getConfs(Conf conf);

	Conf getConf(Conf conf);

	void addConf(Conf conf);

	void upateConf(Conf conf);

	void deleteConf(Config config, Conf conf);

	int countAll();
	
	List<Conf> list(int begin, int end);
}
