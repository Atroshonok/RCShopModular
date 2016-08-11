/**
 * 
 */
package com.atroshonok.command;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class SetLanguageCommand implements ActionCommand {
	private static final String PARAM_NAME_LANGUAGE = "language";
	private static final String SESSION_ATTR_NAME_LOCALE = "locale";

	/*
	 * (non-Javadoc)
	 * 
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		String lang = request.getParameter(PARAM_NAME_LANGUAGE);
		setSessionLocale(request, lang);
		String page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}

	private void setSessionLocale(HttpServletRequest request, String lang) {
		if (lang.equals("ru")) {
			request.getSession().setAttribute(SESSION_ATTR_NAME_LOCALE, "ru_RU");
		} else if (lang.equals("en")) {
			request.getSession().setAttribute(SESSION_ATTR_NAME_LOCALE, "en_US");
		}
	}

}
