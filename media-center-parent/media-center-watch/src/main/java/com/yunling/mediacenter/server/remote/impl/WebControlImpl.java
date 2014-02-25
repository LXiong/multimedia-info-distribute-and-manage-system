package com.yunling.mediacenter.server.remote.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.RandomStringUtils;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianServlet;
import com.yunling.mediacenter.db.model.PolicyFile;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.db.service.WebControlRecordService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.utils.StbMessage;
import com.yunling.mediacenter.utils.WatchServerConfiguration;

public class WebControlImpl extends HessianServlet implements WebControl,
		Serializable {
	private transient Logger log = LoggerFactory.getLogger(WebControlImpl.class);
	private Map<String, Channel> macChannels;
	private Map<String, Map<String, String>> queryResults;
	private transient WebControlRecordService webControlRecordService;
	private transient StbService stbService;
	private transient WatchServerConfiguration config;

	public WebControlImpl(Map<String, Channel> macChannels,
			Map<String, Map<String, String>> queryResults,
			WebControlRecordService webControlRecordService,
			StbService stbService, WatchServerConfiguration config) {
		this.macChannels = macChannels;
		this.queryResults = queryResults;
		this.webControlRecordService = webControlRecordService;
		this.stbService = stbService;
		this.config = config;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -1667947296930072261L;

	/**
	 *
	 */
	@Override
	public String downloadPolicy(String cityCode, String filePath, String md5, int fileSize,
			String downloadTimeStr, String fileVersion) {
		log.info("[start] downloadPolicy");
		log.debug("city code is : " + cityCode);
		Map<String, String> params = new PolicyFile(filePath, md5, fileSize,
				fileVersion, downloadTimeStr).getAsMapParams();
		List<Stb> onlines = stbService.listByStatus("online");
		for(Stb stb : onlines) {
			String stbTypeId = String.valueOf(stb.getTypeId());
		    log.debug("online stb mac : {}, group1 code: {}",  stb.getStbMac(), stbTypeId);
		    if(cityCode.equals(stbTypeId)) {
		    	log.debug("send command to stb to download latest policy");
				controlForOne(params, stb.getStbMac(), "download", "policy");
		    }
		}
		return "success";
	}
	
	/**
	 *
	 */
	@Override
	public String downloadSinglePolicy(String filePath, String md5, int fileSize,
			String downloadTimeStr, String fileVersion) {
		log.debug("[start] downloadPolicy");
		Map<String, String> params = new PolicyFile(filePath, md5, fileSize,
				fileVersion, downloadTimeStr).getAsMapParams();
		List<Stb> onlines = stbService.listByStatus("online");
		for(Stb stb : onlines) {
		    log.debug("online stb`s mac address is :{} ", stb.getStbMac());
		    log.debug("online stb`s city code is : {} ", stb.getGroupId());
			log.debug("send command to stb to download latest policy");
			controlForOne(params, stb.getStbMac(), "download", "policy");
		}
		return "success";
	}

	/**
	 * command: cpu state, vedioPlayer playing_name, disk remaining_space,
	 * memory state
	 */
	@Override
	public String queryProperty(String mac, String target, String action) {
		log.debug("[start] queryProperty");
		log.debug("query properyt with mac: " + mac);
		log.debug("online macs" + macChannels.toString());
		log.debug("macChannels contains the mac? "
				+ macChannels.containsKey(mac));
		Map<String, String> params = new HashMap<String, String>();
		String commandKey = RandomStringUtils.random(10,
				"abcdefghijklmnopqrstuvwxyz0123456789");
		params.put("command_key", commandKey);
		String sendResult = this.controlForOne(params, mac, action, target);
		log.debug("queryProperty result {}", sendResult);
		if ("success".equals(sendResult)) {
			return sendResult + ":" + commandKey;
		} else {
			return sendResult;
		}
	}

	@Override
	public String shutDown(String mac) {
		log.debug("[start] shutDown");
		return this.controlForOne(new HashMap<String, String>(), mac,
				"shutdown");
	}

	@Override
	public String restart(String mac) {
		log.debug("[start] restart");
		return this
				.controlForOne(new HashMap<String, String>(), mac, "restart");
	}

	@Override
	public String stbOperation(String mac, String action) {
		log.debug("[start] stbOperation");
		return this.controlForOne(new HashMap<String, String>(), mac, action);
	}

	@Override
	public String playerOperation(String mac, String action) {
		log.debug("[start] playerOperation");
		return this.controlForOne(new HashMap<String, String>(), mac, action);
	}

	@Override
	public String modifyStbConfig(String mac, Map<String, String> params) {
		log.debug("[start] modifyStbConfig");
		return controlForOne(params, mac, "modify");
	}

	@Override
	public String uploadLog(String mac) {
		log.debug("[start] upload log");
		return controlForOne(new HashMap<String, String>(), mac, "upload_log");
	}

	@Override
	public String updateSoftware(Map<String, String> params, String... macs) {
		log.debug("[start] updateSoftware");
		if (macs.length > 0) {
			log.debug("macs.length > 0, control for one or more");
			for (String mac : macs) {
				controlForOne(params, mac, "update_soft", "updater");
			}
			return "success";
		} else {
			log.debug("macs.length = 0, control for all");
			return controlForMore(params, "update_soft", "updater");
		}
	}

	@Override
	public Map<String, String> getQueryResultByKey(String key) {
		log.debug("[start] getQueryResultByKey");
		Map<String, String> re = queryResults.remove(key);
		clearResults(queryResults);
		return re;
	}

	private void clearResults(Map<String, Map<String, String>> map) {
		// remove the result which put in map more than 1 minute.
		log.debug("[start] clearResults");
		String currentTimeStr = String
				.valueOf(System.currentTimeMillis() - 60000);
		Iterator<Entry<String, Map<String,String>>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Map<String, String>> pair = it.next();
			if(pair.getValue().get("put_in_map_time").compareTo(currentTimeStr) > 0){
				map.remove(pair.getKey());
			}
		}
		log.debug("[end] clearResults");
	}

	public String controlForOne(Map<String, String> params, String mac,
			String... actionTarget) {
		log.debug("[start] controlForOne");
		Stb stb = stbService.findByMac(mac);
		if (stb == null) {
			log.debug("[end] controlForOne:stb_not_found");
			return "stb_not_found";
		}
		Channel ch = this.macChannels.get(mac);
		if (ch == null) {
			log.debug("[end] controlForOne:channel_not_found");
			return "failure";
		} else {
			String stbIp = ch.getRemoteAddress().toString().split(":")[0];
			webControlRecordService.insertRecord(stbIp, actionTarget[0], stb
					.getCustomerName(), mac, config.getLocalOutnetAddress(),
					String.valueOf(config.getPort()));
			log.info("============" + new StbMessage(params, actionTarget).message());
			ch.write(new StbMessage(params, actionTarget).message());
			log.debug("[end] controlForOne:success");
			return "success";
		}

	}

	public String controlForMore(Map<String, String> params,
			String... actionTarget) {
		log.debug("[start] controlForMore");
		String re = "AllSuccess";
		for (String mac : macChannels.keySet()) {
			String tempRe = this.controlForOne(params, mac, actionTarget);
			if (!"success".equalsIgnoreCase(tempRe)) {
				re = "PartSuccess";
			}
		}
		log.debug("[end] controlForMore : " + re);
		return re;
	}

	@Override
	public String sendInstantMessage(Map<String, String> params, List<String> macs) {
		for(String mac : macs){
			this.controlForOne(params, mac, "instant_msg");
		}
		return null;
	}

	@Override
	public String updatePolicyForGroupType(long groupTypeId, Map<String, String> params) {
		List<Stb> stbs = stbService.listOnlineByGroupType(groupTypeId);
		for(Stb stb : stbs){
			this.controlForOne(params, stb.getStbMac(), "download", "policy");
		}
		return "success";
	}

	@Override
	public void configStream(String ip, String groupList, String startTime, String endTime) {
		List<String> macList = stbService.listOnlineByGroupList(groupList);
		Map<String , String> params = new HashMap<String, String>();
		params.put("ip", ip);
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		for (String mac : macList) {
			this.controlForOne(params, mac, "play", "stream");
		}
	}

	@Override
	public void deleteMedia(String mac, String fileName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("media_name", fileName);
		controlForOne(params, mac, "delete_media", "STB");
	}

	@Override
	public String updatePolicyForGroup(long groupId, Map<String, String> params) {
		List<Stb> stbs = stbService.listOnlineByGroup(groupId);
		for(Stb stb : stbs){
			this.controlForOne(params, stb.getStbMac(), "download", "policy");
		}
		return "success";
	}

}
