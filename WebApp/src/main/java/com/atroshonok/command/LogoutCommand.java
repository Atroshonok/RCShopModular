package com.atroshonok.command;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.utilits.ConfigurationManager;

public class LogoutCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.main");
		request.getSession().invalidate();
		
		return page;
	}
}