package com.yunling.policyman.db.model;

import java.util.List;

import com.yunling.policyman.db.model.base.BaseStbGroup;

public class StbGroup extends BaseStbGroup {
	List<StbGroupLevelTwo> groupLevelTwo;

	public List<StbGroupLevelTwo> getGroupLevelTwo() {
		return groupLevelTwo;
	}

	public void setGroupLevelTwo(List<StbGroupLevelTwo> groupLevelTwo) {
		this.groupLevelTwo = groupLevelTwo;
	}
}