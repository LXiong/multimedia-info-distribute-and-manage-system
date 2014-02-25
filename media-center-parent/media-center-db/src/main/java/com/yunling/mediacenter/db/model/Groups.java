/**
 * 
 */
package com.yunling.mediacenter.db.model;

/**
 * @author LingJun
 * @date 2010-10-25
 * 
 */
public class Groups {
	private Long groupId;
	private String groupName;
	private Long typeId;
	private String groupDescription;
	private Long confId;
	private String confName;
	private Long packageId;

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
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
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the groupDescription
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	/**
	 * @param groupDescription
	 *            the groupDescription to set
	 */
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * @return the confId
	 */
	public Long getConfId() {
		return confId;
	}

	/**
	 * @param confId
	 *            the confId to set
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
	 * @param confName
	 *            the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * @return the packageId
	 */
	public Long getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId
	 *            the packageId to set
	 */
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
}
