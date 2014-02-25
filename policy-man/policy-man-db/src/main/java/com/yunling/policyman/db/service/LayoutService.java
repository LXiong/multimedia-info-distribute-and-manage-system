package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.Layout;
import com.yunling.policyman.db.service.base.BaseLayoutService;

public interface LayoutService
	extends BaseLayoutService
{
	Layout getWithArea(long layoutId);

	void updateWithArea(Layout newLayout);

	void insertWithArea(Layout newLayout);

	void deleteWithArea(long layoutId);

	List<Layout> listCountAreaPaged(int begin, int end);
}