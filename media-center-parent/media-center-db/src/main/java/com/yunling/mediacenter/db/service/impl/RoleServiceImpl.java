package com.yunling.mediacenter.db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.AuthorityMapper;
import com.yunling.mediacenter.db.mapper.RoleMapper;
import com.yunling.mediacenter.db.model.Authority;
import com.yunling.mediacenter.db.model.Roles;
import com.yunling.mediacenter.db.service.RoleService;

public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	private RoleMapper getMapper() {
		return getMapper(RoleMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.RoleService#getRoles()
	 */
	@Override
	public List<Roles> getRoles() {
		return getMapper().getRoles();
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.RoleService#createRole(com.yunling.mediacenter.db.model.Roles)
	 */
	@Override
	public void createRole(Roles role) {
		getMapper().createRole(role);
	}
	
	@Override
	public void updateRole(final Roles role) {
		getMapper().updateRole(role);
	}

	@Override
	public void delRoleById(final long roleid) {
		getMapper().delRoleById(roleid);
	}

	@Override
	public Roles getRoleByName(String roleName) {
		return getMapper().getRoleByName(roleName);
	}










	private Integer size;
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Map<String,Object> list( final Integer page,final Integer sum) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		final Map<String,Object> map = new HashMap<String,Object>();
		Integer count = sum%size==0 ? sum/size :sum/size+1;
		Integer page2=page;
		if(page<1){
			 page2=1;
		}
		if(page>count){
			 page2=count;
		}
		if(page2==1){
			map.put("begin", page2);
			map.put("end", page2*size);
		}else{
			map.put("begin",((page2-1)*size)+1);
			map.put("end",(page2*size));
		}
		List<Roles> list  =  getMapper().list(map);
 		resultMap.put("list",list);
 		resultMap.put("page",page2);
 		resultMap.put("count",count);
 		resultMap.put("size",size);
 		return resultMap;
	}
	
	public void addRole(final Roles role) {
		getMapper().addRole(role);
	}

	public Roles getRoleById(final long roleid) {
		return getMapper().getRoleById(roleid);
	}

	public List<Roles> getRoleByUserId(final long userId) {
		return getMapper().getRoleByUserId(userId);
	}

	public List<Roles> notRoleByUserId(final long userId) {
		return getMapper().notRoleByUserId(userId);
	}

	@Override
	public Integer selectRoleCount() {
		return getMapper().selectRoleCount();
	}
	
	@Override
	public void updateAuthorities(Long roleId, List<Long> authorityIdList) {
		List<Authority> authorityList = getMapper(AuthorityMapper.class).listGrantedByRole(roleId);
		List<Long> newList = new ArrayList<Long>();
		for(Long authId : authorityIdList) {
			for(Iterator<Authority> it = authorityList.iterator(); it.hasNext();) {
				Authority auth = it.next();
				if (authId.equals(auth.getId())) {
					if (!auth.isGranted()) {
						newList.add(authId);
					}
					it.remove();
					break;
				}
			}
		}
		for(Long newId: newList) {
			getMapper().setAuthority(roleId, newId);
		}
		for(Authority auth : authorityList) {
			if (auth.isGranted()) {
				getMapper().unsetAuthority(roleId, auth.getId());
			}
		}
	}
}
