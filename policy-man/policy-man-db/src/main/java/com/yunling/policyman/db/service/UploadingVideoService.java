package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.service.base.BaseUploadingVideoService;

public interface UploadingVideoService extends BaseUploadingVideoService {
	public List<UploadingVideo> listByUploaded(Boolean uploaded);

	public int countByUploaded(Boolean uploaded);

	public List<UploadingVideo> listByUploadedPage(Boolean uploaded, int begin,
			int end);

	public UploadingVideo findByFileName(String name);

	public List<UploadingVideo> listByCode(String code);

	public UploadingVideo findByOriginName(String name);
}