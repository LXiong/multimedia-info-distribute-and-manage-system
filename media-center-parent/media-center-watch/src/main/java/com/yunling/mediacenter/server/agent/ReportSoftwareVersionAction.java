package com.yunling.mediacenter.server.agent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.PackageFiles;
import com.yunling.mediacenter.db.model.StbSoftwareVersion;

public class ReportSoftwareVersionAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = getKeyByValue(getMacChannels(), ch);
		params.remove("stb_ip");
		params.remove("stb_port");
		String module = null;
		String version = null;
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		//Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			StringBuffer responseStr = new StringBuffer();
			module = pair.getKey();
			if (StringUtils.isEmpty(module)) {
				continue;
			}
			version = pair.getValue();
			responseStr.append("response=soft_version\r\n");
			responseStr.append("key=" + module + "\r\n");
			responseStr.append("value=" + version + "\r\n");
			StbSoftwareVersion ssv = getStbSoftwareVersionService()
					.selectRecord(mac, module);
			if (ssv == null) {
				this.getStbSoftwareVersionService().insertRecord(mac, module,
						version);
				responseStr.append("type=add_record\r\n\r\n");
			} else {
				this.getStbSoftwareVersionService().updateRecord(mac, module,
						version);
				responseStr.append("type=update_record\r\n\r\n");
			}

			ch.write(responseStr.toString());
			getLog().info(responseStr.toString());
		}
		
		/*--------------------*/
		if (module != null && version != null) {
			List<PackageFiles> packageFiles = getPackageFilesService()
					.getByModuleAndVersion(module, version);
			if (packageFiles.size() > 0) {
				PackageFiles file = packageFiles.get(0);
				getStbService().updatePackageByMac(file.getPackageId(), mac);
			}
		}
		/*--------------------*/
		return null;
	}
}
