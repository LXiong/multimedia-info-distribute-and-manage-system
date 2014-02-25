package com.yunling.mediaman.web.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MediaFilesController {

    @RequestMapping(value = "/media_fiels", method = RequestMethod.GET)
    public ModelAndView index() {
	ModelAndView mav = new ModelAndView();
	
	return mav;
    }
}
