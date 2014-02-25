package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.Category;
import com.yunling.policyman.db.service.base.BaseCategoryService;

public interface CategoryService extends BaseCategoryService {
	public List<Category> listTree();
	
	public List<Category> getByModel(Category category);
}