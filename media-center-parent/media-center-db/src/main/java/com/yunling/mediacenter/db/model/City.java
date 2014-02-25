/**
 * 
 */
package com.yunling.mediacenter.db.model;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public class City {
	private String cityId;
	private String cityName;
	private String provinceId;
	private String queryWeatherId;
	private String cityNamePinyin;

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getQueryWeatherId() {
		return queryWeatherId;
	}

	public void setQueryWeatherId(String queryWeatherId) {
		this.queryWeatherId = queryWeatherId;
	}

	public String getCityNamePinyin() {
		return cityNamePinyin;
	}

	public void setCityNamePinyin(String cityNamePinyin) {
		this.cityNamePinyin = cityNamePinyin;
	}

}
