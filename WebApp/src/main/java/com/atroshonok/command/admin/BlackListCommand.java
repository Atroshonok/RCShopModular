/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.entities.User;
import com.atroshonok.services.UserService;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class BlackListCommand implements ActionCommand {

	private static final String PARAM_NAME_USERID = "userid";
	private static final String PARAM_NAME_ACTION = "action";
	private Logger log = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;
		
			long userID = Long.parseLong(request.getParameter(PARAM_NAME_USERID));
			String action = request.getParameter(PARAM_NAME_ACTION);

			UserService userService = new UserService();
			User user = userService.getUserByID(userID);

			setBlackListUserParameter(action, user);
			updateUserDataInDatabase(request, userService, user, action);

			page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}

	private void setBlackListUserParameter(String action, User user) {

		if (action.equals("add")) {
			user.setInBlackList(true);
		} else if (action.equals("remove")) {
			user.setInBlackList(false);
		}
	}

	private void updateUserDataInDatabase(HttpServletRequest request, UserService userService, User user,
			String action) {
		try {
			userService.updateUserData(user);
			setRequestInfoMessage(request, action);
		} catch (ErrorUpdatingUserServiceException e) {
			log.error("Error updating user:" + e);
			request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.error.addusertoblist"));
		}
	}

	private void setRequestInfoMessage(HttpServletRequest request, String action) {
		if (action.equals("add")) {
			request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.useraddedtoblist"));
		} else if (action.equals("remove")) {
			request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.userremovedfromblist"));
		}
	}

}
