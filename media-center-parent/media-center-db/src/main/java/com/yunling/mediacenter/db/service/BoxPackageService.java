package com.yunling.mediacenter.db.service;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.BoxPackage;
import com.yunling.mediacenter.db.model.Stb;

public interface BoxPackageService {

	Integer selectBoxCount();

	Map<String, Object> list(Integer page, Integer sum);

	void addBoxPackage(BoxPackage box);

	BoxPackage getBoxPackageById(long id);

	void updateBoxPackage(BoxPackage box);

	void updateStbPackageId(long id, String stbMacs);

	void updateGroupsPackageId(long id,String groupid);

	void updateStbPackageIByGroupId(long id, String groupid);

	List<Stb> getStbByGroupId(String groupid);

	void delBoxPackageById(long id);

	void updatePackageIdForGroups(long id);

	void updatePackageIdForStb(long id);

}
