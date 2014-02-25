package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.mapper.StbWarningInfoMapper;
import com.yunling.mediacenter.db.model.StbWarningInfo;
import com.yunling.mediacenter.db.service.StbWarningInfoService;

public class StbWarningInfoServiceImpl extends BaseServiceImpl implements StbWarningInfoService {
	Logger log = LoggerFactory.getLogger(StbWarningInfoServiceImpl.class);
	public StbWarningInfoMapper getMapper(){
		return getMapper(StbWarningInfoMapper.class);
	}
	@Override
	public StbWarningInfo getById(long id) {
		return getMapper().getById(id);
	}


	@Override
	public boolean save(StbWarningInfo info) {
		getMapper().insert(info);
		//getMapper().save(info.getStbMac(), info.getWarningType(), info.getFilePath(), info.getDescription(), info.getIncorrectMd5(), info.getCorrectMd5());
		return true;
	}
	@Override
	public List<StbWarningInfo> query(@Param(value="info")StbWarningInfo info) {
		return getMapper().query(info);
	}
	@Override
	public void delete(long id) {
		getMapper().delete(id);
	}
	@Override
	public void process(StbWarningInfo info) {
		info.setStatus("processed");
		getMapper().update(info);
	}

}
