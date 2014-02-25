package com.yunling.mediacenter.web.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunling.mediacenter.db.model.StbWarningInfo;
import com.yunling.mediacenter.db.service.StbWarningInfoService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class WarningInfoAction extends AbstractUserAwareAction {

	StbWarningInfoService stbWarningInfoService;
	List<StbWarningInfo> infos = new ArrayList<StbWarningInfo>();
	StbWarningInfo info = new StbWarningInfo();
	int page;

	@Override
	public String execute() throws Exception {
		info.setStatus("waiting_for_processing");
		return SUCCESS;
	}
	
	
	public String data(){
		setLayout(JSON);
		infos = stbWarningInfoService.query(info);
		localize(infos);
		return "data";
	}

	public String byMac() {
		return "execute";
	}

	public String byType() {
		return "execute";
	}
	
	public String query(){
		return "execute";
	}
	
	public String delete(){
		log.info("#####################warning info id is :" + info.getId());
		getStbWarningInfoService().delete(info.getId());
		setLayout(JSON);
		return "data";
	}
	
	public String process(){
		StbWarningInfo infoo = getStbWarningInfoService().getById(info.getId());
		getStbWarningInfoService().process(infoo);
		infos.clear();
		infos.add(getStbWarningInfoService().getById(info.getId()));
		localize(infos);
		//getStbWarningInfoService().process(getStbWarningInfoService().getById(info.getId()));
		setLayout(JSON);
		return "data";
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

	public StbWarningInfoService getStbWarningInfoService() {
		return stbWarningInfoService;
	}

	public void setStbWarningInfoService(
			StbWarningInfoService stbWarningInfoService) {
		this.stbWarningInfoService = stbWarningInfoService;
	}

	public StbWarningInfo getInfo() {
		return info;
	}

	public void setInfo(StbWarningInfo info) {
		this.info = info;
	}
	public List<StbWarningInfo> getInfos() {
		return infos;
	}


	public void setPage(int page) {
		this.page = page;
	}

}
