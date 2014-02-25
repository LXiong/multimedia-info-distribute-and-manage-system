package com.yunling.mediaman.web.utils;

import com.yunling.mediaman.web.vo.HtmlTag;

public class HtmlTagFactory {

    /**
     * main:(这里用一句话描述这个方法的作用)
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param args    设定文件
     * @return void    DOM对象
     * @throws 
     * @since  CodingExample　Ver 1.1
     */
    public static HtmlTag generateLayout(String type) {
	HtmlTag layouts = new HtmlTag("div");
	
	HtmlTag left = new HtmlTag("div");
	left.add("style", "width:80%;float:left;height:100%;");
	HtmlTag top = new HtmlTag("div");
	top.add("class", "area");
	top.add("style", "bg_color:red");
	HtmlTag buttom = new HtmlTag("div");
	buttom.add("class", "area");
	buttom.add("style", "bg_color:green");
	
	
	left.add(top);
	left.add(buttom);
	
	
	HtmlTag right = new HtmlTag("div");
	right.add("style", "float:left;width:19%;height:100%;bg_color:yellow");
	right.add("class", "area");
	
	
	
	layouts.add(left);
	layouts.add(right);
	return layouts;
    }
    
    public static HtmlTag generatePlayList(String type) {
	HtmlTag lists = new HtmlTag("div");
	lists.add("class", "lists");
	
	HtmlTag select = new HtmlTag("select");
	select.add("name", "play_mode");
	HtmlTag option1 = new HtmlTag("option");
	option1.add("value", "set_time");
	option1.setValue("TimingPlay");
	HtmlTag option2 = new HtmlTag("option");
	option2.add("value", "loop");
	option2.setValue("LoopPlay");
	select.add(option1);
	select.add(option2);
	
	HtmlTag addList = new HtmlTag("div");
	addList.add("class", "add_list");
	
	HtmlTag ul = new HtmlTag("ul");
	HtmlTag li = new HtmlTag("li");
	li.setValue("play list tab");
	ul.add(li);
	HtmlTag a = new HtmlTag("a");
	a.setValue("This is a play list");
	li.add(a);
	
	HtmlTag tabs = new HtmlTag("div");
	
	lists.add(select);
	lists.add(addList);
	lists.add(ul);
	lists.add(tabs);
	return lists;
    }
    
    public static HtmlTag generateFiles(String type) {
	HtmlTag div = new HtmlTag("div");
	for(int i = 0; i < 10; i++) {
	    HtmlTag checkbox = new HtmlTag("input");
	    checkbox.add("type", "checkbox");
	    checkbox.add("name", "file"+i);
	    checkbox.add("id", "file"+i);
	    checkbox.add("value", "file_path"+i);
	    
	    HtmlTag label = new HtmlTag("label");
	    label.add("for", "file"+i);
	    label.setValue("file_path"+i);
	    
	    div.add(checkbox);
	    div.add(label);
	}
	return div;
    }
    public static void main(String[] args) {
	// TODO Auto-generated method stub

    }

}
