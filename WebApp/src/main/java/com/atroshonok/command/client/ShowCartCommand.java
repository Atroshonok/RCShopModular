/**
 * 
 */
package com.atroshonok.command.client;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class ShowCartCommand implements ActionCommand {

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
	String page = ConfigurationManager.getProperty("path.page.cart");
	return page;
    }

}
