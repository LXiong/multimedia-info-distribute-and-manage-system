package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.LoginLogMapper;
import com.yunling.mediacenter.db.model.LoginLog;
import com.yunling.mediacenter.db.service.LoginLogService;

public class LoginLogServiceImpl extends BaseServiceImpl implements
		LoginLogService {
	
	private LoginLogMapper getMapper() {
		return getMapper(LoginLogMapper.class);
	}

	@Override
	public void add(final LoginLog loginlog) {
		getMapper().addLoginLog(loginlog);
	}

	@Override
	public int countBy(String from, String to, String isSuccess) {
		return getMapper().countBy(from, to, isSuccess);
	}
	
	@Override
	public List<LoginLog> listBy(String from, String to, String isSuccess,
			int begin, int end) {
		return getMapper().listBy(from, to, isSuccess, begin, end);
	}
}
