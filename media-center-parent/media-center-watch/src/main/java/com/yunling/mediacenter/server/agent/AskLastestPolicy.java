package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Policy;
import com.yunling.mediacenter.db.model.PublishRecord;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.PublishRecordService;
import com.yunling.mediacenter.utils.StbMessage;

public class AskLastestPolicy extends RequestBaseAction {
	Map<String, String> city2Folder;
	PublishRecordService publishRecordService;
	GroupTypeService groupTypeService;
	// new , publish policy to different grouptype.
	boolean supportGroupType;
	// old, policy converter put policy in different dirs.
	boolean supportArea;

	public String execute(Channel ch, Map<String, String> params) {
		String mac = getKeyByValue(getMacChannels(), ch);
		Map<String, String> res = new HashMap<String, String>();
		if (null == mac || "".equals(mac)) {
			getLog().info("can not find the mac address");
			res.put("status_code", "0001");
			res.put("message", "can not find the mac address.");
		} else {
			Stb stb = getStbService().findByMac(mac);
			Long stbTypeId = stb.getTypeId();
			if (isSupportGroupType()) {
				GroupType type = new GroupType();
				type.setTypeId(stbTypeId);
				List<PublishRecord> records = publishRecordService
						.getLatestOfGroupType(type);
				if (records.size() == 0) {
					res.put("status_code", "0400");
					res.put("message", "file_not_found");
					getLog()
							.debug(
									"-------------no policy file can be download------------");
				} else {
					PublishRecord record = records.get(0);
					res.put("status_code", "0000");
					res.putAll(record.getAsParams());
					getLog().debug(res.toString());
					getLog()
							.debug(
									"----------send latest policy file to stb----------");
				}
			} else {
				getLog().debug("-------group 1 id :{} , city folder: {}",
						stbTypeId, city2Folder.get(stbTypeId).toString());
				String cityFolder = "%/"
						+ city2Folder.get(String.valueOf(stbTypeId)) + "/%";

				// String cityFolder = "%/" + "shanghai" + "/%";
				List<Policy> policies;
				if (isSupportArea()) {
					policies = getPolicyService().cityLatest(cityFolder, 2);
				} else {
					policies = getPolicyService().listLatest(2);
				}
				if (policies.size() == 0) {
					res.put("status_code", "0400");
					res.put("message", "file_not_found");
					getLog()
							.debug(
									"----------no policy file can be download----------");
				} else {
					Policy policy = policies.get(0);
					res.put("status_code", "0000");
					res.putAll(policy.getAsMap());
					getLog().debug(res.toString());
					getLog()
							.debug(
									"----------send latest policy file to stb----------");
				}
			}

		}
		ch.write(new StbMessage(res, "download", "policy").message());
		return "hello";
	}

	public Map<String, String> getCity2Folder() {
		return city2Folder;
	}

	public void setCity2Folder(Map<String, String> city2Folder) {
		this.city2Folder = city2Folder;
	}

	public boolean isSupportArea() {
		return supportArea;
	}

	public void setSupportArea(boolean supportArea) {
		this.supportArea = supportArea;
	}

	public PublishRecordService getPublishRecordService() {
		return publishRecordService;
	}

	public void setPublishRecordService(
			PublishRecordService publishRecordService) {
		this.publishRecordService = publishRecordService;
	}

	public GroupTypeService getGroupTypeService() {
		return groupTypeService;
	}

	public void setGroupTypeService(GroupTypeService groupTypeService) {
		this.groupTypeService = groupTypeService;
	}

	public boolean isSupportGroupType() {
		return supportGroupType;
	}

	public void setSupportGroupType(boolean supportGroupType) {
		this.supportGroupType = supportGroupType;
	}
}
