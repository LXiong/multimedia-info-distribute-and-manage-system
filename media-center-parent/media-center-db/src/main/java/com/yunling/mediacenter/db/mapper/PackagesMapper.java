package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.Packages;

/**
 * @author LingJun
 * @date 2011-9-1
 * 
 */
public interface PackagesMapper {
	public List<Packages> packageList();

	public void delByVersion(String version);

	public void createPkg(Packages packages);

	public void updatePkg(Packages packages);

	public Packages getPkgByVersion(String version);

	/**
	 * 
	 * @author LingJun
	 * @date 2012-1-4
	 * 
	 * @param id
	 * @return
	 */
	public Packages getPackageById(Long id);
}
