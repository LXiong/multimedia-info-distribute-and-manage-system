package com.yunling.policyman.db.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunling.policyman.db.mapper.AuthorityMapper;
import com.yunling.policyman.db.mapper.RoleAuthorityMapper;
import com.yunling.policyman.db.mapper.RoleMapper;
import com.yunling.policyman.db.model.Authority;
import com.yunling.policyman.db.model.Role;
import com.yunling.policyman.db.model.RoleAuthority;
import com.yunling.policyman.db.service.RoleService;
import com.yunling.policyman.db.service.impl.base.BaseRoleServiceImpl;

public class RoleServiceImpl
	extends BaseRoleServiceImpl
	implements RoleService
{

	@Override
	public void updateAuthorities(int roleId, List<Long> authIdList) {
		AuthorityMapper mapper = getMapper(AuthorityMapper.class);
		List<Authority> authList = mapper.listByRole(roleId);
		List<Long> newList = new ArrayList<Long>();
		List<Long> delList = new ArrayList<Long>();
		for(Long newId : authIdList) {
			for(Iterator<Authority> it = authList.iterator(); it.hasNext();) {
				Authority auth = it.next();
				if (newId.equals(auth.getId())) {
					if (!auth.isGranted()) {
						newList.add(newId);
					}
					it.remove();
					break;
				}
			}
		}
		for(Authority auth : authList) {
			if (auth.isGranted()) {
				delList.add(auth.getId());
			}
		}
		RoleAuthorityMapper raMapper = getMapper(RoleAuthorityMapper.class);
		if (delList.size()>0) {
			for(Long id : delList) {
				raMapper.deleteByKey(new Long(roleId), id);
			}
		}
		if (newList.size()>0) {
			for(Long id : newList) {
				RoleAuthority ra = new RoleAuthority();
				ra.setRoleId(new Long(roleId));
				ra.setAuthorityId(id);
				raMapper.insert(ra);
			}
		}
	}

	@Override
	public List<Role> listByUser(String userName) {
		return getMapper(RoleMapper.class).listByUser(userName);
	}
	
}