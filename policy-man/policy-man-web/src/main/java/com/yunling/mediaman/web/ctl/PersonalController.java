package com.yunling.mediaman.web.ctl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.utils.UserUtil;
import com.yunling.policyman.db.model.User;
import com.yunling.policyman.db.service.UserService;

@Controller
@RequestMapping("/personal")
public class PersonalController {
	
	@Autowired
	private UserService userService;

	/**
	 * Default showing user personal information
	 * @param mav
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(ModelAndView mav) {
		mav.addObject("user", getUser());
		mav.setViewName("personal/info");
		return mav;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(ModelAndView mav) {
		mav.addObject("user", getUser());
		return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute("user")User user, ModelAndView mav) {
		User oldone= getUser();
		oldone.setDispName(user.getDispName());
		oldone.setEmail(user.getEmail());
		oldone.setPhone(user.getPhone());
		userService.update(oldone);
		mav.setViewName("redirect:/personal");
		return mav;
	}
	
	@RequestMapping("/passwd")
	public ModelAndView passwd(ModelAndView mav) {
		return mav;
	}
	
	@RequestMapping("/change")
	public ModelAndView change(@RequestParam("oldpass") String oldPass,
			@RequestParam("newpass1") String newpass1, @RequestParam("newpass2") String newpass2, 
			ModelAndView mav) {
		User oldone= getUser();
		if (StringUtils.equals(oldone.getPassword(), oldPass)
				&& !StringUtils.isEmpty(newpass1)
				&& StringUtils.equals(newpass1, newpass2)) {
			oldone.setPassword(newpass1);
			userService.update(oldone, true);
			mav.setViewName("redirect:/personal/changed");
		} else {
			mav.setViewName("redirect:/personal/nochange");
		}
		return mav;
	}
	
	@RequestMapping("/changed")
	public ModelAndView changed(ModelAndView mav) {
		return mav;
	}
	
	@RequestMapping("/nochange")
	public ModelAndView nochange(ModelAndView mav) {
		return mav;
	}
	
	private User getUser() {
		String key = UserUtil.getUsername();
		User user = userService.getByKey(key);
		return user;
	}
}
