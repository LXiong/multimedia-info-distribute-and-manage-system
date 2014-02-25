package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.StbWarningInfo;

public interface StbWarningInfoService {
	public StbWarningInfo getById(long id);
	
	public List<StbWarningInfo> query(StbWarningInfo info);
	
	public void process(StbWarningInfo info);
	
	//public void update(StbWarningInfo info);
	
	public boolean save(StbWarningInfo info);

	public void delete(long id); 
}
