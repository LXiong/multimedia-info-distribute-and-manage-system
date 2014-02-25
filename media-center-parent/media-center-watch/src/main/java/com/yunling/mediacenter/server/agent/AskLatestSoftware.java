package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Packages;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.utils.StbMessage;

public class AskLatestSoftware extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			Stb stb = getStbService().findByMac(mac);
			if (stb != null) {
				// List<ModuleFile> moduleFiles =
				// this.getModuleFileService().selectModuleByBoxId(stb.getPackageVersion());
				String packageVersion = stb.getPackageVersion();
				if(packageVersion == null) {
					this.getLog().info("there is not a new package version.");
					return "failure";
				}
				Packages pkg = this.getPackageService().getPkgByVersion(
						packageVersion);
				Map<String, String> res = new HashMap<String, String>();
				// for pressure test
				// res.put("test_action", "latest_soft");
				// if(moduleFiles.size() > 0){
				// Iterator<ModuleFile> it = moduleFiles.iterator();
				// while(it.hasNext()){
				// ModuleFile file = it.next();
				res.clear();
				res.put("status_code", "0000");
				// res.put("update_type", file.getModule());
				res.put("file_path", pkg.getName());
				// res.put("md5", pkg.getVerflyCode());
				res.put("version", packageVersion);
				ch.write(new StbMessage(res, "update_soft", "updater")
						.message());
				// }
				return "success";
				/*
				 * }else{ res.put("status_code", "0001"); res.put("message",
				 * "no_software_need_to_update"); ch.write(new StbMessage(res,
				 * "update_soft", "updater").message()); return "failure"; }
				 */
			} else {
				this.getLog().info("stb not found");
				return "failure";
			}
		} else {
			this.getLog().info("mac address not found ");
			return "failure";
		}
	}
}
