/**
 * 
 */
package com.yunling.mediacenter.web.actions;

import java.util.List;

import com.yunling.mediacenter.db.model.District;
import com.yunling.mediacenter.db.service.DistrictService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public class DistrictAction extends AbstractUserAwareAction {
	private List<District> districts;
	private DistrictService districtService;
	private String cityId;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String listDistrict() {
		setLayout(JSON);
		districts = districtService.findByCityId(cityId);
		return SUCCESS;
	}
	
	/**
	 * @param districtService the districtService to set
	 */
	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
	}

	/**
	 * @return the districts
	 */
	public List<District> getDistricts() {
		return districts;
	}

	/**
	 * @param districts the districts to set
	 */
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the districtService
	 */
	public DistrictService getDistrictService() {
		return districtService;
	}
}
