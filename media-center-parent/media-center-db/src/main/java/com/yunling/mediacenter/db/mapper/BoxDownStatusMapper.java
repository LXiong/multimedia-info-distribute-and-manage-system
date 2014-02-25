/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

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
public interface BoxDownStatusMapper {
	
	void mergeStatus(BoxDownStatus downStatus);
	
//	void insert(BoxDownStatus downStatus);
//	
//	void update(BoxDownStatus downStatus);

	List<BoxDownStatus> getMacs(BoxDownStatus downStatus);
	
	List<BoxDownStatus> getList(BoxDownStatus downStatus);
	
	Long getFinishedNum(BoxDownStatus downStatus);
	
	BoxDownStatus getDownStatus(BoxDownStatus downStatus);
	
	List<GroupDownStatusReport> getReport(Map<String, Object> map);
	
	int countBy(Map<String, Object> map);
	
	List<GroupDownStatusReport> getReport2(Map<String, Object> map);
	
	int countBy2(Map<String, Object> map);
	
	List<StbDownStatusReport> stbDownStat(Map<String, Object> map);
	
	int countForDownStat(Map<String, Object> map);
	
	public List<GroupDownStatusReport> allDownStatus(Map<String, Object> map);
	
	public List<GroupDownStatusReport> allDownStatus2(Map<String, Object> map);
	
	public List<StbDownStatusReport> stbAllDownStat(Map<String, Object> map);
}
