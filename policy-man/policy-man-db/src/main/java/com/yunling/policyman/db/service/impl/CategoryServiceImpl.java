package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.mapper.CategoryMapper;
import com.yunling.policyman.db.model.Category;
import com.yunling.policyman.db.service.CategoryService;
import com.yunling.policyman.db.service.impl.base.BaseCategoryServiceImpl;

public class CategoryServiceImpl extends BaseCategoryServiceImpl implements
		CategoryService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yunling.policyman.db.service.CategoryService#listTree()
	 */
	@Override
	public List<Category> listTree() {
		return getMapper(CategoryMapper.class).listTree();
	}

	/* (non-Javadoc)
	 * @see com.yunling.policyman.db.service.CategoryService#getByModel(com.yunling.policyman.db.model.Category)
	 */
	@Override
	public List<Category> getByModel(Category category) {
		return getMapper(CategoryMapper.class).getByModel(category);
	}

}