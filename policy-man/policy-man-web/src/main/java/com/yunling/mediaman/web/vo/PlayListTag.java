package com.yunling.mediaman.web.vo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PlayListTag extends HtmlTag {

	static String[] outPutAttributes = { "begin_at", "end_at" };
	List<HtmlTag> medias = new LinkedList<HtmlTag>();

	public PlayListTag(String name) {
		super(name);
	}

	public PlayListTag(String name, String value) {
		super(name, value);
	}

	public void addMedia(String mediaName) {
		medias.add(new HtmlTag("media_name", mediaName));
	}

	public void addMedias(String[] mediaNames) {
		for (String mediaName : mediaNames) {
			addMedia(mediaName);
		}
	}

	public void addMedias(Set<String> mediaNames) {
		Iterator<String> it = mediaNames.iterator();
		while (it.hasNext()) {
			addMedia(it.next());
		}
	}

	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<" + name + ">");
		if ("set_time_media_list".equalsIgnoreCase(name)) {
			for (String attr : outPutAttributes) {
				sb.append("<" + attr + ">" + attributes.get(attr) + "</" + attr
						+ ">");
			}
		}
		for (HtmlTag media : medias) {
			sb.append("<media_name>" + media.value + "</media_name");
		}

		sb.append("</" + name + ">");

		return sb.toString();
	}

}
