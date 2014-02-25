package com.yunling.mediacenter.db.service.impl;

import java.util.List;
import com.yunling.mediacenter.db.mapper.PackageFilesMapper;
import com.yunling.mediacenter.db.model.PackageFiles;
import com.yunling.mediacenter.db.service.PackageFilesService;

public class PackageFilesServiceImpl extends BaseServiceImpl implements
		PackageFilesService {
	
	private PackageFilesMapper getMapper() {
		return getMapper(PackageFilesMapper.class);
	}

	@Override
	public void add(final PackageFiles pp) {
		getMapper().addPackageFiles(pp);
	}

	@Override
	public void deletePackFiles(final long id, final long fileid) {
		getMapper().deletePackFiles(id, fileid);
	}

	@Override
	public int countByFileId(final Long fileId) {
		return getMapper().countByFileId(fileId);
	}

	@Override
	public void delBoxPackageFilesByPackageId(final long id) {
		getMapper().delBoxPackageFilesByPackageId(id);
	}
	
	@Override
	public List<PackageFiles> getByModuleAndVersion(String module, String version) {
		return getMapper().getByModuleAndVersion(module, version);
	}
}
