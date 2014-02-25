package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseAuthorityGroupMapper;
import com.yunling.policyman.db.model.AuthorityGroup;

public interface AuthorityGroupMapper
	extends BaseAuthorityGroupMapper
{
	List<AuthorityGroup> listWithAuth();
	
	List<AuthorityGroup> listByRole(@Param("role_id") int roleId);
}
