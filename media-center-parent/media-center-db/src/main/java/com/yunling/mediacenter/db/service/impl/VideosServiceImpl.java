package com.yunling.mediacenter.db.service.impl;

import com.yunling.mediacenter.db.mapper.VideosMapper;
import com.yunling.mediacenter.db.model.Videos;
import com.yunling.mediacenter.db.service.VideosService;

/**
 * @author LingJun
 * @date 2011-9-1
 * 
 */
public class VideosServiceImpl extends BaseServiceImpl implements VideosService {

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.VideosService#getVideoByName(java.lang.String)
	 */
	@Override
	public Videos getVideoByName(String name) {
		return getMapper(VideosMapper.class).getVideoByName(name);
	}

}
