package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.LoginLog;

public interface LoginLogService {

	void add(LoginLog loginlog);

	int countBy(String from, String to, String isSuccess);
	
	List<LoginLog> listBy(String from, String to, String isSuccess,
			int begin, int end);
		
}

