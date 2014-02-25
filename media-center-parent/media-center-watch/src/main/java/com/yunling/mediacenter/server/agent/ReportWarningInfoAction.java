package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

import com.yunling.mediacenter.db.model.StbWarningInfo;

public class ReportWarningInfoAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		if(mac != null){
			StbWarningInfo warningInfo = new StbWarningInfo(params);
			warningInfo.setStbMac(mac);
			getStbWarningInfoService().save(warningInfo);
			ch.write("response=alarm\r\nstatus=success\r\nstatus_code=0000");
			getLog().debug("#######################success");
			return "success";
		}else{
			//test
			ChannelFuture future = ch.write("response=alarm\r\nstatus=0001\r\nstatus=error\r\nmessage=mac_not_found");
			future.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture fu)
						throws Exception {
					fu.getChannel().close();
				}
				
			});
			getLog().debug("#######################error");
			return "error";
		}
	}

}
