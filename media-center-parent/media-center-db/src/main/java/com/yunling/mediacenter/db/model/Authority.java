package com.yunling.mediacenter.db.model;

import java.util.Locale;

public class Authority {

	private Long id;
	private String name;
	private String localeZhCn;
	private boolean granted;
	
	public String getLocalName() {
		return getLocalName(null);
	}
	public String getLocalName(Locale l) {
		if (Locale.CHINESE.equals(l) || Locale.CHINA.equals(l)) {
			return localeZhCn;
		}
		
		return localeZhCn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocaleZhCn() {
		return localeZhCn;
	}

	public void setLocaleZhCn(String localeZhCn) {
		this.localeZhCn = localeZhCn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

}
