package com.atroshonok.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.UserService;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.DataEncryptor;
import com.atroshonok.utilits.MessageManager;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String SESSION_ATTR_NAME_USERID = "userID";
	private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
	private static final String SESSION_ATTR_NAME_USERLOGIN = "userLogin";
	private static final String SESSION_ATTR_NAME_CART = "cart";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = DataEncryptor.getPasswordHashCode(request.getParameter(PARAM_NAME_PASSWORD));
		
		UserService userService = new UserService();
		User user = userService.getUserByLoginPassword(login, pass);
		HttpSession session = request.getSession(true);
		
		if ((user != null) && user.getUserType().equals(UserType.ADMIN)) {
			setAdminSessionAttributs(user, session);
			page = ConfigurationManager.getProperty("path.page.admin");
		} else if (( user != null) && user.getUserType().equals(UserType.CLIENT)) {
			setClientSessionAttribute(login, user, session);
			page = ConfigurationManager.getProperty("path.page.products");
		} else {
			request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
			session.setAttribute(SESSION_ATTR_NAME_USERTYPE, UserType.GUEST);
			page = ConfigurationManager.getProperty("path.page.main");
		}
		return page;
	}

	private void setClientSessionAttribute(String login, User user, HttpSession session) {
		session.setAttribute(SESSION_ATTR_NAME_USERID, user.getId());
		session.setAttribute(SESSION_ATTR_NAME_USERTYPE, UserType.CLIENT);
		session.setAttribute(SESSION_ATTR_NAME_USERLOGIN, login);
		session.setAttribute(SESSION_ATTR_NAME_CART, new Cart());
	}

	private void setAdminSessionAttributs(User user, HttpSession session) {
		session.setAttribute(SESSION_ATTR_NAME_USERID, user.getId());
		session.setAttribute(SESSION_ATTR_NAME_USERTYPE, UserType.ADMIN);
		session.setAttribute(SESSION_ATTR_NAME_USERLOGIN, user.getLogin());
	}
}
