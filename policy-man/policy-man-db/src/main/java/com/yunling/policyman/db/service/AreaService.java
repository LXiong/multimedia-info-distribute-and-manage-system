package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.Area;
import com.yunling.policyman.db.service.base.BaseAreaService;

public interface AreaService
	extends BaseAreaService
{

	void deleteByLayoutId(long layoutid);

	List<Area> getByLayoutId(long parseLong);

}