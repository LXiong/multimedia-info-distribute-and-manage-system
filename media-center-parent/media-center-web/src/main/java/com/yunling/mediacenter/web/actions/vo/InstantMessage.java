package com.yunling.mediacenter.web.actions.vo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;


public class InstantMessage {

	String fileName;
	String sendTime;
	String messageContent;
	int playTimes;
	String groupId;
	String[] stbMac;
	String backgroundColor;
	String textColor;
	String textFont;
	String startX;
	String startY;
	String endX;
	String endY;
	
	//List<String> stbMac;
	
	
	public InstantMessage(){
		
	}
	public InstantMessage(String content){
		this.messageContent = content;
		this.playTimes = 1;
	}

	public String getMessageContent() {
		return messageContent;
	}
	
	public String getShortContent(){
		if(messageContent == null){
			return "read error.";
		}
		if(messageContent.length() < 10){
			return messageContent;
		}else{
			return messageContent.substring(0, 7) + "...";
		}
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public List<String> getStbMac() {
		return Arrays.asList(stbMac);
	}
	
	public String getMacJoin(){
		return StringUtils.join(stbMac);
	}
	
	public String getMacJoinShort(){
		String joinResult = getMacJoin();
		if(joinResult == null || "".equals(joinResult) ){
			return "";
		}
		if(joinResult.length() < 10){
			return joinResult;
		}
		return joinResult.substring(0, 7) + "...";
	}

	public void setStbMac(String stbMac) {
		this.stbMac = stbMac.split(",");
	}

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}
	
	
	public void setAttribute(String name, String value){
		if("send_time".equals(name)){
			setSendTime(value);
		}else if("content".equals(name)){
			setMessageContent(value);
		}else if("timecount".equals(name)){
			setPlayTimes(Integer.parseInt(value));
		}else if("target_addresses".equals(name)){
			//String[] macs = value.replace("[", "").replace("]", "").split(",");
			setStbMac(value.replace("[", "").replace("]", ""));
		}else if("bcolor".equals(name)){
			setBackgroundColor(value);
		}else if("font".equals(name)){
			this.setTextFont(value);
		}else if("char_color".equals(name)){
			this.setTextColor(value);
		}else if("lx".equals(name)){
			this.setStartX(value);
		}else if("ly".equals(name)){
			this.setStartY(value);
		}else if("rx".equals(name)){
			this.setEndX(value);
		}else if("ry".equals(name)){
			this.setEndY(value);
		}
	}
	public Map<String, String> getMessageParam() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("content", messageContent);
		map.put("timecount", String.valueOf(this.getPlayTimes()));
		
		/*map.put("lx", String.valueOf((int) (Float.parseFloat(startX) * 7.20)));
		map.put("ly", String.valueOf((int) (Float.parseFloat(startY) * 4.80)));
		map.put("rx", String.valueOf((int) (Float.parseFloat(endX) * 7.20)));
		map.put("ry", String.valueOf((int) (Float.parseFloat(endY) * 4.80)));*/
		map.put("lx", startX);
		map.put("ly", startY);
		map.put("rx", endX);
		map.put("ry", endY);
		map.put("char_color", textColor.replace("#", "0x"));
		map.put("bcolor", backgroundColor.replace("#", "0x"));
		map.put("font", textFont);
		return map;
	}
	
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getFileName() {
		return fileName;//.substring(0, fileName.lastIndexOf("."));
	}
	public String getFileNameWithoutExtentionName(){
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	public String getBackgroundColorValue(){
		return convertHexToWell(getBackgroundColor());
	}
	
	public String convertHexToWell(String hex){
		if(hex.startsWith("0x")){
			return hex.replace("0x", "#");
		}
		return hex;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getTextColor() {
		return textColor;
	}
	
	public String getTextColorValue(){
		return convertHexToWell(getTextColor());
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextFont() {
		return textFont;
	}
	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}
	public String getStartX() {
		return startX;
	}
	public void setStartX(String startX) {
		this.startX = startX;
	}
	public String getStartY() {
		return startY;
	}
	public void setStartY(String startY) {
		this.startY = startY;
	}
	public String getEndX() {
		return endX;
	}
	public void setEndX(String endX) {
		this.endX = endX;
	}
	public String getEndY() {
		return endY;
	}
	public void setEndY(String endY) {
		this.endY = endY;
	}
	
	
}
