package com.yunling.mediaman.web.vo;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class HtmlTag {

	private static int id_account = 1;
	private static String[] htmlAttributes = { "name", "class", "style", "value",
			"type" };
	private static Map<String, String> mediaTypes = new HashMap<String, String>();
	private static List<HtmlTag> areaContent;
	static {
		mediaTypes.put("audio", "AUDIO");
		mediaTypes.put("video", "VIDEO");
		mediaTypes.put("picture", "IMAGE");
		mediaTypes.put("text", "TEXT");
	}

	protected String name;
	protected String value;
	protected String id;
	protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	protected List<HtmlTag> children = new LinkedList<HtmlTag>();

	protected HtmlTag parent;

	public HtmlTag(String name) {
		this.name = name;
		this.id = String.valueOf(id_account++);
	}

	public HtmlTag(String name, String value) {
		this(name);
		this.value = value;
	}

	public void cleanAttributesAndchildren() {
		attributes = new HashMap<String, Object>();
		children = new LinkedList<HtmlTag>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setParent(HtmlTag parent) {
		this.parent = parent;
	}

	public HtmlTag getParent() {
		return parent;
	}

	public void setId(int id) {
		this.id = String.valueOf(id);
	}

	public void add(HtmlTag tag) {
		Collections.synchronizedList(children).add(tag);
		tag.setParent(this);
	}

	public void addAll(List<HtmlTag> tags) {
		for (HtmlTag tag : tags) {
			add(tag);
		}
	}

	public void removeChildren() {

		for (HtmlTag child : Collections.synchronizedList(children)) {
			Collections.synchronizedList(children).remove(child);
		}
	}

	public void add(String attrName, Object attrValue) {
		attributes.put(attrName, attrValue);
	}

	public void addAll(Map<String, Object> attrs) {
		attributes.putAll(attrs);
	}

	public List<HtmlTag> children() {
		return children;
	}

	public HtmlTag getChild(String child_id) {
		for (HtmlTag tag : children) {
			if (tag.getId().equals(child_id)) {
				return tag;
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String toAreaString() {
		if (parent == null) {
			this.add("style", "width:100%;height:100%");
		} else {
			if (attr("width") != null && attr("height") != null) {
				String widthPercent = "width:" + (Double) attr("width")
						/ (Double) getParent().attr("width") * 100 + "%;";
				String heightPercent = "height:" + (Double) attr("height")
						/ (Double) getParent().attr("height") * 100 + "%;";
				this.add("style", widthPercent + heightPercent);
			}
			// this.remove("width");
			// this.remove("height");
			// this.remove("area");
			// this.addAll(areaContent());
		}
		for (HtmlTag child : children) {
			child.toAreaString();
		}
		return this.toString();
	}

	List<HtmlTag> areaContent() {
		if (areaContent == null) {
			areaContent = new LinkedList<HtmlTag>();
			// switch media type
			areaContent.add(new HtmlTag("label", "SwitchMediaType"));
			HtmlTag select = new HtmlTag("select");
			select.add("name", "play_type");
			select.add("class", "switch_media_type");
			for (String key : mediaTypes.keySet()) {
				HtmlTag option = new HtmlTag("option", mediaTypes.get(key));
				option.add("value", key);
				select.add(option);
			}
			areaContent.add(select);

			HtmlTag button = new HtmlTag("button", "AddPlayList");
			button.add("class", "add_play_list_btn");
			areaContent.add(button);

			HtmlTag div = new HtmlTag("div");
			div.add("class", "play_list_tabs_container");
			HtmlTag ul = new HtmlTag("ul");
			ul.add("class", "play_list_tab");
			div.add(ul);

			areaContent.add(div);
		}

		return areaContent;
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<" + name);
		// attrs
		for (String attr : htmlAttributes) {
			if (attributes.containsKey(attr)) {
				sb.append(" " + attr + "='" + attributes.get(attr) + "'");
			}
		}
		sb.append(">");

		// text
		if (value != null) {
			sb.append(value);
		}
		// sub tag
		for (HtmlTag subTag : children) {
			sb.append(subTag.toHtml());
		}
		// for area
		if (attr("class") != null && attr("class").toString().contains("area")) {
			sb.append("<input type='hidden' name='area_id' value='" + id
					+ "'/>");
			for (HtmlTag exSubTag : areaContent()) {
				sb.append(exSubTag.toHtml());
			}
		}
		sb.append("</" + name + ">");

		return sb.toString();
	}

	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<" + name + ">");
		for (String attr : attributes.keySet()) {
			sb.append("<" + attr + ">");
			sb.append(attributes.get(attr));
			sb.append("</" + attr + ">");
		}
		for (HtmlTag child : children) {
			sb.append(child.toXml());
		}
		sb.append("</" + name + ">");
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<" + name);
		// attributes
		for (Entry<String, Object> en : attributes.entrySet()) {
			sb.append(" " + en.getKey() + "=\"" + en.getValue().toString()
					+ "\"");
		}
		sb.append(">");
		// text
		if (value != null) {
			sb.append(value);
		}

		sb.append("<input name='area_id' value='" + id + "' type='text'/>");

		// children
		for (HtmlTag tag : children) {
			sb.append(tag.toString());
		}
		sb.append("</" + name + ">");
		return sb.toString();
	}

	public Object attr(String attr) {
		return attributes.get(attr);
	}

	public Object remove(String attr) {
		return attributes.remove(attr);
	}

	public boolean isVertical() {
		boolean isV = false;
		for (HtmlTag child : children) {
			String childClass = child.attr("class").toString();
			isV = isV || childClass.contains("top");
		}
		return isV;
	}

	public List<HtmlTag> special4ScreenArea(double startX, double startY) {
		List<HtmlTag> areas = new LinkedList<HtmlTag>();
		if (this.attributes.containsKey("area")) {
			// if it is a area
			// this.cleanAttributesAndchildren();
			this.add("left_top_x", String.valueOf(startX));
			this.add("left_top_y", String.valueOf(startY));
			String endX = String.valueOf((Double) attr("width") + startX);
			String endY = String.valueOf((Double) attr("height") + startX);
			this.add("right_bottom_x", endX);
			this.add("right_bottom_y", endY);
			areas.add(this);
		} else {
			// if it is a area container
			double newStartX = startX;
			double newStartY = startY;
			boolean isV = isVertical();
			for (HtmlTag child : children) {
				areas.addAll(child.special4ScreenArea(newStartX, newStartY));
				if (isV) {
					newStartY = newStartY + (Double) child.attr("height");
				} else {
					newStartX = newStartX + (Double) child.attr("width");
				}
			}
		}
		return areas;
	}
}
