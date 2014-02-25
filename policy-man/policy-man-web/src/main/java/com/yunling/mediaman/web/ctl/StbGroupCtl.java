package com.yunling.mediaman.web.ctl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.service.StbGroupLevelTwoService;
import com.yunling.policyman.db.service.StbGroupService;

@Controller
@RequestMapping("/stb_group")
public class StbGroupCtl {
	
	@Autowired
	StbGroupService stbGroupService;
	
	@Autowired
	StbGroupLevelTwoService stbGroupLevelTwoService;

	@RequestMapping("/aj_list")
	public @ResponseBody List<StbGroup> ajaxList() {
		List<StbGroup> groupList = stbGroupService.listAll();
		Iterator<StbGroup> it = groupList.iterator();
		while(it.hasNext()){
			StbGroup groupType = it.next();
			List<StbGroupLevelTwo> groups = stbGroupLevelTwoService.listByGroupType(groupType);
			groupType.setGroupLevelTwo(groups);
		}
		return groupList;
	}
}
