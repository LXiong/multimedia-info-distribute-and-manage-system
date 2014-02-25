package com.yunling.mediacenter.db.service.impl;

import com.yunling.mediacenter.db.mapper.VideoMapper;
import com.yunling.mediacenter.db.model.Video;
import com.yunling.mediacenter.db.service.VideoService;

public class VideoServiceImpl extends BaseServiceImpl implements VideoService {

	private VideoMapper getMapper() {
		return getMapper(VideoMapper.class);
	}
	
	public void addVideo(final Video vv) {
		getMapper().addVideo(vv);
	}

}
