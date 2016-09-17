/**
 * 
 */
package com.atroshonok.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.ProductServiceImpl;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class GetMainPageCommand implements ActionCommand {

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
	String page = ConfigurationManager.getProperty("path.page.main");
	return page;
    }

}
