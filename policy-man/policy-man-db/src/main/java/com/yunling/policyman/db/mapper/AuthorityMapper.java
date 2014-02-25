package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseAuthorityMapper;
import com.yunling.policyman.db.model.Authority;

public interface AuthorityMapper
	extends BaseAuthorityMapper
{

	List<Authority> listByRole(@Param("role_id") int roleId);

}
