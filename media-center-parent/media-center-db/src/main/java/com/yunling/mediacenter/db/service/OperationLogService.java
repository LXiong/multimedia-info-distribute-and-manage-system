package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.OperationLog;

public interface OperationLogService {

	void add(OperationLog log);

	int countBy(String from, String to, String isok);

	List<OperationLog> listBy(String from, String to, String isok, int begin,
			int end);
}
