package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Policy;

public interface PolicyService {

	void addPolicy(Policy pp);

	List<Policy> listLatest(int count);
	
	List<Policy> cityLatest(String city, int count);
	
}

