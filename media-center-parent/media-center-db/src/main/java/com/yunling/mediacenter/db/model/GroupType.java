/**
 * 
 */
package com.yunling.mediacenter.db.model;

import java.util.List;

/**
 * @author LingJun
 * @date 2010-10-22
 * 
 */
public class GroupType {
	private Long typeId;
	private String typeName;
	private String typeDescription;
	
	private List<Groups> groups;
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
	 * @return the typeDescription
	 */
	public String getTypeDescription() {
		return typeDescription;
	}
	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public List<Groups> getGroups() {
		return groups;
	}
	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
	
	
}
