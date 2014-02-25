package com.yunling.mediacenter.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.StbMapper;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.StbService;

/**
 * @author LingJun
 * @date 2010-10-9
 * 
 */
public class StbServiceImpl extends BaseServiceImpl implements StbService {

	private StbMapper getMapper() {
		return getMapper(StbMapper.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#searchStbs(com.yunling
	 * .mediacenter.web.model.Stb)
	 */
	@Override
	public List<Stb> searchStbs(final Stb stb, final int begin, final int end,
			final Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stb", stb);
		map.put("begin", begin);
		map.put("end", end);
		map.put("userId", userId);
		return getMapper().searchStbs(map);
	}

	@Override
	public List<Stb> listByStatus(String status) {
		return getMapper().listByStatus(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#countAuditStbs(com.yunling
	 * .mediacenter.db.model.Stb, java.lang.Long)
	 */
	@Override
	public int countAuditStbs(Stb stb, Long userId) {
		return getMapper().countAuditStbs(stb, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#searchAuditStbs(com.yunling
	 * .mediacenter.db.model.Stb, int, int, java.lang.Long)
	 */
	@Override
	public List<Stb> searchAuditStbs(Stb stb, int begin, int end, Long userId) {
		return getMapper().searchAuditStbs(stb, begin, end, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#findByMac(java.lang.String
	 * )
	 */
	@Override
	public Stb findByMac(final String stbMac) {
		return getMapper().findByMac(stbMac);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yunling.mediacenter.web.service.StbService#getStbStatus()
	 */
	@Override
	public List<String> getStbStatus() {
		return getMapper().getStbStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#insertStb(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void insertStb(final String terminalIp, final String stbMac,
			final String customerName, final String auditState) {
		getMapper().insertStb(terminalIp, stbMac, customerName, auditState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#updateToken(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void updateToken(final String stbMac, final String accessToken) {
		getMapper().updateToken(stbMac, accessToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#update(com.yunling.mediacenter
	 * .web.model.Stb)
	 */
	@Override
	public void updateInfos(final Stb stb) {
		getMapper().updateInfos(stb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#audit(com.yunling.mediacenter
	 * .web.model.Stb)
	 */
	@Override
	public void audit(final Stb stb) {
		getMapper().audit(stb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#updateGroup(com.yunling
	 * .mediacenter.db.model.Stb)
	 */
	@Override
	public void updateGroup(final Stb stb) {
		getMapper().updateGroup(stb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#updateConf(com.yunling.
	 * mediacenter.db.model.Stb)
	 */
	@Override
	public void updateConf(final Stb stb) {
		getMapper().updateConf(stb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.web.service.StbService#countStbs(com.yunling.
	 * mediacenter.web.model.Stb)
	 */
	@Override
	public Count countStbs(final Stb stb, final Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stb", stb);
		map.put("userId", userId);
		return getMapper().countStbs(map);
	}

	@Override
	public Stb findAllByToken(final String stbToken) {
		return getMapper().findAllByToken(stbToken);
	}

	@Override
	public Stb findActiveByToken(final String token) {
		return getMapper().findActiveByToken(token);
	}

	@Override
	public void macOnline(final String mac, final String termianlIp) {
		getMapper().macOnline(mac, termianlIp);

	}

	@Override
	public void macOffline(final String mac) {
		getMapper().macOffline(mac);

	}

	@Override
	public void macPause(final String mac) {
		getMapper().macPause(mac);
	}

	@Override
	public void macResume(final String mac) {
		getMapper().macResume(mac);
	}

	@Override
	public void updateStatus(String mac, String status) {
		getMapper().updateStatus(mac, status);
	}

	@Override
	public void lastAccess(String mac) {
		getMapper().lastAccess(mac);
	}

	@Override
	public Integer getAuditCount() {
		return getMapper().getAuditCount();
	}

	@Override
	public void updateActivePolicy(final String mac, final String activePolicy,
			final int activePolicySuccessNum, final int activePolicyFailedNum) {
		getMapper().updateActivePolicy(mac, activePolicy,
				activePolicySuccessNum, activePolicyFailedNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yunling.mediacenter.db.service.StbService#delete()
	 */
	@Override
	public void delete(final Stb stb) {
		getMapper().delete(stb);
	}

	@Override
	public List<Statistics> standAloneReport(final String mac,
			final Date dateinput, final Date dateinput2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", dateinput);
		map.put("end", dateinput2);
		map.put("mac", mac);
		return getMapper().standAloneReport(map);
	}

	@Override
	public void offlineAllStb() {
		getMapper().offlineAllStb();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#insert(com.yunling.mediacenter
	 * .db.model.Stb)
	 */
	@Override
	public void insert(final Stb stb) {
		getMapper().insertNewStb(stb);
	}

	@Override
	public List<Map<String, Object>> countStbByPolicy(long userId) {
		return getMapper().countByPolicy(userId);
	}

	@Override
	public List<Stb> listOnlineByGroup(long groupId) {
		return getMapper().listOnlineByGroup(groupId);
	}

	@Override
	public void updatePerformance(Stb stb) {
		getMapper().updatePerformance(stb);
	}

	@Override
	public List<Stb> listOnlineByGroupType(long typeId) {
		return getMapper().listOnlineByGroupType(typeId);
	}

	/*@Override
	public void updatePackage(String mac, long packageId) {
		getMapper().updatePackage(mac, packageId);
	}*/

	@Override
	public List<String> listOnlineByGroupList(String groupList) {
		return getMapper().listOnlineByGroupList(groupList);
	}

	@Override
	public List<Stb> pageOnlineByGroup(long groupId, int page) {
		return getMapper().pageOnlinesByGroup(groupId, page);
	}

	@Override
	public List<Stb> listMacIn(List<String> macs) {
		return getMapper().listMacIn(macs);
	}

	@Override
	public List<Stb> pageOnlineByTypeAndGroup(Long typeId, Long groupId,
			int page) {
		return getMapper().pageOnlinesByTypeAndGroup(typeId, groupId, page);
	}

	@Override
	public void downloaded(String mac) {
		getMapper().updateDownloadStatus(mac, "updated");
	}

	@Override
	public void downloading(String mac) {
		getMapper().updateDownloadStatus(mac, "updating");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#getStbsStatus(java.util
	 * .Map)
	 */
	@Override
	public List<Stb> getStbsStatus(Map<String, Object> map) {
		return getMapper().getStbsStatus(map);
	}

	@Override
	public void updateFlowRate(Stb stb) {
		getMapper().updateFlowRate(stb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#updatePackageByGroup(java
	 * .lang.Long, java.lang.Long)
	 */
	@Override
	public void updatePackageByGroup(Long packageId, Long groupId) {
		getMapper().updatePackageByGroup(packageId, groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#updatePackageByMac(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public void updatePackageByMac(Long packageId, String mac) {
		getMapper().updatePackageByMac(packageId, mac);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.StbService#getStbByGroupId(java.lang
	 * .Long, java.lang.Long)
	 */
	@Override
	public List<Stb> getStbByGroupId(Long groupId) {
		return getMapper().getStbByGroupId(groupId);
	}

}
