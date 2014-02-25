package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;

public class StbOnline extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		String mac = getKeyByValue(getMacChannels(), ch);
		/**
		 * when auth server crash but restart in one minute, the watch server
		 * will not know the auth server offline all stb, so , when stb report
		 * online, watch server should change the stb status to online.
		 */
		
		Stb stb = getStbService().findByMac(mac);
		if(stb != null){
			if("offline".equalsIgnoreCase(stb.getStbStatus())){
				getLog().info("online the stb when report online, but it offlined before");
				getStbService().macOnline(mac, params.get("stb_ip"));
			}
		}
		getLog().info("online with mac:" + mac);
		ch.write("response=online\r\nstatus_code=0000\r\nresult=ok\r\n\r\n");
		return "success";
	}

}
