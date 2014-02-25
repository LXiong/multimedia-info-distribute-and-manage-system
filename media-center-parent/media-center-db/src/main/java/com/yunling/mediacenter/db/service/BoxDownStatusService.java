/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.BoxDownStatus;
import com.yunling.mediacenter.db.model.GroupDownStatusReport;
import com.yunling.mediacenter.db.model.StbDownStatusReport;

/**
 * @author LingJun
 * @date 2010-12-28
 * 
 */
public interface BoxDownStatusService {
	public void insertOrUpdate(final BoxDownStatus downStatus);
//	public void insert(BoxDownStatus downStatus);
//	
//	public void update(BoxDownStatus downStatus);
	
	public List<BoxDownStatus> getMacs(BoxDownStatus downStatus);
	
	public List<BoxDownStatus> getList(BoxDownStatus downStatus);
	
	public Long getFinishedNum(BoxDownStatus downStatus);
	
	public BoxDownStatus getDownStatus(BoxDownStatus downStatus);
	
	public List<GroupDownStatusReport> getReport(Map<String, Object> map);
	
	public int countBy(Map<String, Object> map);
	
	public List<GroupDownStatusReport> getReport2(Map<String, Object> map);
	
	public int countBy2(Map<String, Object> map);
	
	public List<StbDownStatusReport> stbDownStat(Map<String, Object> map);
	
	public int countForDownStat(Map<String, Object> map);
	
	public List<GroupDownStatusReport> allDownStatus(Map<String, Object> map);
	
	public List<GroupDownStatusReport> allDownStatus2(Map<String, Object> map);
	
	public List<StbDownStatusReport> stbAllDownStat(Map<String, Object> map);
}
