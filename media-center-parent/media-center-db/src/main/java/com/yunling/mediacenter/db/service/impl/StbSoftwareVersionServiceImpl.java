package com.yunling.mediacenter.db.service.impl;

import com.yunling.mediacenter.db.mapper.StbSoftwareVersionMapper;
import com.yunling.mediacenter.db.model.StbSoftwareVersion;
import com.yunling.mediacenter.db.service.StbSoftwareVersionService;

public class StbSoftwareVersionServiceImpl extends BaseServiceImpl implements
		StbSoftwareVersionService {

	private StbSoftwareVersionMapper getMapper() {
		return getMapper(StbSoftwareVersionMapper.class);
	}
	
	@Override
	public void insertRecord(final String mac, final String type, final String version) {
		getMapper().insertRecord(mac, type, version);
	}

	@Override
	public StbSoftwareVersion selectRecord(final String mac, final String type) {
		return getMapper().selectRecord(mac, type);
	}

	@Override
	public void updateRecord(final String mac, final String type, final String version) {
		getMapper().updateRecord(mac, type, version);
	}

}
