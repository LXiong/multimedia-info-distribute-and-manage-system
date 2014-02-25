package com.yunling.mediaman.web.ctl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.JqgridRow;
import com.yunling.policyman.db.model.Role;
import com.yunling.policyman.db.model.User;
import com.yunling.policyman.db.model.helper.UserHelper;
import com.yunling.policyman.db.service.RoleService;
import com.yunling.policyman.db.service.UserService;
import com.yunling.web.PageBean;

@Controller
@RequestMapping("/manuser")
@PreAuthorize("hasRole('AUTH_MAN_USER')")
public class ManUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private WebConfig webConfig;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(@RequestParam(defaultValue = "1") int page,
			ModelAndView mav) {
		return mav;
	}

	@RequestMapping("/save")
	public @ResponseBody
	String save(@RequestParam("id") String id,
			@RequestParam("oper") String oper,
			@RequestParam(value="password", required=false) String password,
			@RequestParam(value="password2", required=false) String password2,
			@ModelAttribute User userVo) {
		boolean withPassword = StringUtils.isNotEmpty(password) && StringUtils.equals(password, password2);
		if ("edit".equalsIgnoreCase(oper)) {
			userService.update(userVo, withPassword);
		} else if ("add".equalsIgnoreCase(oper)) {
			if (!withPassword) {
				return "new_missing_passwd";
			}
			User old = userService.getByKey(userVo.getUsername());
			if (old != null) {
				return "name_exists";
			}
			userService.insert(userVo, withPassword);
		} else if ("del".equalsIgnoreCase(oper)) {
			userService.deleteByKey(String.valueOf(id));
		}
		return "success";
	}

	static String[] columns = { "username", "disp_name", "email", "phone", "enabled",
			"last_login" };

	@RequestMapping(value = "/grid")
	public @ResponseBody
	JqgridPage grid(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		int total = userService.countAll();
		PageBean pb = new PageBean(pageNo, total,  rows>0 ?rows : webConfig.getPageSize());
		List<User> userList = userService.listAllPaged(pb.getBegin(), pb.getEnd());
		jpage.setPage(pb.getCurPage());
		jpage.setTotal(pb.getTotalPage());
		jpage.setRecords(total);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>();
		for (User user : userList) {
			JqgridRow row = new JqgridRow();
			row.setId(user.getUsername());
			String[] arr = UserHelper.toSimpleArray(user, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		// mav.addObject("userList", userList);
		return jpage;
	}
	
	@RequestMapping("/listrole")
	public ModelAndView listRole(@RequestParam("id") String userName, ModelAndView mav) {
		User user = userService.getByKey(userName);
		if (user == null) {
			mav.setViewName("manuser/nouser");
			return mav;
		}
		mav.addObject("user", user);
		List<Role> roleList = roleService.listByUser(userName);
		mav.addObject("roleList", roleList);
		return mav;
	}
	
	@RequestMapping("/saverole")
	public @ResponseBody String saveRole(@RequestParam("id") String userName,
			@RequestParam("role") String[] roleArr) {
		List<Long> roleIdList = new ArrayList<Long>();
		if (roleArr!=null) {
			for(String idStr : roleArr) {
				try {
					Long id = Long.parseLong(idStr);
					roleIdList.add(id);
				} catch (NumberFormatException e) {
				}
			}
		}
		userService.updateRole(userName, roleIdList);
		return "success";
	}
}
