package com.yunling.mediacenter.db.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.Stb;

/**
 * @author LingJun
 * @date 2010-10-9
 * 
 */
public interface StbService {
	/**
	 * Find all STBs by conditions
	 * 
	 * @param stb
	 * @return
	 */
	List<Stb> searchStbs(Stb stb, int begin, int end, Long userId);

	public List<Stb> listByStatus(String status);
	
	public List<Stb> listOnlineByGroup(long groupId);
	
	public List<Stb> listOnlineByGroupType(long typeId);
	
	public List<Stb> pageOnlineByGroup(long groupId, int page);

	public List<Stb> searchAuditStbs(Stb stb, int begin, int end, Long userId);

	public int countAuditStbs(Stb stb, Long userId);

	/**
	 * Find STB by MAC
	 * 
	 * @param stbMac
	 * @return
	 */
	Stb findByMac(String stbMac);

	List<String> getStbStatus();

	/**
	 * Update STB Token
	 * 
	 * @param stbMac
	 * @param accessToken
	 * @return
	 */
	public void updateToken(String stbMac, String accessToken);

	/**
	 * Update informations of a STB
	 * 
	 * @param STB
	 * @return
	 */
	void updateInfos(Stb stb);

	void audit(Stb stb);

	void updateGroup(Stb stb);

	void updateConf(Stb stb);

	/**
	 * Insert information of a STB
	 * 
	 * @param stbMac
	 * @param auditState
	 * @return
	 */
	void insertStb(String terminalIp, String stbMac, String customerName,
			String auditState);

	Count countStbs(Stb stb, Long userId);

	void macOnline(String mac, String termianlIp);

	void macOffline(String mac);

	void macPause(String mac);

	void macResume(String mac);
	
	public void updateStatus(String mac, String status);

	public void downloading(String mac);
	
	public void downloaded(String mac);

	void lastAccess(String mac);

	void updateActivePolicy(String mac, String activePolicy,
			int activePolicySuccessNum, int activePolicyFailedNum);

	Stb findAllByToken(String token);

	Stb findActiveByToken(String token);

	Integer getAuditCount();

	void delete(Stb stb);

	void offlineAllStb();

	List<Statistics> standAloneReport(String mac, Date dateinput,
			Date dateinput2);

	/**
	 * insert a new STB
	 * 
	 * @param stb
	 */
	void insert(Stb stb);

	List<Map<String, Object>> countStbByPolicy(long userId);
	
	void updatePerformance(Stb stb);

	List<String> listOnlineByGroupList(String groupList);
	
//	void updatePackage(String mac, long packageId);
	
	List<Stb> listMacIn(List<String> macs);

	List<Stb> pageOnlineByTypeAndGroup(Long typeId, Long groupId, int page);
	
	List<Stb> getStbsStatus(Map<String, Object> map);
	
	void updateFlowRate(Stb stb);
	
	/**
	 * 
	 * @author LingJun
	 * @date 2012-1-4
	 *
	 * @param packageId
	 * @param groupId
	 */
	void updatePackageByGroup(Long packageId, Long groupId);
	
	/**
	 * 
	 * @author LingJun
	 * @date 2012-1-4
	 *
	 * @param packageId
	 * @param mac
	 */
	void updatePackageByMac(Long packageId, String mac);
	
	/**
	 * 
	 * @author LingJun
	 * @date 2012-1-4
	 *
	 * @param groupId
	 * @param userId
	 * @return
	 */
	List<Stb> getStbByGroupId(Long groupId);
}
