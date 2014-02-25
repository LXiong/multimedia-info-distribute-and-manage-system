package com.yunling.policyman.db.mapper;

import java.util.List;

import com.yunling.policyman.db.mapper.base.BaseStbGroupLevelTwoMapper;
import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;

public interface StbGroupLevelTwoMapper
	extends BaseStbGroupLevelTwoMapper
{

	List<StbGroupLevelTwo> listByGroupType(StbGroup group);

}