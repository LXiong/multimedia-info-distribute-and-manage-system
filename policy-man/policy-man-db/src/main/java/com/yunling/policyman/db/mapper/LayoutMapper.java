package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseLayoutMapper;
import com.yunling.policyman.db.model.Layout;

public interface LayoutMapper
	extends BaseLayoutMapper
{
	Layout getWithArea(@Param("layout_id")long layoutId);

	List<Layout> listCountAreaPaged(@Param("begin")long begin, @Param("end")long end);
}
