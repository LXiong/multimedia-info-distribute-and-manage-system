package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.PackagesMapper;
import com.yunling.mediacenter.db.model.Packages;
import com.yunling.mediacenter.db.service.PackagesService;

/**
 * @author LingJun
 * @date 2011-9-1
 * 
 */
public class PackagesServiceImpl extends BaseServiceImpl implements
		PackagesService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yunling.mediacenter.db.service.PackagesService#packageList()
	 */
	@Override
	public List<Packages> packageList() {
		return getMapper(PackagesMapper.class).packageList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.PackagesService#delByVersion(java.
	 * lang.String)
	 */
	@Override
	public void delByVersion(String version) {
		getMapper(PackagesMapper.class).delByVersion(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.PackagesService#createPkg(com.yunling
	 * .mediacenter.db.model.Packages)
	 */
	@Override
	public void createPkg(Packages packages) {
		getMapper(PackagesMapper.class).createPkg(packages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.PackagesService#updatePkg(com.yunling
	 * .mediacenter.db.model.Packages)
	 */
	@Override
	public void updatePkg(Packages packages) {
		getMapper(PackagesMapper.class).updatePkg(packages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.PackagesService#getPkgByVersion(java
	 * .lang.String)
	 */
	@Override
	public Packages getPkgByVersion(String version) {
		return getMapper(PackagesMapper.class).getPkgByVersion(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yunling.mediacenter.db.service.PackagesService#getPackageById(java
	 * .lang.Long)
	 */
	@Override
	public Packages getPackageById(Long id) {
		return getMapper(PackagesMapper.class).getPackageById(id);
	}

}
