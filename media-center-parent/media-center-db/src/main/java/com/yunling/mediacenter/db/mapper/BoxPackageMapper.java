package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.BoxPackage;
import com.yunling.mediacenter.db.model.Stb;

public interface BoxPackageMapper {

	Integer selectBoxCount();

	List<BoxPackage> list(Map<String, Object> map);

	Object addBoxPackage(BoxPackage box);

	BoxPackage getBoxPackageById(long id);

	Object updateBoxPackage(BoxPackage box);

	Object updateStbPackageId(@Param("id") long id, @Param("stbmac") String stbMac);

	Object updateGroupsPackageId(@Param("id") long id, @Param("groupid") String groupid);

	Object updateStbPackageIByGroupId(@Param("id") long id, @Param("groupid") String groupid);

	List<Stb> getStbByGroupId(String groupid);

	Object delBoxPackageById(long id);

	Object updatePackageIdForGroups(long id);

	Object updatePackageIdForStb(long id);

}
							