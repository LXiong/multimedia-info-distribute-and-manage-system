package com.yunling.policyman.db.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunling.policyman.db.mapper.AreaMapper;
import com.yunling.policyman.db.mapper.LayoutMapper;
import com.yunling.policyman.db.model.Area;
import com.yunling.policyman.db.model.Layout;
import com.yunling.policyman.db.service.LayoutService;
import com.yunling.policyman.db.service.impl.base.BaseLayoutServiceImpl;

public class LayoutServiceImpl
	extends BaseLayoutServiceImpl
	implements LayoutService
{

	@Override
	public Layout getWithArea(long layoutId) {
		return getMapper().getWithArea(layoutId);
	}

	@Override
	public void updateWithArea(Layout newLayout) {
		LayoutMapper layoutMapper = getMapper();
		Layout old = layoutMapper.getWithArea(newLayout.getId());
		if (old == null) {
			throw new RuntimeException("Old layout is not found");
		}
		old.merge(newLayout);
		layoutMapper.update(old);
		List<Area> newList = new ArrayList<Area>();
		List<Area> updateList = new ArrayList<Area>();
		if (newLayout.getAreas()!=null) {
			for(Area newArea : newLayout.getAreas()) {
				if (newArea.getId() == null || newArea.getId() == -1) {
					newArea.setLayoutid(old.getId());
					newList.add(newArea);
					continue;
				}
				if (old.getAreas()!=null) {
					for (Iterator<Area> it = old.getAreas().iterator(); it
							.hasNext();) {
						Area oldArea = (Area) it.next();
						if (oldArea.getId().equals(newArea.getId())) {
							oldArea.merge(newArea);
							updateList.add(oldArea);
							it.remove();
						}
					}
				}
			}
		}
		AreaMapper areaMapper = getMapper(AreaMapper.class);
		for(Area area:newList) {
			areaMapper.insert(area);
		}
		for(Area area:updateList) {
			areaMapper.update(area);
		}
		if (old.getAreas()!=null) {
			for(Area area: old.getAreas()) {
				areaMapper.deleteByKey(area.getId());
			}
		}
	}

	@Override
	public void insertWithArea(Layout newLayout) {
		LayoutMapper layoutMapper = getMapper(LayoutMapper.class);
		layoutMapper.insert(newLayout);
		if (newLayout.getAreas() != null) {
			AreaMapper areaMapper = getMapper(AreaMapper.class);
			for(Area area : newLayout.getAreas()) {
				area.setLayoutid(newLayout.getId());
				areaMapper.insert(area);
			}
		}
	}
	
	@Override
	public void deleteWithArea(long layoutId) {
		AreaMapper areaMapper = getMapper(AreaMapper.class);
		areaMapper.deleteByLayoutId(layoutId);
		LayoutMapper layoutMapper = getMapper(LayoutMapper.class);
		layoutMapper.deleteByKey(layoutId);
	}

	@Override
	public List<Layout> listCountAreaPaged(int begin, int end) {
		return getMapper(LayoutMapper.class).listCountAreaPaged(begin, end);
	}
}