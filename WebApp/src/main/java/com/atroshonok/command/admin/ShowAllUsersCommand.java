/**
 * 
 */
package com.atroshonok.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.User;
import com.atroshonok.services.UserServiceImpl;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class ShowAllUsersCommand implements ActionCommand {

	/* (non-Javadoc)
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.HttpServletRequest)
	 */

	@Override
	public String execute(HttpServletRequest request) {
		
		List<User> users = UserServiceImpl.getInstance().getAllUsers();
		
		request.setAttribute("usersList", users);
		request.setAttribute("fragmentPath", ConfigurationManager.getProperty("path.fragment.allusers"));

		String page = ConfigurationManager.getProperty("path.page.admin"); 
		
		return page;
	}

}
