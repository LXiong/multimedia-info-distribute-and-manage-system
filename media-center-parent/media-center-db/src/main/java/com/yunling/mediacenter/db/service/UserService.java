package com.yunling.mediacenter.db.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.UserStb;
import com.yunling.mediacenter.db.model.Users;

public interface UserService {
	public List<Users> getUsers();
	
	public void createUser(Users user);
	
	public void updateUser(Users user);
	
	public void delByLoginName(String loginName);

	public Users getUserByLoginName(String loginName);
	
	
	Map<String, Object> list(Integer page, Integer sum);

	Users getUserByIdPassword(final String loginname, final String password);

	boolean delById(final long userid);

	void addUser(Users uu);

	Users findUserByLoginName(String loginname);

	void changePassword(Users uu);

	Users getUserById(long userid);

	Integer selectUserCount();

	List<GroupType> groupTypeList();

	List<Groups> groupsList();

	List<UserStb> getUserStbById(long userid);

	void delUserStbById(long userid);

	void addUserStb(long userid, long parseLong, int i);

	Integer getAuthByStatus(String string);

	Integer getAuthByStatusIsFail(String string);

	void updateLastLogin(long userId);

	Integer getStbByStatusIsPending(String string);

	@Transactional
	void setUserStb(long userid, long[] groups, long[] types);

}
