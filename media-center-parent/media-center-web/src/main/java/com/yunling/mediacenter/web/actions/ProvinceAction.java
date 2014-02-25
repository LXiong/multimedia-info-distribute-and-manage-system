/**
 * 
 */
package com.yunling.mediacenter.web.actions;

import java.util.List;

import com.yunling.mediacenter.db.model.Province;
import com.yunling.mediacenter.db.service.ProvinceService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public class ProvinceAction extends AbstractUserAwareAction {

	private List<Province> provinces;
	private ProvinceService provService;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String listProvince() {
		setLayout(JSON);
		provinces = provService.listProvince();
		return SUCCESS;
	}

	/**
	 * @return the provinces
	 */
	public List<Province> getProvinces() {
		return provinces;
	}

	/**
	 * @param provinces the provinces to set
	 */
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

	/**
	 * @return the provService
	 */
	public ProvinceService getProvService() {
		return provService;
	}

	/**
	 * @param provService the provService to set
	 */
	public void setProvService(ProvinceService provService) {
		this.provService = provService;
	}

}
