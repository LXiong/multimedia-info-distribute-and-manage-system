package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.service.UploadingVideoService;
import com.yunling.policyman.db.service.impl.base.BaseUploadingVideoServiceImpl;

public class UploadingVideoServiceImpl extends BaseUploadingVideoServiceImpl
		implements UploadingVideoService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.policyman.db.service.UploadingVideoService#listByUploaded()
	 */
	@Override
	public List<UploadingVideo> listByUploaded(Boolean uploaded) {
		String temp = uploaded == true ? "1" : "0";
		return getMapper().listByUploaded(temp);
	}

	/* (non-Javadoc)
	 * @see com.yunling.policyman.db.service.UploadingVideoService#countByUploaded(java.lang.Boolean)
	 */
	@Override
	public int countByUploaded(Boolean uploaded) {
		String temp = uploaded == true ? "1" : "0";
		return getMapper().countByUploaded(temp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.policyman.db.service.UploadingVideoService#listByUploadedPage
	 * (java.util.Map)
	 */
	@Override
	public List<UploadingVideo> listByUploadedPage(Boolean uploaded, int begin,
			int end) {
		String temp = uploaded == true ? "1" : "0";
		return getMapper().listByUploadedPage(
				temp, begin, end);
	}

	@Override
	public UploadingVideo findByFileName(String name) {
		return getMapper().findByFileName(name);
	}

	@Override
	public List<UploadingVideo> listByCode(String code) {
		return getMapper().listByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.yunling.policyman.db.service.UploadingVideoService#findByOriginName(java.lang.String)
	 */
	@Override
	public UploadingVideo findByOriginName(String name) {
		return getMapper().findByOriginName(name);
	}

}