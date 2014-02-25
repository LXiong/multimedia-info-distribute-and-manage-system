package com.yunling.mediacenter.web.menu;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class MenuBuilder {

	private String resourceName;
	
	public MenuBar buildMenuFromResource() {
		return buildMenu(getClass().getResourceAsStream(resourceName));
	}
	
	public MenuBar buildMenu(InputStream in) {
		if (in == null) {
			throw new IllegalArgumentException("The input stream is null");
		}
		
		Digester digester = buildDigester();
		try {
			MenuBar menubar = (MenuBar) digester.parse(in);
			if (menubar!=null) {
				menubar.buildMap();
			}
			return menubar;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected Digester buildDigester() {
		Digester digester = new Digester();
		
		digester.addObjectCreate("menus", MenuBar.class);
		digester.addSetProperties("menus");
		digester.addObjectCreate("*/menu", Menu.class);
		digester.addSetProperties("*/menu");
		digester.addSetNext("*/menu", "addMenu" );
		
		return digester;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
