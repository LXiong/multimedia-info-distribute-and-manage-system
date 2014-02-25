package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.yunling.mediacenter.db.mapper.UserMapper;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.UserStb;
import com.yunling.mediacenter.db.model.Users;
import com.yunling.mediacenter.db.service.UserService;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private Integer size;

	private UserMapper getUserMapper() {
		return getMapper(UserMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.UserService#getUsers()
	 */
	@Override
	public List<Users> getUsers() {
		return getUserMapper().getUsers();
	}

	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.UserService#createUser(com.yunling.mediacenter.db.model.Users)
	 */
	@Override
	public void createUser(Users user) {
		getUserMapper().createUser(user);
	}
	
	@Override
	public void updateUser(final Users user) {
		getUserMapper().updateUser(user);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.UserService#delByLoginName(java.lang.String)
	 */
	@Override
	public void delByLoginName(String loginName) {
		getUserMapper().delByLoginName(loginName);
	}

	@Override
	public Users getUserByLoginName(final String loginName) {
		return getUserMapper().getUserByLoginName(loginName);
	}
	
	
	
	
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Users getUserByIdPassword(final String loginname,
			final String password) {
		Users param = new Users();
		param.setLoginName(loginname);
		param.setPassword(password);
		return getUserMapper().findByIdPassword(param);
	}

	public Map<String, Object> list(final Integer page, final Integer sum) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String, Object> map = new HashMap<String, Object>();
		Integer count = sum % size == 0 ? sum / size : sum / size + 1;
		Integer page2 = page;
		if (page < 1) {
			page2 = 1;
		}
		if (page > count) {
			page2 = count;
		}
		if (page2 == 1) {
			map.put("begin", page2);
			map.put("end", page2 * size);
		} else {
			map.put("begin", ((page2 - 1) * size) + 1);
			map.put("end", (page2 * size));
		}
		List<Users> list = getUserMapper().list(map);
		resultMap.put("list", list);
		resultMap.put("page", page2);
		resultMap.put("count", count);
		resultMap.put("size", size);
		return resultMap;
	}

	public boolean delById(final long userid) {
		getUserMapper().delById(userid);
		return true;
	}

	public void addUser(final Users uu) {
		getUserMapper().addUser(uu);
	}

	public Users findUserByLoginName(final String loginname) {
		return getUserMapper().findUserByLoginName(loginname);
	}

	public void changePassword(final Users uu) {
		getUserMapper().changePassword(uu);
	}

	public Users getUserById(final long userid) {
		return getUserMapper().getUserById(userid);
	}

	@Override
	public Integer selectUserCount() {
		return getUserMapper().selectUserCount();
	}

	@Override
	public List<GroupType> groupTypeList() {
		return getUserMapper().groupTypeList();
	}

	@Override
	public List<Groups> groupsList() {
		return getUserMapper().groupsList();
	}

	@Override
	public List<UserStb> getUserStbById(final long userid) {
		return getUserMapper().getUserStbById(userid);
	}

	@Override
	public void delUserStbById(final long userid) {
		getUserMapper().delUserStbById(userid);
	}
	
	@Transactional
	public void setUserStb(long userid, long[] groups, long[] types) {
		getUserMapper().delUserStbById(userid);
		if (groups!=null && groups.length>0) {
			for(long group : groups) {
				getUserMapper().addUserStb(userid, group, 5);
			}
		}
		if (types!=null && types.length>0) {
			for(long type : types) {
				getUserMapper().addUserStb(userid, type, 4);
			}
		}
	}

	@Override
	public void addUserStb(final long userid, final long parseLong, final int i) {
		getUserMapper().addUserStb(userid, parseLong, i);
	}

	@Override
	public void updateLastLogin(final long userId) {
		getUserMapper().updateLastLogin(userId);
	}

	@Override
	public Integer getAuthByStatus(final String string) {
		return getUserMapper().getAuthByStatus(string);
	}
	@Override
	public Integer getAuthByStatusIsFail(final String string) {
		return getUserMapper().getAuthByStatusIsFail(string);
	}

	@Override
	public Integer getStbByStatusIsPending(String string) {
		return getUserMapper().getStbByStatusIsPending(string);
	}
}
