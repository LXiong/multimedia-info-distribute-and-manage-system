package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.model.StbWarningInfo;

public class StbLogin extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		String mac = "unknow";
		String status = "FAILURE";
		getLog().info("login with token : " + params.get("token"));
		Stb activeStb = getStbService().findActiveByToken(params.get("token"));
		getLog().info("stb == null? {}" , (activeStb == null));
		if (activeStb != null) {
			mac = activeStb.getStbMac();
			status = "SUCCESS";
			addToMap(mac, ch, params.get("stb_ip"), params.get("stb_port"));
			getStbService().macOnline(mac, params.get("stb_ip"));
			ch.write(getMessageFromMap(getMessageMapByStatus(status)));
			getLog().info("login success with mac : " + mac);
		} else {
			Stb falseStb = getStbService().findAllByToken(params.get("token"));
			if (falseStb != null) {
				mac = falseStb.getStbMac();
				getLog().info("stb mac:" + falseStb.getStbMac());
			}
			getLog().info("login failure with mac : " + mac);
			refuseConnect(ch, getMessageFromMap(getMessageMapByStatus(status)));
			addIllegalLoginWarning(mac);
		}
		getStbLoginRecordService().insertRecord("login", mac,
				params.get("stb_ip"), params.get("stb_port"),
				getConfig().getLocalOutnetAddress(),
				String.valueOf(this.getConfig().getPort()), status);
		if(!"unknow".equalsIgnoreCase(mac)){
			getStbService().lastAccess(mac);
		}
		return "hello world";
	}

	public void addToMap(String mac, Channel ch, String stbIp, String stbPort) {
		Channel oldCh = getMacChannels().get(mac);
		getMacChannels().put(mac, ch);
		if (oldCh != null) {
			getLog().info("---------close old channel with mac " + mac);
			oldCh.close();
		}
		getLog().info("----------login with mac " + mac);
		getLog().info("begin report there is a stb logon this watch server");
		getWatchReporter().stbLogin(getConfig().getLocalOutnetAddress(), mac,
				stbIp, stbPort);
		
		getLog().info("has reported!----------------------------------");
		getLog()
				.info(
						"Now, there are " + getMacChannels().size()
								+ "stb connection:");

	}

	private Map<String, String> getMessageMapByStatus(String status) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test_action", "login");
		map.put("response", "login");
		if ("SUCCESS".equalsIgnoreCase(status)) {
			map.put("status", "SUCCESS");
			map.put("status_code", "0000");
		} else if ("FAILURE".equalsIgnoreCase(status)) {
			map.put("status", "FAILURE");
			map.put("status_code", "0001");
		}
		return map;
	}
	
	private void addIllegalLoginWarning(String mac){
		StbWarningInfo info = new StbWarningInfo();
		mac = mac != null ? mac : "unknow";
		info.setStbMac(mac);
		info.setWarningType("illegal-login");
		info.setStatus("waiting_for_processing");
		info.setSeverity("3");
		info.setDetails("type#illegal\tmac#" + mac);
		getStbWarningInfoService().save(info);
	}

}
