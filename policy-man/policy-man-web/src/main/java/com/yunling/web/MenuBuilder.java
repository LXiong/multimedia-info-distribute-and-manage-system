package com.yunling.web;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.springframework.beans.factory.InitializingBean;
import org.xml.sax.SAXException;

public class MenuBuilder implements InitializingBean {

	private String resourceName;
	private MenuBar menuBar;
	
	/** Exposed factory method */
	public MenuBar buildMenuFromResource() {
		return menuBar;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		menuBar = buildMenu(getClass().getResourceAsStream(resourceName));
	}
	
	public MenuBar buildMenu(InputStream in) {
		if (in == null) {
			throw new IllegalArgumentException("The input stream is null");
		}
		
		Digester digester = buildDigester();
		try {
			MenuBar menubar = (MenuBar) digester.parse(in);
			if (menubar != null) {
				menubar.collectAll();
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
