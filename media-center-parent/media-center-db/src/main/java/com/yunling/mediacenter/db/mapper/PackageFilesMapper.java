package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.PackageFiles;

public interface PackageFilesMapper {

	Object addPackageFiles(PackageFiles pp);

	Object deletePackFiles(@Param("id") long id, @Param("fileid")long fileid);

	int countByFileId(Long fileId);

	Object delBoxPackageFilesByPackageId(long id);
	
	List<PackageFiles> getByModuleAndVersion(@Param("module")String type, @Param("version")String version);
}
