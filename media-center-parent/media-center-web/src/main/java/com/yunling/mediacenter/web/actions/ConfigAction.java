package com.yunling.mediacenter.web.actions;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.service.ConfigService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public class ConfigAction extends AbstractUserAwareAction {
	private List<String> properties;
	private List<Config> configs;
	private List<Groups> groupList;
	private ConfigService configService;
	private GroupsService groupsService;
	private Long confId;
	private Long configId;
	private String configKey;
	private String configValue;
	private String confName;
	private String updateTime;
	private String userName;
	private HttpServletResponse rawResponse = ServletActionContext.getResponse();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return null;
	}

	/**
	 * get the configuration properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getProperty() throws Exception {
		properties = configService.getProperties();
		return SUCCESS;
	}

	/**
	 * add configuration property
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		if (configValue != null && !"".equals(configValue)) {
			Config config = new Config();
			config.setConfId(confId);
			config.setConfigKey(configKey);
			List<Config> configs2 = configService.getConfigs(config);
			if (configs2!=null && configs2.size() > 0) {
				rawResponse.getWriter().print(
						configKey + " " + this.getText("config.property.exist")
								+ ". " + configKey + " "
								+ this.getText("config.property.not.add"));
				return null;
			}
			config.setConfigValue(configValue);

			configService.addConfig(config);
		} else {
			rawResponse.getWriter().print(
					configKey + " " + this.getText("config.property.null")
							+ ". " + configKey + " "
							+ this.getText("config.property.not.add"));
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confContent() throws Exception {
		Config config = new Config();
		config.setConfId(confId);
		configs = configService.getConfigs(config);
		Groups group = new Groups();
		group.setConfId(confId);
		groupList = groupsService.getGroupList(group, getCurrentUserId());

		this.userName = this.getUserName();
		return "content";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		if (configValue != null && !"".equals(configValue)) {
			Config config = new Config();
			config.setConfigId(configId);
			config.setConfigValue(configValue);

			configService.editConfig(config);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteConfig() throws Exception {
		Config config = new Config();
		config.setConfId(confId);
		configService.deleteConfigs(config);
		return null;
	}

	/**
	 * @return the configService
	 */
	public ConfigService getConfigService() {
		return configService;
	}

	/**
	 * @param configService
	 *            the configService to set
	 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
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
	 * @return the properties
	 */
	public List<String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<String> properties) {
		this.properties = properties;
	}

	/**
	 * @return the configs
	 */
	public List<Config> getConfigs() {
		return configs;
	}

	/**
	 * @param configs
	 *            the configs to set
	 */
	public void setConfigs(List<Config> configs) {
		this.configs = configs;
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
	 * @return the configId
	 */
	public Long getConfigId() {
		return configId;
	}

	/**
	 * @param configId
	 *            the configId to set
	 */
	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	/**
	 * @return the configKey
	 */
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * @param configKey
	 *            the configKey to set
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	/**
	 * @return the configValue
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * @param configValue
	 *            the configValue to set
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
