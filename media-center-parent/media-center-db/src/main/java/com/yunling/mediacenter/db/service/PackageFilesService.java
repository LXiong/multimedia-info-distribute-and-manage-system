package com.yunling.mediacenter.db.service;

import java.util.List;
import com.yunling.mediacenter.db.model.PackageFiles;

public interface PackageFilesService {

	void add(PackageFiles pp);

	void deletePackFiles(long id, long fileid);
	
	int countByFileId(Long fileId);

	void delBoxPackageFilesByPackageId(long id);
	
	List<PackageFiles> getByModuleAndVersion(String module, String version);

}
