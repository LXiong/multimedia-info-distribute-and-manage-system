package com.yunling.mediaman.web.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageOnlyController {
	
	@RequestMapping("/404")
	public ModelAndView p404() {
		return createModelAndView("error_404");
	}

	@RequestMapping("/")
	public ModelAndView root() {
		return createModelAndView("redirect:/home");
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		return createModelAndView("login_page");
	}
	
	@RequestMapping("/access_denied")
	public ModelAndView accessDenied() {
		return createModelAndView("access_denied");
	}
	
	private ModelAndView createModelAndView(String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
}
