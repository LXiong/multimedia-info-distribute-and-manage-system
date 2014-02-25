/**
 * 
 */
package com.yunling.mediacenter.db.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author LingJun
 * @date 2010-10-9
 * 
 */
public class Stb {
	private String stbMac;
	private String provinceId;
	private String provinceName;
	private String cityId;
	private String cityName;
	private String districtId;
	private String districtName;
	private String addrDetail;
	private Long groupId;
	private String groupName;
	private String stbToken;
	private String stbStatus;
	private String statusUpdateTime;
	private String stbId;
	private String stbDisabled;
	private Long confId;
	private String confName;
	private String activePolicy;
	private Long activePolicySuccessNum;
	private Long activePolicyFailedNum;
	private String customerName;
	private Date lastOfflineTime;
	private Date lastAccessTime;
	private String packageVersion;
	private String packageName;
	private String shopNo;
	private String shopName;
	private String terminalIp;
	private Long typeId;
	private String typeName;
	private String contacts;
	private String phone;
	
	private String nmem;
	private String cpu;
	private String disk;
	// add by L.J. on 8/17/2011
	private String downloadStatus;
	
	private String receivedKiloByte;
	private String sentKiloByte;
	
	public void addReceivedKiloByte(String receivedByte){
		long newReceivedKiloByte = (long)(Long.parseLong(receivedByte)/1024);
		receivedKiloByte = String.valueOf(newReceivedKiloByte);
//		if(receivedKiloByte == null){
//			receivedKiloByte = String.valueOf(newReceivedKiloByte);
//		}else{
//			long hasReceivedKiloByte = Long.parseLong(receivedKiloByte);
//			receivedKiloByte = String.valueOf(newReceivedKiloByte + hasReceivedKiloByte);
//		}
	}
	
	public void addSentKiloByte(String sentByte){
		long newSentKiloByte = (long)(Long.parseLong(sentByte)/1024);
		sentKiloByte = String.valueOf(newSentKiloByte);
//		if(sentKiloByte == null){
//			sentKiloByte = String.valueOf(newSentKiloByte);
//		}else{
//			long hasSentKiloByte = Long.parseLong(sentKiloByte);
//			sentKiloByte = String.valueOf(newSentKiloByte + hasSentKiloByte);
//		}
	}
	
	public boolean isDisabled(){
		return "t".equalsIgnoreCase(getStbStatus());
	}
	
	/**
	 * @return the stbMac
	 */
	public String getStbMac() {
		return stbMac;
	}
	/**
	 * @param stbMac the stbMac to set
	 */
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}
	/**
	 * @return the addrDetail
	 */
	public String getAddrDetail() {
		return addrDetail;
	}
	/**
	 * @param addrDetail the addrDetail to set
	 */
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}
	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the districtId
	 */
	public String getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the stbToken
	 */
	public String getStbToken() {
		return stbToken;
	}
	/**
	 * @param stbToken the stbToken to set
	 */
	public void setStbToken(String stbToken) {
		this.stbToken = stbToken;
	}
	/**
	 * @return the stbStatus
	 */
	public String getStbStatus() {
		return stbStatus;
	}
	/**
	 * @param stbStatus the stbStatus to set
	 */
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	/**
	 * @return the statusUpdateTime
	 */
	public String getStatusUpdateTime() {
		return statusUpdateTime;
	}
	/**
	 * @param statusUpdateTime the statusUpdateTime to set
	 */
	public void setStatusUpdateTime(String statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}
	/**
	 * @return the stbId
	 */
	public String getStbId() {
		return stbId;
	}
	/**
	 * @param stbId the stbId to set
	 */
	public void setStbId(String stbId) {
		this.stbId = stbId;
	}
	/**
	 * @return the stbDisabled
	 */
	public String getStbDisabled() {
		return stbDisabled;
	}
	/**
	 * @param stbDisabled the stbDisabled to set
	 */
	public void setStbDisabled(String stbDisabled) {
		this.stbDisabled = stbDisabled;
	}
	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	/**
	 * @return the confId
	 */
	public Long getConfId() {
		return confId;
	}

	/**
	 * @param confId the confId to set
	 */
	public void setConfId(Long confId) {
		this.confId = confId;
	}

	/**
	 * @return the confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the activePolicy
	 */
	public String getActivePolicy() {
		return activePolicy;
	}

	/**
	 * @param activePolicy the activePolicy to set
	 */
	public void setActivePolicy(String activePolicy) {
		this.activePolicy = activePolicy;
	}

	/**
	 * @return the activePolicySuccessNum
	 */
	public Long getActivePolicySuccessNum() {
		return activePolicySuccessNum;
	}

	/**
	 * @param activePolicySuccessNum the activePolicySuccessNum to set
	 */
	public void setActivePolicySuccessNum(Long activePolicySuccessNum) {
		this.activePolicySuccessNum = activePolicySuccessNum;
	}

	/**
	 * @return the activePolicyFailedNum
	 */
	public Long getActivePolicyFailedNum() {
		return activePolicyFailedNum;
	}

	/**
	 * @param activePolicyFailedNum the activePolicyFailedNum to set
	 */
	public void setActivePolicyFailedNum(Long activePolicyFailedNum) {
		this.activePolicyFailedNum = activePolicyFailedNum;
	}

	/**
	 * @return the packageVersion
	 */
	public String getPackageVersion() {
		return packageVersion;
	}

	/**
	 * @param packageVersion the packageVersion to set
	 */
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getLastOfflineTime() {
		return lastOfflineTime;
	}

	public void setLastOfflineTime(Date lastOfflineTime) {
		this.lastOfflineTime = lastOfflineTime;
	}

	/**
	 * @return the shopNo
	 */
	public String getShopNo() {
		return shopNo;
	}

	/**
	 * @param shopNo the shopNo to set
	 */
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the terminalIp
	 */
	public String getTerminalIp() {
		return terminalIp;
	}

	/**
	 * @param terminalIp the terminalIp to set
	 */
	public void setTerminalIp(String terminalIp) {
		this.terminalIp = terminalIp;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getNmem() {
		return nmem;
	}

	public void setNmem(String nmem) {
		this.nmem = nmem;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}
	
	/**
	 * @return the downloadStatus
	 */
	public String getDownloadStatus() {
		return downloadStatus;
	}

	/**
	 * @param downloadStatus the downloadStatus to set
	 */
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public List<String> performanceIndexes(){
		return Arrays.asList(new String[]{"cpu", "disk", "nmem"});
	}
	public void setPerformance(String key, String value){
		if(performanceIndexes().contains(key)){
			if("cpu".equalsIgnoreCase(key)){
				this.setCpu(value);
			}else if("nmem".equalsIgnoreCase(key)){
				this.setNmem(value);
			}else if("disk".equalsIgnoreCase(key)){
				this.setDisk(value);
			}
		}
	}
	
	public String getPerformance(String key){
		if(performanceIndexes().contains(key)){
			if("cpu".equalsIgnoreCase(key)){
				return this.getCpu();
			}else if("nmem".equalsIgnoreCase(key)){
				return this.getNmem();
			}else if("disk".equalsIgnoreCase(key)){
				return this.getDisk();
			}
		}
		return null;
	}

	public String getReceivedKiloByte() {
		return receivedKiloByte;
	}

	public void setReceivedKiloByte(String receivedKiloByte) {
		this.receivedKiloByte = receivedKiloByte;
	}

	public String getSentKiloByte() {
		return sentKiloByte;
	}

	public void setSentKiloByte(String sentKiloByte) {
		this.sentKiloByte = sentKiloByte;
	}

}
