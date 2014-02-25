package com.yunling.mediacenter.db.model;

/**
 * @author LingJun
 * @date 2011-6-14
 * 
 */
public class GroupDownStatusReport {
	private String policyNumber;
	private String groupName;
	private Long finishedStb;
	private Long notfinishedStb;

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber
	 *            the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the finishedStb
	 */
	public Long getFinishedStb() {
		return finishedStb;
	}

	/**
	 * @param finishedStb
	 *            the finishedStb to set
	 */
	public void setFinishedStb(Long finishedStb) {
		this.finishedStb = finishedStb;
	}

	/**
	 * @return the notfinishedStb
	 */
	public Long getNotfinishedStb() {
		return notfinishedStb;
	}

	/**
	 * @param notfinishedStb
	 *            the notfinishedStb to set
	 */
	public void setNotfinishedStb(Long notfinishedStb) {
		this.notfinishedStb = notfinishedStb;
	}

}
