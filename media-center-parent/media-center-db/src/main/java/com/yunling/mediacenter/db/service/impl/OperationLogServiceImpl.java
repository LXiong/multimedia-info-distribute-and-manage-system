package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.OperationLogMapper;
import com.yunling.mediacenter.db.model.OperationLog;
import com.yunling.mediacenter.db.service.OperationLogService;

public class OperationLogServiceImpl extends BaseServiceImpl implements
		OperationLogService {

	private OperationLogMapper getMapper() {
		return getMapper(OperationLogMapper.class);
	}

	@Override
	public void add(final OperationLog log) {
		getMapper().addOperationLog(log);
	}

	@Override
	public int countBy(String from, String to, String isok) {
		return getMapper().countBy(from, to, isok);
	}

	@Override
	public List<OperationLog> listBy(String from, String to, String isok,
			int begin, int end) {
		return getMapper().listBy(from, to, isok, begin, end);
	}

}
