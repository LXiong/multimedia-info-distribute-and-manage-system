package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.Authority;

public interface AuthorityMapper {
	List<Authority> list();
	List<Authority> listByRole(Long roleId);
	List<Authority> listGrantedByRole(Long roleId);
}
