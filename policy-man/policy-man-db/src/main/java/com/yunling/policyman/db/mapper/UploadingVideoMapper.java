package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseUploadingVideoMapper;
import com.yunling.policyman.db.model.UploadingVideo;

public interface UploadingVideoMapper extends BaseUploadingVideoMapper {
	public List<UploadingVideo> listByUploaded(
			@Param(value = "uploaded") String uploaded);
	
	public int countByUploaded(String uploaded);

	public List<UploadingVideo> listByUploadedPage(
			@Param(value = "uploaded") String uploaded,
			@Param(value = "begin") int begin, @Param(value = "end") int end);

	public UploadingVideo findByFileName(@Param("filename") String filename);

	public List<UploadingVideo> listByCode(@Param("code") String code);
	
	public UploadingVideo findByOriginName(@Param("originName") String name);
}
