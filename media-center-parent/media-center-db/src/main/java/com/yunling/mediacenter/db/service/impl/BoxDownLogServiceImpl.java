/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import com.yunling.mediacenter.db.mapper.BoxDownLogMapper;
import com.yunling.mediacenter.db.model.BoxDownLog;
import com.yunling.mediacenter.db.service.BoxDownLogService;

/**
 * @author LingJun
 * @date 2010-12-21
 * 
 */
public class BoxDownLogServiceImpl extends BaseServiceImpl implements
		BoxDownLogService {

	@Override
	public void insertLog(final BoxDownLog log) {
		getMapper(BoxDownLogMapper.class).insertLog(log);
	}

}
