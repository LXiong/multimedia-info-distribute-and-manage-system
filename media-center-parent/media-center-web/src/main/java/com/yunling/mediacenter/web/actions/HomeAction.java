package com.yunling.mediacenter.web.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.StbWarningInfo;
import com.yunling.mediacenter.db.model.Users;
import com.yunling.mediacenter.db.model.WatchServerStatus;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.db.service.StbWarningInfoService;
import com.yunling.mediacenter.db.service.UserService;
import com.yunling.mediacenter.server.remote.AuthenticateQuery;
import com.yunling.mediacenter.web.FuncDeclare;
import com.yunling.mediacenter.web.Functions;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;


@FuncDeclare(Functions.Basic)
public class HomeAction extends AbstractUserAwareAction {
	private Logger log = LoggerFactory.getLogger(HomeAction.class);
	private UserService userService;
	private String lastLoginTime;
	private String userName;
	private StbService stbService;
	private StbWarningInfoService stbWarningInfoService;
	List<StbWarningInfo> infos = new ArrayList<StbWarningInfo>();
	
	private AuthenticateQuery<WatchServerStatus> authentiCation;
	private FastDateFormat df = FastDateFormat.getInstance(getText("web.date.pattern"));
	
	public String execute() {
		boolean status = false;
		long userId = getCurrentUserId();
		if (userId!=-1) {
			Users u = userService.getUserById(userId);
			if (u!=null) {
				if (u.getLastLoginTime()!=null) {
					this.lastLoginTime = df.format(u.getLastLoginTime());
				}
				this.userName = u.getUserName();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<WatchServerStatus> list = authentiCation.getAllWatchServerStatus();
			request.put("list",list);
		} catch (Exception e) {
			log.error("Failed to get server status", e);
			status = true;
		}
		Integer num = userService.getAuthByStatus("NORMAL");
		Integer num2 = userService.getAuthByStatus("BLOCKED");
		Integer num3 = userService.getStbByStatusIsPending("pending");
		Integer num4 = userService.getAuthByStatusIsFail("NORMAL");
		map.put("normal",num);
		map.put("blocked",num2);  
		map.put("waitauth",num3);
		map.put("fail",num4); 
		request.put("map",map);
		request.put("status",status);
		/*-----------------------*/
		StbWarningInfo info = new StbWarningInfo();
		info.setStatus("waiting_for_processing");
		info.setPage(1);
		infos = stbWarningInfoService.query(info);
		localize(infos);
		log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@infos size : " + infos.size());
		/*-----------------------*/
		return SUCCESS;
	}
	
	public void localize(List<StbWarningInfo> list){
		Iterator<StbWarningInfo> it = list.iterator();
		while(it.hasNext()){
			localize(it.next());
		}
	}
	
	public void localize(StbWarningInfo info){
		info.setWarningTypeCn(this.getText("warning.type."+info.getWarningType()));
		info.setStatusCn(this.getText("warning.status."+info.getStatus()));
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUserName() {
		return userName;
	}
	public AuthenticateQuery<WatchServerStatus> getAuthentiCation() {
		return authentiCation;
	}
	public void setAuthentiCation(
			AuthenticateQuery<WatchServerStatus> authentiCation) {
		this.authentiCation = authentiCation;
	}
	public StbService getStbService() {
		return stbService;
	}
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}
	public StbWarningInfoService getStbWarningInfoService() {
		return stbWarningInfoService;
	}
	public void setStbWarningInfoService(StbWarningInfoService stbWarningInfoService) {
		this.stbWarningInfoService = stbWarningInfoService;
	}
	public List<StbWarningInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<StbWarningInfo> infos) {
		this.infos = infos;
	}
}
