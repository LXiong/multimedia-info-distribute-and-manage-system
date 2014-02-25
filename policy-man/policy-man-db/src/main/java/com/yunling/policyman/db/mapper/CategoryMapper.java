package com.yunling.policyman.db.mapper;

import java.util.List;

import com.yunling.policyman.db.mapper.base.BaseCategoryMapper;
import com.yunling.policyman.db.model.Category;

public interface CategoryMapper extends BaseCategoryMapper {
	public List<Category> listTree();
	
	public List<Category> getByModel(Category category);
}
