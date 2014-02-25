package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.Stb;

/**
 * @author LingJun
 * @date 2010-10-9
 * 
 */
@SuppressWarnings("rawtypes")
public interface StbMapper {
	public List<Stb> searchStbs(Map map);

	public List<Stb> listByStatus(@Param(value = "status") String status);

	public List<Stb> searchAuditStbs(@Param(value = "stb") Stb stb,
			@Param(value = "begin") int begin, @Param(value = "end") int end,
			@Param(value = "userId") Long userId);

	public int countAuditStbs(@Param(value = "stb") Stb stb,
			@Param(value = "userId") Long userId);

	public Stb findByMac(String stbMac);

	public List<String> getStbStatus();

	public void updateInfos(Stb stb);

	public void audit(Stb stb);

	public void updateGroup(Stb stb);

	public void updateConf(Stb stb);

	public Count countStbs(Map map);

	public Stb findAllByToken(@Param(value = "stbToken") String stbToken);

	public Stb findActiveByToken(@Param(value = "stbToken") String stbToken);

	public void updateToken(@Param(value = "stbMac") String stbMac,
			@Param(value = "accessToken") String accessToken);

	public void insertStb(@Param(value = "terminalIp") String terminalIp,
			@Param(value = "mac") String stbMac,
			@Param(value = "customerName") String customerName,
			@Param(value = "status") String auditState);

	public void macOnline(@Param(value = "mac") String mac,
			@Param(value = "terminalIp") String terminalIp);

	public void macOffline(@Param(value = "mac") String mac);

	public void macPause(@Param(value = "mac") String mac);

	public void macResume(@Param(value = "mac") String mac);

	public void updateStatus(@Param(value = "mac") String mac,
			@Param(value = "status") String status);

	public void lastAccess(@Param(value = "mac") String mac);

	public Integer getAuditCount();

	public void updateActivePolicy(
			@Param(value = "mac") String mac,
			@Param(value = "activePolicy") String activePolicy,
			@Param(value = "activePolicySuccessNum") int activePolicySuccessNum,
			@Param(value = "activePolicyFailedNum") int activePolicyFailedNum);

	public void delete(Stb stb);

	public void offlineAllStb();

	public List<Statistics> standAloneReport(Map map);

	public void insertNewStb(Stb stb);

	public List<Map<String, Object>> countByPolicy(@Param("userid") long userId);

	public List<Stb> listOnlineByGroup(@Param("group_id")long groupId);
	
	public List<Stb> pageOnlinesByGroup(@Param("group_id")long typeId, @Param("page")int page);

	public void updatePerformance(@Param(value="stb")Stb stb);

	public List<Stb> listOnlineByGroupType(@Param("type_id")long typeId);

	public List<String> listOnlineByGroupList(@Param("groupList") String groupList);
	
	/* delete by LJ on 5/1 2012
	public void updatePackage(@Param("mac")String mac, @Param("package_id")long packageId);
	*/
	
	public List<Stb> listMacIn(@Param("macs")List<String> macs);

	public List<Stb> pageOnlinesByTypeAndGroup(@Param("type_id")Long typeId, @Param("group_id")Long groupId,
			@Param("page")int page);

	public void updateDownloadStatus(@Param("mac")String mac, @Param("downloadStatus")String string);
	
	List<Stb> getStbsStatus(Map<String, Object> map);

	public void updateFlowRate(@Param(value="stb")Stb stb);
	
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
