/**
 * 
 */
package com.atroshonok.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.ProductService;
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
	List<Product> products = ProductService.getInstatnce().getAllProducts();
	request.getSession().setAttribute("productsList", products);
	String page = ConfigurationManager.getProperty("path.page.main");
	return page;
    }

}
