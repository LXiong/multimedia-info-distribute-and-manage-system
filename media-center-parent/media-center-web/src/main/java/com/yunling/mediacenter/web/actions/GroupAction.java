package com.yunling.mediacenter.web.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

@Results( {
		@Result(name = "redirect-deleteGroup", location = "group!deleteGroup.action", type = "redirect"),
		@Result(name = "addGroup", location = "group!addGroup.action", type = "redirect") })
public class GroupAction extends AbstractUserAwareAction {
	private GroupTypeService groupTypeService;
	private List<GroupType> typeList;
	private GroupsService groupsService;
	private StbService stbService;
	private List<Groups> groupList;
	private Long typeId;
	private String typeName;
	private String typeDescription;
	private Long groupId;
	private String groupName;
	private String groupDescription;
	private Long confId;
	private List<Groups> groupsList;
	private String msg = null;
	private HttpServletResponse response = ServletActionContext.getResponse();

	public String execute() throws Exception {
		typeList = groupTypeService.getTypeList(getCurrentUserId());
		Groups groups = new Groups();
		groups.setConfId(0L);
		groupList = groupsService.getGroupList(groups, getCurrentUserId());
		List<Long> typeIdList = new Vector<Long>();
		Iterator<GroupType> itg = typeList.iterator();
		while (itg.hasNext()) {
			typeIdList.add(itg.next().getTypeId());
		}
		groupsList = new Vector<Groups>();
		Iterator<Groups> it = groupList.iterator();
		while (it.hasNext()) {
			groups = it.next();
			if ( !typeIdList.contains(groups.getTypeId()) ) {
				groupsList.add(groups);
			}
		}
		log.debug("type list size {}, group list size {}", typeList.size(),
				groupList.size());
		log.debug("groupsList size {}", groupsList.size());
		return SUCCESS;
	}

	/**
	 * edit group name
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editGroupName() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Groups groups = new Groups();
		groups.setTypeId(typeId);
		groups.setGroupName(groupName);
		if (groupsService.getGroup(groups) != null) {
			msg = groupName + " " + this.getText("group.exist") + ". ";
			response.getWriter().print(msg);
			return null;
		}
		groups.setGroupId(groupId);
		groupsService.update(groups);
		log.debug("new group name is: {}", groupName);
		/*
		 * if(typeName != null && !"".equals(typeName)) { GroupType newType =
		 * new GroupType(); GroupType groupType; newType.setTypeName(typeName);
		 * groupType = groupTypeService.getGroupType(newType); if(groupType ==
		 * null) { newType.setTypeId(typeId); groupTypeService.update(newType);
		 * } else { msg = msg + typeName + " " + this.getText("type.exist") +
		 * ". "; } if(groupName != null && !"".equals(groupName)) { Groups
		 * newGroup = new Groups(); Groups groups; newGroup.setGroupId(0L);
		 * newGroup.setTypeId(typeId); newGroup.setGroupName(groupName); groups
		 * = groupsService.getGroup(newGroup); if(groups == null) {
		 * newGroup.setGroupId(groupId); groupsService.update(newGroup); } else
		 * { msg = msg + groupName + " " + this.getText("group.exist") + ". "; }
		 * } } log.debug("msg info: {}", msg);
		 */
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editGroupDesp() throws Exception {
		Groups groups = new Groups();
		groups.setGroupId(groupId);
		groups.setGroupDescription(groupDescription);
		groupsService.update(groups);
		log.debug("new group description is: {}", groupDescription);
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editTypeName() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		GroupType groupType = new GroupType();
		groupType.setTypeName(typeName);
		if (groupTypeService.getGroupType(groupType) != null) {
			msg = typeName + " " + this.getText("type.exist") + ". ";
			response.getWriter().print(msg);
			return null;
		}
		groupType.setTypeId(typeId);
		groupTypeService.update(groupType);
		log.debug("new group type name is: {}", typeName);
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editTypeDesp() throws Exception {
		GroupType groupType = new GroupType();
		groupType.setTypeId(typeId);
		groupType.setTypeDescription(typeDescription);
		groupTypeService.update(groupType);
		return null;
	}

	/**
	 * Add group
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addGroup() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Groups groups = new Groups();
		groups.setGroupName(groupName);
		groups.setTypeId(typeId);
		log.debug("the name of new group is {}", groupName);
		if (groupsService.getGroup(groups) == null) {
			if (groupDescription == null) {
				groupDescription = "";
			}
			groups.setGroupDescription(groupDescription.trim());
			groupsService.addGroups(groups);
			// response.getWriter().print(this.getText("save"));
		} else {
			response.getWriter().print(this.getText("group.exist"));
		}
		/*
		 * GroupType newType = new GroupType(); GroupType groupType;
		 * newType.setTypeName(typeName); groupType =
		 * groupTypeService.getGroupType(newType); // type not exists
		 * if(groupType == null) { if(typeDescription == null) { typeDescription
		 * = ""; } newType.setTypeDescription(typeDescription.trim());
		 * groupTypeService.addGroupType(newType);
		 * 
		 * if(groupName != null && !"".equals(groupName)) {
		 * groups.setTypeId(groupTypeService.getGroupType(newType).getTypeId());
		 * if(groupDescription == null) { groupDescription = ""; }
		 * groups.setGroupDescription(groupDescription.trim());
		 * groupsService.addGroups(groups); } } else { // type exists
		 * if(groupName != null && !"".equals(groupName)) {
		 * groups.setTypeId(groupType.getTypeId()); // If group not exists
		 * if(groupsService.getGroup(groups) == null) { if(groupDescription ==
		 * null) { groupDescription = ""; }
		 * groups.setGroupDescription(groupDescription.trim());
		 * groupsService.addGroups(groups); } else {
		 * response.getWriter().print(this.getText("group.exist")); } } else {
		 * response.getWriter().print(this.getText("type.exist")); } }
		 */

		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addType() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		GroupType newType = new GroupType();
		newType.setTypeName(typeName);
		log.debug("the name of new type is {}", typeName);
		if (groupTypeService.getGroupType(newType) == null) {
			if (typeDescription == null) {
				typeDescription = "";
			}
			newType.setTypeDescription(typeDescription.trim());
			groupTypeService.addGroupType(newType);
		} else {
			response.getWriter().print(this.getText("type.exist"));
		}
		return null;
	}

