package com.yunling.mediaman.web.ctl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.JqgridRow;
import com.yunling.mediaman.web.vo.RoleVo;
import com.yunling.policyman.db.model.AuthorityGroup;
import com.yunling.policyman.db.model.Role;
import com.yunling.policyman.db.model.helper.RoleHelper;
import com.yunling.policyman.db.service.AuthorityGroupService;
import com.yunling.policyman.db.service.RoleService;
import com.yunling.web.PageBean;


@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private WebConfig webConfig; 
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityGroupService authorityGroupService;
	
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		return mav;
	}
	
	@RequestMapping("/save")
	public @ResponseBody String save(@RequestParam("id") String id, @RequestParam("oper") String oper,
			@ModelAttribute RoleVo roleVo) {
		Role role = roleVo.toRole();
		if ("edit".equals(oper)) {
			Role old = null;
			if (role.getId() != null) {
				old = roleService.getByKey(role.getId());
			}
			if (old != null) {
				old.setName(role.getName());
				old.setDescription(role.getDescription());
				if (role.getEnabled()!=null ) {
					old.setEnabled(role.getEnabled());
				}
				roleService.update(role);
			}
		} else if ("add".equals(oper)) {
			roleService.insert(role);
		} else if ("del".equals(oper)) {
			Long roleId = Long.parseLong(id);
			roleService.deleteByKey(roleId);
		}
		return "success";
	}
	
	static String[] columns = {"name","description","enabled"}; 
	@RequestMapping("/grid")
	public @ResponseBody JqgridPage grid(@RequestParam("page") int page, 
			@RequestParam("rows") int rowNum) {
		int pageSize = rowNum > 0? rowNum:webConfig.getPageSize();
		int total = roleService.countAll();
		PageBean pb = new PageBean(page, total, pageSize);
		JqgridPage jqpage = new JqgridPage();
		jqpage.setPage(pb.getCurPage());
		jqpage.setTotal(pb.getTotalPage());
		jqpage.setRecords(total);
		if (pb.getTotalPage() > 0) {
			List<Role> roleList = roleService.listAllPaged(pb.getBegin(), pb.getEnd());
			List<JqgridRow> rows = new ArrayList<JqgridRow>(roleList.size());
			for(Role role : roleList) {
				JqgridRow row = new JqgridRow();
				row.setCell(RoleHelper.toSimpleArray(role,columns));
				row.setId(String.valueOf(role.getId()));
				rows.add(row);
			}
			jqpage.setRows(rows);
		}
		return jqpage;
	}
	
	@RequestMapping("/saveauth")
	public @ResponseBody String saveAuth(@RequestParam("id") int roleId, @RequestParam("authority")String[] authorities) {
		List<Long> authIdList = new ArrayList<Long>();
		if (authorities != null) {
			for(String auth : authorities) {
				try {
					Long id = Long.parseLong(auth);
					authIdList.add(id);
				} catch (NumberFormatException e) {
				}
			}
		}
		roleService.updateAuthorities(roleId,authIdList);
		return "success";
	}
	
	@RequestMapping("/listauth")
	public ModelAndView listAuth(@RequestParam("id")int roleId, ModelAndView mav) {
		List<AuthorityGroup> authGroupList = authorityGroupService.listByRole(roleId);
		mav.addObject("authGroupList", authGroupList);
		mav.addObject("roleId", roleId);
		return mav;
	}

}
