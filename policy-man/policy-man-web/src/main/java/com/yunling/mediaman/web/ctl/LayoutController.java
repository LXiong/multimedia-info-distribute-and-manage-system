package com.yunling.mediaman.web.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.ctl.json.JsonResult;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.JqgridRow;
import com.yunling.policyman.db.model.Area;
import com.yunling.policyman.db.model.Layout;
import com.yunling.policyman.db.model.helper.LayoutHelper;
import com.yunling.policyman.db.service.LayoutService;
import com.yunling.web.PageBean;

@Controller
@RequestMapping("/layout")
public class LayoutController {
	@Autowired
	private LayoutService layoutService;
	@Autowired
	private WebConfig webConfig;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView layout(ModelAndView mav) {
		return mav;
	}

	static String[] columns = { "name", "width", "height", "lcomment" };

	@RequestMapping(value = "/grid")
	public @ResponseBody
	JqgridPage grid(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		int total = layoutService.countAll();
		PageBean pageBean = new PageBean(pageNo, total,  rows>0 ?rows : webConfig.getPageSize());
		List<Layout> layoutList = layoutService.listAllPaged(
				pageBean.getBegin(), pageBean.getEnd());
		jpage.setPage(pageBean.getCurPage());
		jpage.setTotal(pageBean.getTotalPage());
		jpage.setRecords(total);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>();
		for (Layout lay : layoutList) {
			JqgridRow row = new JqgridRow();
			row.setId(lay.getId().toString());
			String[] arr = LayoutHelper.toSimpleArray(lay, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping("/editform")
	public ModelAndView edit(
			@RequestParam(value = "id", defaultValue = "-1", required = false) int layoutId,
			ModelAndView mav) {
		Layout layout;
		if (layoutId == -1) {
			layout = new Layout();
			layout.setUsePercent(Boolean.TRUE);
			layout.setWidth(1280l);
			layout.setHeight(720l);
		} else {
			layout = layoutService.getWithArea(new Long(layoutId));
		}
		mav.addObject("layout", layout);
		return mav;
	}

	@RequestMapping("/save")
	public @ResponseBody
	JsonResult save(@RequestBody Layout newLayout, ModelAndView mav) {
		if (newLayout.getId() != null && newLayout.getId() != -1) {
			layoutService.updateWithArea(newLayout);
		} else {
			layoutService.insertWithArea(newLayout);
		}
		return new JsonResult("success");
	}

	@RequestMapping("/delete")
	public @ResponseBody JsonResult delete(@RequestParam("id")long layoutId) {
		JsonResult result = new JsonResult("success");
		Layout layout = layoutService.getByKey(layoutId);
		if (layout !=null) {
			layoutService.deleteWithArea(layoutId);
		}
		return result;		
	}
	
	
	@RequestMapping("/sellist")
	public @ResponseBody JqgridPage sellist(
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="rows", defaultValue="-1") int rows
			) {
		int total = layoutService.countAll();
		PageBean pb = new PageBean(page, total, rows>0?rows:webConfig.getPageSize());
		JqgridPage selPage = new JqgridPage();
		selPage.setPage(pb.getCurPage());
		selPage.setTotal(pb.getTotalPage());
		List<Layout> layoutList = layoutService.listCountAreaPaged(pb.getBegin(), pb.getEnd());
		for(Layout layout : layoutList) {
			String[] row = new String[] {
					layout.getName(),String.valueOf(layout.getAreaCount())
			};
			selPage.addRow(row, String.valueOf(layout.getId()));
		}
		return selPage;
	}
	
	@RequestMapping("/jdetail")
	public @ResponseBody Map<String,Object> detail(@RequestParam(value="id") long id) {
		Map<String,Object> result = new HashMap<String, Object>();
		Layout layout = layoutService.getWithArea(id);
		result.put("id", layout.getId());
		result.put("name", layout.getName());
		result.put("lcomment", layout.getLcomment());
		List<Map<String,Object>> areaList = new ArrayList<Map<String,Object>>();
		for(Area area: layout.getAreas()) {
			Map<String,Object> areaMap = new HashMap<String, Object>();
			areaMap.put("id", area.getId());
			areaMap.put("name", area.getName());
			areaMap.put("type", area.getType());
			areaMap.put("lcomment", area.getLcomment());
			areaMap.put("left", area.getLeft());
			areaMap.put("top", area.getTop());
			areaMap.put("width", area.getWidth());
			areaMap.put("height", area.getHeight());
			areaMap.put("timeFormat", area.getTimeFormat());
			areaList.add(areaMap);
		}
		result.put("areas", areaList);
		return result;
	}
}
