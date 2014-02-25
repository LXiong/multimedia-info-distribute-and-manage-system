package com.yunling.mediaman.web.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.ctl.json.JsonResult;
import com.yunling.policyman.db.model.Category;
import com.yunling.policyman.db.service.CategoryService;

/**
 * @author LingJun
 * @date 2011-3-7
 * 
 */
@Controller
@RequestMapping("/videos")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/categories")
	public ModelAndView categories(ModelAndView mav) {
		List<Category> categoryList = categoryService.listTree();
		// List<Category> parentList = new ArrayList<Category>();
		List<Category> childList = null;
		Map<Category, List<Category>> categoryMap = new HashMap<Category, List<Category>>();
		Category category = null;
		Category temp = null;
		Iterator<Category> it = categoryList.iterator();
		while (it.hasNext()) {
			category = it.next();
			if (category.getParentId() == 0) {
				// parentList.add(category);
				temp = category;
				childList = new ArrayList<Category>();
				categoryMap.put(temp, childList);
			} else {
				childList = categoryMap.get(temp);
				childList.add(category);
				categoryMap.put(temp, childList);
			}
		}
		mav.addObject("categoryMap", categoryMap);
		return mav;
	}

	@RequestMapping(value = "category/update")
	public @ResponseBody
	String update(@RequestParam("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("description") String description) {
		Category category = categoryService.getByKey(id);
		Category temp = new Category();
		temp.setName(name);
		temp.setParentId(category.getParentId());
		JsonResult result = new JsonResult("true");
		List<Category> list = categoryService.getByModel(temp);
		if (list.size() > 0) {
			Iterator<Category> it = list.iterator();
			while(it.hasNext()) {
				if(it.next().getId() != id) {
					result.setFlag("false");
					result.setMessage("category.name.confict");
					return "category.name.confict";
				}
			}
		}
		category.setName(name);
		category.setDescription(description);
		categoryService.update(category);
		return "";
	}

	@RequestMapping(value = "category/add")
	public @ResponseBody
	String add(@RequestParam("parentId") Long parentId,
			@RequestParam("name") String name,
			@RequestParam("description") String description) {
		Category category = new Category();
		category.setName(name);
		category.setParentId(parentId);
		JsonResult result = new JsonResult("true");
		if (categoryService.getByModel(category).size() > 0) {
			result.setFlag("false");
			result.setMessage("category.name.confict");
			return "category.name.confict";
		} else {
			category.setDescription(description);
			categoryService.insert(category);
		}
		return "";
	}

	@RequestMapping(value = "category/del")
	public @ResponseBody
	String del(@RequestParam("id") Long id) {
		Category category = new Category();
		category.setParentId(id);
		JsonResult result = new JsonResult("true");
		if (categoryService.getByModel(category).size() > 0) {
			result.setFlag("false");
			result.setMessage("category.can.not.delete");
			return "category.can.not.delete";
		} else {
			categoryService.deleteByKey(id);
		}
		return "";
	}
}