	/**
	 * get the types
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getType() throws Exception {
		setLayout(JSON);
		typeList = groupTypeService.getTypeList(getCurrentUserId());
		return "typelist";
	}

	/**
	 * get the type of a group
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getGroupType() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Groups groups = new Groups();
		if (groupId != 0L) {
			groups.setGroupId(groupId);
			groups = groupsService.getGroup(groups);
			response.getWriter().write(groups.getTypeId().toString());
		}
		return null;
	}

	/**
	 * Get Group list by typeId
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getGroup() throws Exception {
		setLayout(JSON);
		if (typeId != null && typeId != 0L) {
			Groups groups = new Groups();
			groups.setTypeId(typeId);
			groupList = groupsService.getGroupListByType(groups);
		}
		return "list";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getGroupNameById() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Groups groups = new Groups();
		if (groupId != 0) {
			groups.setGroupId(groupId);
			response.getWriter().print(
					groupsService.getGroup(groups).getGroupName());
		}
		return null;
	}

	/**
	 * Delete Type or Group
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteGroup() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Stb stb = new Stb();
		stb.setGroupId(groupId);
		if (stbService.countStbs(stb, getCurrentUserId()).getCounts() > 0) {
			msg = this.getText("group.not.null") + ", "
					+ this.getText("Cannot") + this.getText("Delete");
			response.getWriter().print(msg);
		} else {
			Groups groups = new Groups();
			groups.setGroupId(groupId);
			groupsService.deleteGroup(groups);
		}
		/*
		 * Groups groups = new Groups(); groups.setTypeId(typeId);
		 * if(groupsService.getGroupListByType(groups).size() > 0) { if(groupId
		 * == null || groupId == 0) { msg = this.getText("type.not.null") + ", "
		 * + this.getText("Cannot") + this.getText("Delete");
		 * response.getWriter().print(msg); } else { Stb stb = new Stb();
		 * stb.setGroupId(groupId); if( stbService.countStbs(stb,
		 * getUser().getUserId()).getCounts() > 0) { msg =
		 * this.getText("group.not.null") + ", " + this.getText("Cannot") +
		 * this.getText("Delete"); response.getWriter().print(msg); } else {
		 * groups.setGroupId(groupId); groupsService.deleteGroup(groups); } //
		 * msg = this.getText("Group") + " " + groupId + " " +
		 * //this.getText("Delete") + this.getText("Success"); } } else {
		 * GroupType groupType = new GroupType(); groupType.setTypeId(typeId);
		 * groupTypeService.deleteGroupType(groupType); //msg =
		 * this.getText("GroupType") + " " + typeId + " " +
		 * //this.getText("Delete") + this.getText("Success"); }
		 */
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteType() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Groups groups = new Groups();
		groups.setTypeId(typeId);
		if (groupsService.getGroupListByType(groups).size() > 0) {
			msg = this.getText("type.not.null") + ", " + this.getText("Cannot")
					+ this.getText("Delete");
			response.getWriter().print(msg);
		} else {
			GroupType groupType = new GroupType();
			groupType.setTypeId(typeId);
			groupTypeService.deleteGroupType(groupType);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		Groups groups = new Groups();
		groups.setGroupId(groupId);
		groups.setConfId(confId);
		groupsService.update(groups);
		return null;
	}

	/**
	 * @return the groupTypeService
	 */
	public GroupTypeService getGroupTypeService() {
		return groupTypeService;
	}

	/**
	 * @param groupTypeService
	 *            the groupTypeService to set
	 */
	public void setGroupTypeService(GroupTypeService groupTypeService) {
		this.groupTypeService = groupTypeService;
	}

	/**
	 * @return the typeList
	 */
	public List<GroupType> getTypeList() {
		return typeList;
	}

	/**
	 * @param typeList
	 *            the typeList to set
	 */
	public void setTypeList(List<GroupType> typeList) {
		this.typeList = typeList;
	}

	/**
	 * @return the groupsService
	 */
	public GroupsService getGroupsService() {
		return groupsService;
	}

	/**
	 * @param groupsService
	 *            the groupsService to set
	 */
	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	/**
	 * @return the stbService
	 */
	public StbService getStbService() {
		return stbService;
	}

	/**
	 * @param stbService
	 *            the stbService to set
	 */
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	/**
	 * @return the groupList
	 */
	public List<Groups> getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList
	 *            the groupList to set
	 */
	public void setGroupList(List<Groups> groupList) {
		this.groupList = groupList;
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
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
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
	 * @param typeDescription
	 *            the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

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
	 * @return the groupsList
	 */
	public List<Groups> getGroupsList() {
		return groupsList;
	}

	/**
	 * @param groupsList
	 *            the groupsList to set
	 */
	public void setGroupsList(List<Groups> groupsList) {
		this.groupsList = groupsList;
	}

}
