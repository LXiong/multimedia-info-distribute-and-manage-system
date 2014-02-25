/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Conf;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public interface ConfMapper {
	List<Conf> getConfs(Conf conf);
	Conf getConf(Conf conf);
	
	void addConf(Conf conf);
	void upateConf(Conf conf);
	void deleteConf(Conf conf);
	List<Conf> list(@Param("begin")int begin, @Param("end")int end);
	int countAll();
}
