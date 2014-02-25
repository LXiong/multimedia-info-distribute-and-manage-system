package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Authority;

public interface AuthorityService {
	List<Authority> list();
	List<Authority> listByRole(Long roleId);
	List<Authority> listGrantedByRole(Long roleId);
}
