package com.yunling.mediacenter.db.service;

import com.yunling.mediacenter.db.model.StbSoftwareVersion;

public interface StbSoftwareVersionService {
	public void insertRecord(String mac, String type, String version);

	public void updateRecord(String mac, String type, String version);

	public StbSoftwareVersion selectRecord(String mac, String type);
}
