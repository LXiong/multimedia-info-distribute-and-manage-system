package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.BoxPackageMapper;
import com.yunling.mediacenter.db.model.BoxPackage;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.BoxPackageService;

public class BoxPackageServiceImpl extends BaseServiceImpl implements
		BoxPackageService {
	private Integer size;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer selectBoxCount() {
		return getMapper().selectBoxCount();
	}
	
	public Map<String, Object> list(final Integer page, final Integer sum) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String, Object> map = new HashMap<String, Object>();
		Integer count = sum%size==0 ? sum/size :sum/size+1;
		Integer page2=page;
		if(page<1){
			 page2=1;
		}
		if(page>count){
			 page2=count;
		}
		
		if(page2==1){
			map.put("begin", page2);
			map.put("end", page2*size);
		}else{
			map.put("begin",((page2-1)*size)+1);
			map.put("end",(page2*size));
		}
		getMapper().list(map);
		List<BoxPackage> list  =  getMapper().list(map);
 		resultMap.put("list",list);
 		resultMap.put("page",page2);
 		resultMap.put("count",count);
 		resultMap.put("size",size);
 		return resultMap;
	}
	
	public void addBoxPackage(final BoxPackage box) {
		getMapper().addBoxPackage(box);
	}
	public BoxPackage getBoxPackageById(final long id) {
		return getMapper().getBoxPackageById(id);
	}
	public void updateBoxPackage(final BoxPackage box) {
		getMapper().updateBoxPackage(box);
	}

	@Override
	public void updateStbPackageId(final long id, final String stbmac) {
		getMapper().updateStbPackageId(id, stbmac);
	}

	@Override
	public void updateGroupsPackageId(final long id,final String groupid) {
		getMapper().updateGroupsPackageId(id, groupid);
	}

	@Override
	public void updateStbPackageIByGroupId(final long id, final String groupid) {
		getMapper().updateStbPackageIByGroupId(id, groupid);
	}
	@Override
	public List<Stb> getStbByGroupId(final String groupid) {
		return getMapper().getStbByGroupId(groupid);
	}

	@Override
	public void delBoxPackageById(final long id) {
		getMapper().delBoxPackageById(id);
	}

	@Override
	public void updatePackageIdForGroups(final long id) {
		getMapper().updatePackageIdForGroups(id);
	}

	@Override
	public void updatePackageIdForStb(final long id) {
		getMapper().updatePackageIdForStb(id);
	}
	
	private BoxPackageMapper getMapper() {
		return getMapper(BoxPackageMapper.class);
	}
}
