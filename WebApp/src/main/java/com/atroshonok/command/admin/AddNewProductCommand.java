/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class AddNewProductCommand implements ActionCommand {

	/* (non-Javadoc)
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		
		request.setAttribute("fragmentPath", ConfigurationManager.getProperty("path.fragment.addproductform"));
		String page = ConfigurationManager.getProperty("path.page.admin"); 
		
		return page;
		
	}

}
