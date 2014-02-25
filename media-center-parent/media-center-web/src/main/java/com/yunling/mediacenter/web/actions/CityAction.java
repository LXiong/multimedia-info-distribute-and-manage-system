/**
 * 
 */
package com.yunling.mediacenter.web.actions;

import java.util.List;

import com.yunling.mediacenter.db.model.City;
import com.yunling.mediacenter.db.service.CityService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public class CityAction extends AbstractUserAwareAction {

	private List<City> cities;
	private CityService cityService;
	private String provinceId;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String listCity() {
		setLayout(JSON);
		cities = cityService.findByProvinceId(provinceId);
		return SUCCESS;
	}

	/**
	 * @return the cityService
	 */
	public CityService getCityService() {
		return cityService;
	}

	/**
	 * @param cityService the cityService to set
	 */
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cities
	 */
	public List<City> getCities() {
		return cities;
	}
}
