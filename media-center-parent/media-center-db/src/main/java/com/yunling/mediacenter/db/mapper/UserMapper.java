package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.UserStb;
import com.yunling.mediacenter.db.model.Users;

public interface UserMapper {
	public List<Users> getUsers();
	
	public void createUser(Users user);
	
	public void updateUser(Users user);
	
	public void delByLoginName(String loginName);
	
	public Users getUserByLoginName(String loginName);
	
	
	
	
	

	public List<Users> list(Map<String, Object> map);

	public Users findByIdPassword(Users tmp);

	public Object delById(Long userid);

	public Object addUser(Users uu);

	public Users findUserByLoginName(String loginname);

	public Object changePassword(Users uu);

	public Users getUserById(long userid);

	//public Object updateUser(Users user);

	public Integer selectUserCount();

	public List<GroupType> groupTypeList();

	public List<Groups> groupsList();

	public List<UserStb> getUserStbById(long userid);

	public Object delUserStbById(long userid);

	public Object addUserStb(@Param("userid") long userId,
			@Param("typeid") long typeid, @Param("typeflag") int typeflag);

	// public Object addUserStb(Map<String, Object> map);
	public Integer getAuthByStatus(String string);

	public Object updateLastLogin(long userId);

	public Integer getAuthByStatusIsFail(String string);

	public Integer getStbByStatusIsPending(String string);

}
