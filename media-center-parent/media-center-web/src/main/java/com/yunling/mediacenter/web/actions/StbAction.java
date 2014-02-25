package com.yunling.mediacenter.web.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.yunling.mediacenter.db.model.Conf;
import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.model.Count;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.model.StbOperationLog;
import com.yunling.mediacenter.db.model.Videos;
import com.yunling.mediacenter.db.service.ConfigService;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.db.service.StbOperationLogService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.db.service.VideosService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.web.WatchServerRef;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-10-9
 * 
 */
public class StbAction extends AbstractUserAwareAction {
	private List<Stb> stbs;
	// private List<GroupType> typeList;
	private String macStr;
	private String stbMac;
	private String provinceId;
	private String cityId;
	private String districtId;
	private String addrDetail;
	private String stbStatus;
	private String stbStatusEdit;
	private Long groupId;
	private Long typeId;
	private String activePolicy;
	private String shopNo;
	private String shopName;
	private String contacts;
	private String phone;
	private int pageNums; // Total pages
	private int page; // Current page
	private WatchServerRef watchServerRef;
	private WebControl localService;
	//private Map<String, String> mapStatus;
	private Map<String, HashMap<String, Integer>> mapStatistics;
	private String playerOperation;
	private String policyKey;
	private Long confId;
	private String updateTime;
	private Date dateinput;
	private Date dateinput2;
	private String mac;
//	private String pic;
//	private String picUrl;
	private InputStream image4show;
	private InputStream inputStream;
	private String contentLength;
	private String contentDisposition;
	private String picStr;
	private String picture;
	private String stbListJson;
	private String filesKey;
	private String fileStr;

	private StbService stbService;
	private ConfigService configService;
	private StbOperationLogService stbOperaLogService;
	private GroupTypeService groupTypeService;
	private GroupsService groupsService;
	private VideosService videosService;
	
