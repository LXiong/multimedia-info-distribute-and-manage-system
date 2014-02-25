package com.yunling.commons.log;

public interface WebLog {
	public void logLogin(String loginId, boolean isSuccess);
	public void logAdd(int currentUserId, int newUserId, String newUserName);
	public void logModify(int currentUserId, String modifiedUser, String newAttributes);
	public void logDelete(int currentUserId, String modifiedUser);
	public void logStb(int currentUserId, String group, String mac/*need truncating?*/, String operation);
	public void logDownload(int currentUserId,String item);
}
