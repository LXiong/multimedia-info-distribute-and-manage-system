package com.yunling.mediaman.web.vo;

import java.util.LinkedList;
import java.util.List;

public class ScreenArea extends HtmlTag {

    static int id_account = 1;
    static String[] outPutAttributes = { "name", "play_type", "left_top_x",
	    "left_top_y", "right_bottom_x", "right_bottom_y"};
    List<PlayListTag> playLists = new LinkedList<PlayListTag>();

    
    public ScreenArea(String value) {
	super("area", value);
	this.id = String.valueOf(id_account++);
    }
    
    public ScreenArea() {
	this("area");
    }
    
    public void addPlayList(PlayListTag list) {
	playLists.add(list);
    }
    public void addAllList(List<PlayListTag> lists) {
	playLists.addAll(lists);
    }
    
    public String toHtml() {
	StringBuffer sb = new StringBuffer();
	
	return sb.toString();
    }
    
    public String toXml() {
	StringBuffer sb = new StringBuffer();
	sb.append("<area>");
	for(String attr : outPutAttributes) {
	    if(attributes.containsKey(attr)) {
		sb.append("<" + attr + ">" + attributes.get(attr) + "</" + attr + ">");
	    }
	}
	for(PlayListTag tag : playLists) {
	    sb.append(tag.toXml());
	}
	sb.append("</area>");
	return sb.toString();
    }

}
