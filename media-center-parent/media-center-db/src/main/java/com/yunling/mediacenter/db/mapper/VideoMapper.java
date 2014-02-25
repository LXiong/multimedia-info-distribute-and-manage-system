package com.yunling.mediacenter.db.mapper;

import com.yunling.mediacenter.db.model.Video;

public interface VideoMapper {
	Object addVideo(Video vv);

	public Long countByPolicyId(Video video);
}
