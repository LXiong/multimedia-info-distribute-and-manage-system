package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;

public class ReportPerformanceParams extends RequestBaseAction {
	String[] keys = new String[]{"nmem", "disk", "cpu"};
	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		Stb stb = this.getStbService().findByMac(mac);
		if(stb != null){
			if("online".equalsIgnoreCase(stb.getStbStatus())){
				for(String index : keys){
					String value = params.get(index);
					getLog().info("stb performance " + index + " is : " + value);
					
					if(value.length() > 5){
						value = value.substring(0, 5);
					}
					stb.setPerformance(index, value);
					if(value == null || value.equals("")){
						stb.setPerformance(index, null);
					}
				}
				this.getStbService().updatePerformance(stb);
				getLog().info("update stb performance success");
			}
		}
		return "success";
	}

}
