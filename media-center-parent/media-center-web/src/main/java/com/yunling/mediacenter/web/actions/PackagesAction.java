package com.yunling.mediacenter.web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.Packages;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.db.service.PackagesService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2011-9-1
 * 
 */
public class PackagesAction extends AbstractUserAwareAction {
	private String jsonStr;
	private Long packageId;
	private String version;
	private String name;
	private String illustrate;

	private PackagesService packagesService;
	private GroupsService groupsService;
	private StbService stbService;
	private WebControl localService;

	private Logger log = LoggerFactory.getLogger(PackagesAction.class);
	HttpServletRequest rawRequest = ServletActionContext.getRequest();

	@Override
	public String execute() throws Exception {
		log.debug("packages manager.");
		List<Packages> packages = packagesService.packageList();
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(packages);
			log.debug(jsonStr);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @author LingJun
	 * @date 2011-9-2
	 * @return
	 */
	public String del() throws Exception {
		packagesService.delByVersion(version);
		return null;
	}

	/**
	 * @author LingJun
	 * @date 2011-9-4
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		log.debug("create package.");
		Packages packages = new Packages();
		if (version == null || version.isEmpty() || name == null
				|| name.isEmpty()) {
			return null;
		}
		packages.setVersion(version);
		packages.setName(name);
		packages.setIllustrate(illustrate);
		Date date = new Date();
		packages.setCreateTime(date);
		packages.setUpdateTime(date);
		packagesService.createPkg(packages);
		return null; // "create";
	}

	/**
	 * @author LingJun
	 * @date 2011-9-5
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		log.debug("update package.");
		Packages packages = new Packages();
		if (version == null || version.isEmpty()) {
			return null;
		}
		if (name == null || name.isEmpty()) {
			return null;
		}
		packages.setVersion(version);
		packages.setName(name);
		packages.setIllustrate(illustrate);
		packages.setUpdateTime(new Date());
		packagesService.updatePkg(packages);
		return null;
	}

	/**
	 * @date 2011-12-9
	 * @return
	 * @throws Exception
	 */
	public String updateStbPackageVersion() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String str = null;
		String[] stbmac = null;
		Long groupId = 0L;

		Packages pkg = packagesService.getPackageById(packageId);
		try {
			stbmac = rawRequest.getParameter("arry").split(",");
		} catch (Exception e) {
			String strId = rawRequest.getParameter("groupId");
			groupId = strId.isEmpty() ? 0L : Long.valueOf(strId);
			Groups group = new Groups();
			group.setGroupId(groupId);
			group.setPackageId(packageId);
			groupsService.update(group);
			stbService.updatePackageByGroup(packageId, groupId);
			List<Stb> stblist = stbService.getStbByGroupId(groupId);
			List<String> maclist = new ArrayList<String>();
			if (stblist != null && stblist.size() > 0) {
				for (Stb stb : stblist) {
					maclist.add(stb.getStbMac());
				}
				stbmac = (String[]) maclist.toArray(new String[maclist.size()]);

				if (pkg != null) {
					// map.put("md5", pkg.getVerflyCode());
					map.put("file_path", pkg.getName());
					map.put("version", pkg.getVersion());
					log.info("=============start call webControll");
					try {
						str = localService.updateSoftware(map, stbmac);
					} catch (Exception e1) {
						log.error("error", e1);
					}
					log.info("=========result localService.updateSoftware{}"
							+ str);
				}
			}
			// ActionContext.getContext().put("alert",this.getText("relevance.group.success"));
			// ActionContext.getContext().put("location","box-package!list.action");
			request.put("alert", this.getText("relevance.group.success"));
			request.put("location", "box-package!list.action");
			return "prompt";
		}
		for (String mac : stbmac) {
			stbService.updatePackageByMac(groupId, mac);
		}
		// map.put("md5", pkg.getVerflyCode());
		map.put("file_path", pkg.getName());
		map.put("version", pkg.getVersion());
		log.info("=============start call webControll");
		try {
			str = localService.updateSoftware(map, stbmac);
		} catch (Exception e) {
			log.error("error", e);
		}
		log.info("=========result localService.updateSoftware {}" + str);
		// ActionContext.getContext().put("alert",this.getText("relevance.device.success"));
		// ActionContext.getContext().put("location","box-package!list.action");
		request.put("alert", this.getText("relevance.device.success"));
		request.put("location", "box-package!list.action");
		return "prompt";
	}

	/**
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}

	/**
	 * @param jsonStr
	 *            the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
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

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the illustrate
	 */
	public String getIllustrate() {
		return illustrate;
	}

	/**
	 * @param illustrate
	 *            the illustrate to set
	 */
	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}

	/**
	 * @return the packagesService
	 */
	public PackagesService getPackagesService() {
		return packagesService;
	}

	/**
	 * @param packagesService
	 *            the packagesService to set
	 */
	public void setPackagesService(PackagesService packagesService) {
		this.packagesService = packagesService;
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
	 * @return the localService
	 */
	public WebControl getLocalService() {
		return localService;
	}

	/**
	 * @param localService
	 *            the localService to set
	 */
	public void setLocalService(WebControl localService) {
		this.localService = localService;
	}

}
