package com.yunling.mediacenter.web.actions;

import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;
import com.yunling.mediacenter.web.menu.MenuBar;

public class MainMenuAction 
	extends AbstractUserAwareAction 
{
	private String currentAction;
	private MenuBar mainMenu;
	
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	public MenuBar getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuBar mainMenu) {
		this.mainMenu = mainMenu;
	}
}