	private HttpServletResponse rawResponse = ServletActionContext
			.getResponse();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public String execute() throws Exception {
		//setLayout(JSON);
		/*List<String> status = stbService.getStbStatus();
		if (status != null) {
			mapStatus = new HashMap<String, String>();
			for (int i = 0; i < status.size(); i++) {
				if (!("".equals(status.get(i))) && status.get(i) != null) {
					mapStatus.put(status.get(i).trim(), status.get(i).trim());
				}
			}
		}*/
		this.getList();
		StringBuffer jsonStr = new StringBuffer("");
		if(stbs != null) {
			Iterator<Stb> it = stbs.iterator();
			Stb temp = null;
			while(it.hasNext()) {
				jsonStr.append("{");
				temp = it.next();
				String policyIntegrity = "";
				if (temp.getActivePolicyFailedNum() == null
						|| temp.getActivePolicyFailedNum().intValue() > 0) {
					policyIntegrity = this.getText("stb.policy.not.complete");
				} else {
					policyIntegrity = this.getText("stb.policy.complete");
				}
				this.addProp(jsonStr, "stbMac", temp.getStbMac());
				jsonStr.append(",");
				this.addProp(jsonStr, "provinceId", temp.getProvinceId());
				jsonStr.append(",");
				this.addProp(jsonStr, "provinceName", temp.getProvinceName());
				jsonStr.append(",");
				this.addProp(jsonStr, "cityId", temp.getCityId());
				jsonStr.append(",");
				this.addProp(jsonStr, "cityName", temp.getCityName());
				jsonStr.append(",");
				this.addProp(jsonStr, "districtId", temp.getDistrictId());
				jsonStr.append(",");
				this.addProp(jsonStr, "districtName", temp.getDistrictName());
				jsonStr.append(",");
				this.addProp(jsonStr, "addrDetail", temp.getAddrDetail());
				jsonStr.append(",");
				this.addProp(jsonStr, "stbStatus", temp.getStbStatus());
				jsonStr.append(",");
				this.addProp(jsonStr, "groupId", temp.getGroupId() == null ? null : temp.getGroupId().toString());
				jsonStr.append(",");
				this.addProp(jsonStr, "groupName", temp.getGroupName());
				jsonStr.append(",");
				this.addProp(jsonStr, "confId", temp.getConfId() == null ? null : temp.getConfId().toString());
				jsonStr.append(",");
				this.addProp(jsonStr, "confName", temp.getConfName());
				jsonStr.append(",");
				this.addProp(jsonStr, "activePolicy", temp.getActivePolicy());
				jsonStr.append(",");
				this.addProp(jsonStr, "activePolicySuccessNum", temp.getActivePolicySuccessNum() == null ? null : temp.getActivePolicySuccessNum().toString());
				jsonStr.append(",");
				this.addProp(jsonStr, "activePolicyFailedNum", temp.getActivePolicyFailedNum() == null ? null : temp.getActivePolicyFailedNum().toString());
				jsonStr.append(",");
				this.addProp(jsonStr, "downloadStatus", temp.getDownloadStatus());
				jsonStr.append(",");
				this.addProp(jsonStr, "policyIntegrity", policyIntegrity);
				jsonStr.append(",");
				Date date = temp.getLastOfflineTime();
				String dateString = null;
				if(date != null) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dateString = format.format(date);
				}
				log.debug(dateString);
				this.addProp(jsonStr, "lastOfflineTime", dateString);
				jsonStr.append(",");
				this.addProp(jsonStr, "packageVersion", temp.getPackageVersion());
				jsonStr.append(",");
				this.addProp(jsonStr, "packageName", temp.getPackageName());
				jsonStr.append(",");
				this.addProp(jsonStr, "terminalIp", temp.getTerminalIp());
				jsonStr.append(",");
				this.addProp(jsonStr, "shopNo", temp.getShopNo());
				jsonStr.append(",");
				this.addProp(jsonStr, "shopName", temp.getShopName());
				jsonStr.append(",");
				this.addProp(jsonStr, "contacts", temp.getContacts());
				jsonStr.append(",");
				this.addProp(jsonStr, "phone", temp.getPhone());
				jsonStr.append(",");
				this.addProp(jsonStr, "cpu", temp.getCpu());
				jsonStr.append(",");
				this.addProp(jsonStr, "nmem", temp.getNmem());
				jsonStr.append(",");
				this.addProp(jsonStr, "disk", temp.getDisk());
				jsonStr.append(",");
				this.addProp(jsonStr, "typeName", temp.getTypeName());
				jsonStr.append("}");
				if(it.hasNext()) {
					jsonStr.append(",");
				}
			}
		}
		this.stbListJson = jsonStr.toString();
		log.info("jsonStr: {}", stbListJson);
		return SUCCESS;
	}
	
	private void addProp(StringBuffer jsonStr, String name, String value) {
		jsonStr.append("\"");
		jsonStr.append(name);
		jsonStr.append("\"");
		jsonStr.append(":");
		jsonStr.append("\"");
		jsonStr.append(value==null ? "" : value);
		jsonStr.append("\"");
		//jsonStr.append("\"" + name + "\":\"" + value==null?"":value + "\"");
	}

	/**
	 * Get STBs by conditions
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stbList() throws Exception {
		setLayout(JSON);
		this.getList();
		return "list";
	}

	/**
	 * Update STB infos
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateStb() throws Exception {
		Stb stb = new Stb();
		stb.setStbMac(stbMac);
		log.debug("update infos: mac {}", stbMac);
		stb.setProvinceId(provinceId);
		stb.setCityId(cityId);
		stb.setDistrictId(districtId);
		stb.setAddrDetail(addrDetail);
		stb.setTypeId(typeId);
		stb.setGroupId(groupId);
		stb.setShopNo(shopNo);
		stb.setShopName(shopName);
		log.debug("update infos: shopno {}, shopname {}", shopNo, shopName);
		stb.setContacts(contacts);
		stb.setPhone(phone);
		log.debug("update infos: contacts {}, phone {}", contacts, phone);
		if ("-1".equals(stbStatusEdit)) {
			stb.setStbStatus("");
		} else {
			stb.setStbStatus(stbStatusEdit);
		}

		stbService.updateInfos(stb);

		return null;
	}

	/**
	 * Update STB group
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateGroup() throws Exception {
		Stb stb = new Stb();
		stb.setStbMac(stbMac);
		stb.setGroupId(groupId);

		stbService.updateGroup(stb);

		return null;
	}

	/**
	 * Get STB by MAC
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *             public String edit() throws Exception { setLayout(JSON);
	 * 
	 *             stb = stbService.findByMac(stbMac);
	 * 
	 *             return SUCCESS; }
	 */
	/**
	 * Get STBs by GroupId
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findByGroupId() throws Exception {
		setLayout(JSON);
		Stb stb = new Stb();
		stb.setGroupId(groupId);
		Count stbCount = stbService.countStbs(stb, getCurrentUserId());
		if (stbCount != null) {
			int pageSize = webConfig.getPageSize();
			int counts = stbCount.getCounts();
			pageNums = counts % pageSize == 0 ? counts / pageSize : counts
					/ pageSize + 1;
			if (page == 0) {
				page = 1;
			}
			if (pageNums == 0) {
				page = 0;
				return "list";
			}

			stbs = stbService.searchStbs(stb, (page - 1) * pageSize + 1, page
					* pageSize, getCurrentUserId());
		}

		return "list";
	}

	/**
	 * Statistics STBs by Status
	 * 
	 * @return
	 * @throws Exception
	 */
	public String statistics() throws Exception {
		List<GroupType> typeList = groupTypeService
				.getTypeList(getCurrentUserId());
		List<String> status = stbService.getStbStatus();
		if (status != null) {
			mapStatistics = new HashMap<String, HashMap<String, Integer>>();
			Iterator<GroupType> it = typeList.iterator();
			while (it.hasNext()) {
				GroupType groupType = it.next();
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				Stb stb = new Stb();
				stb.setTypeId(groupType.getTypeId());
				stb.setGroupId(0L);
				for (int i = 0; i < status.size(); i++) {
					stb.setStbStatus(status.get(i).trim());
					map.put(status.get(i).trim(), stbService.countStbs(stb,
							getCurrentUserId()).getCounts());
				}
				mapStatistics.put(groupType.getTypeName(), map);
				log.debug("group type {}", groupType.getTypeName());
			}
		}

		return "statistics";
	}

	/**
	 * Shutdown the STB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String shutdown() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				if ("success".equalsIgnoreCase(localService.shutDown(stbMac))) {
					rawResponse.getWriter().print(
							this.getText("command.send.success"));
				} else {
					rawResponse.getWriter().print(
							this.getText("command.send.failure"));
				}
			} else {
				rawResponse.getWriter().print(this.getText("NoResponse"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Restart the STB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String restart() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				StringBuffer strBuff = new StringBuffer();
				boolean flag;
				String[] macArr = macStr.split("l");
				log.debug("Mac Array: {}", macArr);
				for (int i = 0; i < macArr.length; i++) {
					if ("success".equalsIgnoreCase(localService
							.restart(macArr[i]))) {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.success") + ". ");
						flag = true;
					} else {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.failure") + ". ");
						flag = false;
					}
					operationLog(macArr[i], "restart", flag);
				}
				rawResponse.getWriter().print(strBuff);
			} else {
				rawResponse.getWriter().print(this.getText("no.response"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Control the STB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String playerOperation() {
		try {
			rawResponse.setContentType("text/html;charset=utf-8");
			if (localService != null) {
				StringBuffer strBuff = new StringBuffer();
				String[] macArr = macStr.split("l");
				String result;
				boolean flag;
				log.debug("Mac Array: {}", macArr);
				for (int i = 0; i < macArr.length; i++) {
					result = localService.playerOperation(macArr[i],
							playerOperation.toLowerCase());
					log.debug("result: {}", result);
					if ("success".equalsIgnoreCase(result)) {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.success") + ". ");
						flag = true;
					} else {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.failure") + ". ");
						flag = false;
					}
					operationLog(macArr[i], playerOperation, flag);
				}
				rawResponse.getWriter().print(strBuff);
			} else {
				rawResponse.getWriter().print(this.getText("no.response"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String uploadLog() {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				StringBuffer strBuff = new StringBuffer();
				boolean flag;
				String[] macArr = macStr.split("l");
				log.debug("Mac Array: {}", macArr);
				for (int i = 0; i < macArr.length; i++) {
					if ("success".equalsIgnoreCase(localService
							.uploadLog(macArr[i]))) {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.success") + ". ");
						flag = true;
					} else {
						strBuff.append(macArr[i] + " "
								+ this.getText("command.send.failure") + ". ");
						flag = false;
					}
					operationLog(macArr[i], "uplaodLog", flag);
				}
				rawResponse.getWriter().print(strBuff);
			} else {
				rawResponse.getWriter().print(this.getText("no.response"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get the policy version key
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String getPolicy() {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				String answer = localService.queryProperty(stbMac, "policy",
						"ask_active_policy");
				log.debug("get policy result: {}", answer);
				if (!"".equals(answer) && answer != null) {
					if ((answer.split(":")[0]).trim().toLowerCase().equals(
							"success")) {
						policyKey = (answer.split(":")[1]).trim().toLowerCase();
						rawResponse.getWriter().print(policyKey);
					} else {
						rawResponse.getWriter().print(
								this.getText("stb.offline"));
					}
				}
			} else {
				rawResponse.getWriter().print(
						this.getText("connect.control.server.failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	/**
	 * get policy version by key
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String getPolicyVer() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			Map<String, String> map = null;
			// sleep(3000);
			if (localService != null) {
				map = localService.getQueryResultByKey(policyKey);
				log.info("StbAction getPolicyVer map: {}", map);
				if (map != null) {
					log.debug("StbAction getPolicyVer: {}", map
							.get("policy_number"));
					rawResponse.getWriter().print(map.get("policy_number"));
				} else {
					rawResponse.getWriter().print(
							this.getText("stb.version.get.failed"));
				}
			} else {
				rawResponse.getWriter().print(
						this.getText("connect.control.server.failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendConfig() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				Stb stb = null;
				Conf conf = null;
				String msg = "";
				String[] macArr = macStr.split("l");
				log.debug("Mac Array: {}", macStr);
				for (int i = 0; i < macArr.length; i++) {
					if (!"".equals(macArr[i])) {
						stb = new Stb();
						stb.setStbMac(macArr[i]);
						stb.setConfId(confId);
						conf = new Conf();
						conf.setConfId(confId);
						stbService.updateConf(stb);
						Map<String, String> configMap = new HashMap<String, String>();
						configMap.put("updateTime", updateTime);
						List<Config> configs = null;
						Config config = new Config();
						config.setConfId(confId);
						configs = configService.getConfigs(config);
						if (configs != null) {
							Iterator<Config> it = configs.iterator();
							while (it.hasNext()) {
								config = it.next();
								configMap.put(config.getConfigKey(), config
										.getConfigValue());
							}
							try {
								localService.modifyStbConfig(macArr[i],
										configMap);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					// msg = msg + localService.modifyStbConfig(macArr[i],
					// configMap) + ". ";
				}
				rawResponse.getWriter().print(msg);
			} else {
				rawResponse.getWriter().print(this.getText("no.response"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteStbs() throws Exception {
		String[] macArr = macStr.split("l");
		Stb stb = new Stb();
		for (int i = 0; i < macArr.length; i++) {
			stb.setStbMac(macArr[i]);
			stbService.delete(stb);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String getPic() throws Exception {
		setLayout(JSON);
		try {
			File path = null;
			path = new File(webConfig.getPicUrl() + stbMac);
			//path = new File("E:/Documents/pictures/phone");
			log.debug("screen snap path: {}", path);
			File[] files = path.listFiles();
			List<String> fileNames = new ArrayList<String>();
			if(files != null) {
				Arrays.sort(files);
				for (File file : files) {
					if (!file.isFile() || !file.exists()) {
						continue;
					}
					if(file.getName().contains("jpg")) {
						fileNames.add(file.getName());
					}
				}
				request.put("files", fileNames);
				log.debug("file num: {}", fileNames.size());
			} else {
				log.debug("There is no files.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
//		this.copyFile(this.getPicture(), ServletActionContext
//				.getServletContext().getRealPath(picUrl));
		return "filenames";
	}

	public String showPicture() {
		setLayout(AJAX);
		try {
			File file = null;
			String path = webConfig.getPicUrl() + stbMac + "/";
			//String path = "E:/Documents/pictures/phone/";
			file = new File(path + picture);
			log.info("file: {}", path + picture);
			if(file.exists()) {
				this.image4show = new FileInputStream(file);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "downloadImage";
	}
	/*private File getPicture() {
		try {
			File path = null;
			//path = new File(webConfig.getPicUrl() + stbMac);
			path = new File("E:/Documents/pictures/logo");
			log.info("screen snap path: {}", path);
			File[] files = path.listFiles();
			pic = "";
			File result = null;
			for (File file : files) {
				if (!file.isFile() || !file.exists())
					continue;
				if (pic.compareTo(file.getName()) < 0) {
					pic = file.getName();
					result = file;
				}
			}
			return result;
			//return path.getPath() + "/" + stbMac + "/" + pic;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	public String deletePics() {
		String[] picArr = picStr.split(",");
		log.info("Will be deleted pictures' name: {}", picStr);
		try {
			File file = null;
			String path = webConfig.getPicUrl() + stbMac + "/";
			//String path = "E:/Documents/pictures/phone/";
			for (int i = 0; i < picArr.length; i++) {
				file = new File(path + picArr[i]);
				log.info("Will be deleted picture's name: {}", picArr[i]);
				FileUtils.deleteQuietly(file);
				//file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * create by L.J. on 8/29/2011
	 * @return
	 */
	public String getFiles() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		try {
			if (localService != null) {
				String answer = localService.queryProperty(stbMac, "STB",
						"ask_file_list");
				log.debug("get files result: {}", answer);
				if (!"".equals(answer) && answer != null) {
					if ((answer.split(":")[0]).trim().toLowerCase()
							.equals("success")) {
						filesKey = (answer.split(":")[1]).trim()
								.toLowerCase();
						rawResponse.getWriter().print(filesKey);
					} else {
						rawResponse.getWriter().print(
										this.getText("stb.offline"));
					}
				}
			} else {
				rawResponse.getWriter().print(
						this.getText("connect.control.server.failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * create by L.J. on 8/31/2011
	 * @return
	 * @throws Exception
	 */
	public String getFilesByKey() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		log.debug("StbAction getFilesByKey key: {}", filesKey);
		try {
			Map<String, String> map = null;
			if (localService != null) {
				map = localService.getQueryResultByKey(filesKey);
				log.debug("StbAction getFilesByKey map: {}", map);
				if (map != null) {
					String files = map.get("file_list");
					log.debug("StbAction getFilesByKey: {}", files);
					String[] fileNames = files.split(",");
					// create JSON string
					StringBuffer jsonStr = new StringBuffer("");
					jsonStr.append("{");
					Videos video = null;
					String name = "";
					for (int i = 0; i < fileNames.length; i++) {
						if(!".".equals(fileNames[i]) && !"..".equals(fileNames[i])) {
							video = videosService.getVideoByName(fileNames[i]);
							if(video != null) {
								name = video.getName();
							} else {
								name = "Unknown" + i;
							}
							this.addProp(jsonStr, name, fileNames[i]);
							if (i < fileNames.length - 1) {
								jsonStr.append(",");
							}
						}
					}
					jsonStr.append("}");
					rawResponse.getWriter().print(jsonStr);
				} else {
					rawResponse.getWriter().print(
							this.getText("stb.file.list.get.failed"));
				}
			} else {
				rawResponse.getWriter().print(
						this.getText("connect.control.server.failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * delete the selected files
	 * @return
	 */
	public String deleteFiles() {
		String[] fileArr = fileStr.split(",");
		log.debug("Will be deleted files' name: {}", fileArr);
		try {
			if (localService != null) {
				for (int i = 0; i < fileArr.length; i++) {
					log.debug("Will be deleted file's name: {}", fileArr[i]);
					localService.deleteMedia(stbMac, fileArr[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private void operationLog(String mac, String command, boolean flag) {
		StbOperationLog stbOperaLog = new StbOperationLog();
		log.info("MAC: {}", mac);
		stbOperaLog.setMac(mac);
		// get user id
		stbOperaLog.setUserId(getCurrentUserId());
		log.info("command: {}", command);
		stbOperaLog.setCommand(command);
		Date runTime = new Date();
		stbOperaLog.setRunTime(runTime);
		if (flag == true) {
			stbOperaLog.setResult("Success");
		} else {
			stbOperaLog.setResult("Failure");
		}
		log.debug("operate result {}", stbOperaLog.getResult());
		stbOperaLogService.addLog(stbOperaLog);
	}

	/**
	 * get STB list
	 */
	private void getList() {
		int pageSize = webConfig.getPageSize();
		Stb stb = new Stb();
		stb.setStbMac(stbMac);
		stb.setStbStatus(stbStatus);
		// stb.setDistrictId(districtId);
		// stb.setCityId(cityId);
		// stb.setProvinceId(provinceId);
		stb.setActivePolicy(activePolicy);
		stb.setShopNo(shopNo);
		stb.setShopName(shopName);
		log.debug("Type {}, Group {}", typeId, groupId);
		if (typeId == null) {
			typeId = 0L;
		}
		if (groupId == null) {
			groupId = 0L;
		}
		stb.setTypeId(typeId);
		stb.setGroupId(groupId);
		int counts = stbService.countStbs(stb, getCurrentUserId()).getCounts();
		log.debug("user Id {}", getCurrentUserId());
		log.debug("stb list size: {}", counts);
		pageNums = counts % pageSize == 0 ? counts / pageSize : counts
				/ pageSize + 1;
		if (page == 0) {
			page = 1;
		}
		if (pageNums == 0) {
			page = 0;
		} else {
			int begin = (page - 1) * pageSize + 1;
			int end = page * pageSize;
			stbs = stbService.searchStbs(stb, begin, end, getCurrentUserId());
			if (log.isDebugEnabled()) {
				log.debug("result stb list: {}", stbs);
			}
		}
	}

	public String standAlone() {
		if (dateinput != null || dateinput2 != null) {
			List<Statistics> list = stbService.standAloneReport(mac, dateinput,
					dateinput2);
			// ActionContext.getContext().put("list",list);
			request.put("list", list);
		}
		return "standalone";
	}

	public String listOnlinesByType() {
		setLayout(JSON);
		if (groupId != null && groupId != 0l) {
			//stbs = getStbService().listOnlineByGroup(groupId);
			//stbs = getStbService().pageOnlineByGroup(typeId, groupId, page);
			stbs = getStbService().pageOnlineByTypeAndGroup(typeId, groupId, page);
		}
		return "json-list";
	}

	public String statusReport() {
		this.getList();
		return "status";
	}

	/**
	 * create by L.J. on 8/25/2011
	 * @return
	 * @throws Exception
	 */
	public String exportStatusReport() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		map.put("typeId", typeId);
		map.put("groupId", groupId);
		map.put("userId", getCurrentUserId());
		List<Stb> stbList = stbService.getStbsStatus(map);
		HSSFWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getStatusReportXls(stbList);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=StbStatusReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}
	
	/**
	 * create by L.J. on 8/25/2011
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getStatusReportXls(List<Stb> list) throws Exception {
		HSSFWorkbook wbook = null;
		try {
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("StbStatus");
			GroupType type = new GroupType();
			if (typeId != null && typeId != 0L) {
				type.setTypeId(typeId);
				type = groupTypeService.getGroupType(type);
			}
			Groups group = new Groups();
			if (groupId != null && groupId != 0L) {
				group.setGroupId(groupId);
				group = groupsService.getGroup(group);
			}
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("Top Level:");
			srow.createCell(1).setCellValue(type.getTypeName());
			srow.createCell(2).setCellValue("Secend Level:");
			srow.createCell(3).setCellValue(group.getGroupName());
			srow.createCell(4).setCellValue("MAC:");
			srow.createCell(5).setCellValue(mac);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("top.level"),
					this.getText("second.level"), 
					this.getText("Mac"), 
					this.getText("package.name"), 
					this.getText("cup.use"), 
					this.getText("mem.use"), 
					this.getText("disk.use") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<Stb> it = list.iterator();
			Stb entity = null;
			String temp = "";
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getTypeName());
				srow.createCell(1).setCellValue(entity.getGroupName());
				srow.createCell(2).setCellValue(entity.getStbMac());
				srow.createCell(3).setCellValue(entity.getPackageName());
				temp = entity.getCpu();
				if(temp != null && !temp.isEmpty() && Integer.parseInt(temp) > 0) {
					srow.createCell(4).setCellValue(temp + "%");
				} else {
					srow.createCell(4).setCellValue("");
				}
				temp = entity.getNmem();
				if(temp != null && !temp.isEmpty() && Integer.parseInt(temp) > 0) {
					srow.createCell(5).setCellValue(temp + "%");
				} else {
					srow.createCell(5).setCellValue("");
				}
				temp = entity.getDisk();
				if(temp != null && !temp.isEmpty() && Integer.parseInt(temp) > 0) {
					srow.createCell(6).setCellValue(temp + "%");
				} else {
					srow.createCell(6).setCellValue("");
				}
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}
	
	/**
	 * @param stbs
	 *            the stbs to set
	 */
	public void setStbs(List<Stb> stbs) {
		this.stbs = stbs;
	}

	public List<Stb> getStbs() {
		return stbs;
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
	 * @return the macStr
	 */
	public String getMacStr() {
		return macStr;
	}

	/**
	 * @param macStr
	 *            the macStr to set
	 */
	public void setMacStr(String macStr) {
		this.macStr = macStr;
	}

	/**
	 * @return the stbMac
	 */
	public String getStbMac() {
		return stbMac;
	}

	/**
	 * @param stbMac
	 *            the stbMac to set
	 */
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
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
	 * @param cityId
	 *            the cityId to set
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
	 * @param districtId
	 *            the districtId to set
	 */
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the addrDetail
	 */
	public String getAddrDetail() {
		return addrDetail;
	}

	/**
	 * @param addrDetail
	 *            the addrDetail to set
	 */
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	/**
	 * @return the stbStatus
	 */
	public String getStbStatus() {
		return stbStatus;
	}

	/**
	 * @param stbStatus
	 *            the stbStatus to set
	 */
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}

	/**
	 * @return the stbStatusEdit
	 */
	public String getStbStatusEdit() {
		return stbStatusEdit;
	}

	/**
	 * @param stbStatusEdit
	 *            the stbStatusEdit to set
	 */
	public void setStbStatusEdit(String stbStatusEdit) {
		this.stbStatusEdit = stbStatusEdit;
	}

	/**
	 * @return the pageNums
	 */
	public int getPageNums() {
		return pageNums;
	}

	/**
	 * @param pageNums
	 *            the pageNums to set
	 */
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

//	/**
//	 * @return the mapStatus
//	 */
//	public Map<String, String> getMapStatus() {
//		return mapStatus;
//	}
//
//	/**
//	 * @param mapStatus
//	 *            the mapStatus to set
//	 */
//	public void setMapStatus(Map<String, String> mapStatus) {
//		this.mapStatus = mapStatus;
//	}

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
	 * @return the activePolicy
	 */
	public String getActivePolicy() {
		return activePolicy;
	}

	/**
	 * @param activePolicy
	 *            the activePolicy to set
	 */
	public void setActivePolicy(String activePolicy) {
		this.activePolicy = activePolicy;
	}

	/**
	 * @return the shopNo
	 */
	public String getShopNo() {
		return shopNo;
	}

	/**
	 * @param shopNo
	 *            the shopNo to set
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
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * @param contacts
	 *            the contacts to set
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
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the mapStatistics
	 */
	public Map<String, HashMap<String, Integer>> getMapStatistics() {
		return mapStatistics;
	}

	/**
	 * @param mapStatistics
	 *            the mapStatistics to set
	 */
	public void setMapStatistics(
			Map<String, HashMap<String, Integer>> mapStatistics) {
		this.mapStatistics = mapStatistics;
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

	public WatchServerRef getWatchServerRef() {
		return watchServerRef;
	}

	public void setWatchServerRef(WatchServerRef watchServerRef) {
		this.watchServerRef = watchServerRef;
	}

	/**
	 * @return the playerOperation
	 */
	public String getPlayerOperation() {
		return playerOperation;
	}

	/**
	 * @param playerOperation
	 *            the playerOperation to set
	 */
	public void setPlayerOperation(String playerOperation) {
		this.playerOperation = playerOperation;
	}

	/**
	 * @param policyKey
	 *            the policyKey to set
	 */
	public void setPolicyKey(String policyKey) {
		this.policyKey = policyKey;
	}

	/**
	 * @return the policyKey
	 */
	public String getPolicyKey() {
		return policyKey;
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
	 * @return the stbOperaLogService
	 */
	public StbOperationLogService getStbOperaLogService() {
		return stbOperaLogService;
	}

	/**
	 * @param stbOperaLogService
	 *            the stbOperaLogService to set
	 */
	public void setStbOperaLogService(StbOperationLogService stbOperaLogService) {
		this.stbOperaLogService = stbOperaLogService;
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
	 * @return the groupsService
	 */
	public GroupsService getGroupsService() {
		return groupsService;
	}

	/**
	 * @param groupsService the groupsService to set
	 */
	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	public Date getDateinput() {
		return dateinput;
	}

	public void setDateinput(Date dateinput) {
		this.dateinput = dateinput;
	}

	public Date getDateinput2() {
		return dateinput2;
	}

	public void setDateinput2(Date dateinput2) {
		this.dateinput2 = dateinput2;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the image4show
	 */
	public InputStream getImage4show() {
		return image4show;
	}

	/**
	 * @param image4show the image4show to set
	 */
	public void setImage4show(InputStream image4show) {
		this.image4show = image4show;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the contentLength
	 */
	public String getContentLength() {
		return contentLength;
	}

	/**
	 * @param contentLength
	 *            the contentLength to set
	 */
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * @return the contentDisposition
	 */
	public String getContentDisposition() {
		return contentDisposition;
	}

	/**
	 * @param contentDisposition
	 *            the contentDisposition to set
	 */
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	/**
	 * @return the picStr
	 */
	public String getPicStr() {
		return picStr;
	}

	/**
	 * @param picStr the picStr to set
	 */
	public void setPicStr(String picStr) {
		this.picStr = picStr;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * @return the stbListJson
	 */
	public String getStbListJson() {
		return stbListJson;
	}

	/**
	 * @param stbListJson the stbListJson to set
	 */
	public void setStbListJson(String stbListJson) {
		this.stbListJson = stbListJson;
	}

	
	/**
	 * @param filesKey the filesKey to set
	 */
	public void setFilesKey(String filesKey) {
		this.filesKey = filesKey;
	}

	/**
	 * @return the filesKey
	 */
	public String getFilesKey() {
		return filesKey;
	}

	/**
	 * @return the fileStr
	 */
	public String getFileStr() {
		return fileStr;
	}

	/**
	 * @param fileStr the fileStr to set
	 */
	public void setFileStr(String fileStr) {
		this.fileStr = fileStr;
	}

	/**
	 * @return the videosService
	 */
	public VideosService getVideosService() {
		return videosService;
	}

	/**
	 * @param videosService the videosService to set
	 */
	public void setVideosService(VideosService videosService) {
		this.videosService = videosService;
	}

}
