package com.yunling.mediacenter.server.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.AuthenticateRecordService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.utils.MacState;
import com.yunling.mediacenter.utils.Statistics;

public class CommonAuthenticater implements Authenticater {
	private Logger log = LoggerFactory.getLogger(CommonAuthenticater.class);
	private StbService stbService;
	private AuthenticateRecordService authenticateRecordService;
	private Statistics statistics;
	private String encryptKey;

	@Override
	public String process(Map<String, String> params) {
		if (isValid(params.keySet())) {
			Map<String, String> response = new HashMap<String, String>();
			response.put("test_action", "authenticate");

			response.put("server_ip", "---");
			response.put("server_port", "---");
			StringBuffer strBuf = new StringBuffer();
			String mac = joinStrings(params.get("mac").split(":"));
			String customerName = params.get("customer");
			String md5Code = params.get("md5");
			boolean is3G = false;
			if (params.get("vpn_flag") != null
					&& "true".equalsIgnoreCase(params.get("vpn_flag"))) {
				is3G = true;
			}
			String[] ipPort = statistics.getUsableIpAndPort(is3G).split(":");
			String token = DigestUtils.md5Hex(
					RandomStringUtils.random(15,
							"abcdefghijklmnopqrstuvwxyz0123456789") + mac)
					.substring(5, 23);

			MacState macState = getMacState(params.get("mac"), customerName,
					md5Code);
			response.put("status_code", macState.status());
			response.put("message", macState.message());
			log.info("authenticate_status:" + macState.status() + "\r\n"
					+ "authenticate_message:" + macState.message());
			log.info("macstate:" + macState.toString());
			switch (macState) {
			case NEW:
				log.info("--------NEW========");
				statistics.increaseAuthenticateAudit(params.get("remote_ip"));
				stbService.insertStb(params.get("remote_ip"), mac,
						customerName, "pending");
				break;
			case NORMAL:
				statistics.increaseAuthenticateSuccess(params.get("remote_ip"));
				stbService.updateToken(mac, token);
				response.put("token", token);
				response.put("server_ip", ipPort[0]);
				response.put("server_port", ipPort[1]);
				log.info("--------NORMAL end =======");
				break;
			case NOTINSTALLED:
				log.info("--------NOTINSTALLED========");
				stbService.updateStatus(mac, "installed_pending");
			default:
				statistics.increaseAuthenticateFailure(params.get("remote_ip"));
				break;
			}
			strBuf.append("response=authenticate\r\n");
			Iterator<Entry<String, String>> it = response.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				strBuf.append(entry.getKey() + "=" + entry.getValue() + "\r\n");
			}
			strBuf.append("\r\n");
			log.info("stbIp:" + params.get("remote_ip"));
			log.info("customerName:" + customerName);
			log.info("stbMac:" + mac);
			log.info("macState:" + macState.toString());
			log.info("serverIp:" + response.get("server_ip"));
			log.info("serverPort:" + response.get("server_port"));
			authenticateRecordService.insertRecord(params.get("remote_ip"),
					customerName, mac, macState.toString(),
					response.get("server_ip"), response.get("server_port"));
			log.info("content sended to stb:" + strBuf.toString());
			stbService.lastAccess(mac);
			return strBuf.toString();
		}
		return "format=error\r\n\r\n";
	}

	private boolean isValid(Set<String> paramsKeys) {
		String[] keys = { "mac", "customer", "md5" };
		for (String key : keys) {
			if (!paramsKeys.contains(key)) {
				return false;
			}
		}
		return true;
	}

	private MacState getMacState(String mac, String customerName, String md5Code) {
		String expecteMd5 = DigestUtils.md5Hex(mac + " " + customerName + " "
				+ encryptKey);
		log.info("original encode string is :" + mac.trim() + " "
				+ customerName.trim() + " " + encryptKey.trim());
		log.info("expecteMd5:" + expecteMd5);
		
		if(md5Code.equals(expecteMd5)){
			Stb stb = stbService.findByMac(joinStrings(mac.split(":")));
			if(stb == null){
				return MacState.NEW;
			}else{
				List<String> statuses = Arrays.asList(new String[]{"pending", "notinstalled", "installed_pending"});
				if(statuses.contains(stb.getStbStatus())){
					return MacState.valueOf(stb.getStbStatus().toUpperCase());
				}
				if("nointernet".equalsIgnoreCase(stb.getStbStatus())){
					return MacState.NORMAL;
				}
				if(stb.isDisabled()){
					return MacState.BLOCKED;
				}
				return MacState.NORMAL;
			}
		}else{
			return MacState.MD5_WRONG;
		}
	}

	private String joinStrings(String[] strs) {
		StringBuffer str = new StringBuffer();
		for (String s : strs) {
			str.append(s);
		}
		return str.toString();
	}

	public StbService getStbService() {
		return stbService;
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public void setAuthenticateRecordService(
			AuthenticateRecordService authenticateRecordService) {
		this.authenticateRecordService = authenticateRecordService;
	}

}
