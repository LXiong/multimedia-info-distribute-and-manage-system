package com.yunling.mediaman.web.ctl.json;

public class JsonResult {

	private String flag;
	private String message;
	private Object value;
	
	public static JsonResult SUCCESS = new JsonResult("success");
	public static JsonResult NOT_FOUND = new JsonResult("not_found");
	public static JsonResult WRONG_STATUS = new JsonResult("wrong_status");
	
	public JsonResult(String flag) {
		this.flag = flag;
	}
	
	public JsonResult(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
