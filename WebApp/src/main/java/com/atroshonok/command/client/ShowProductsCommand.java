/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.entities.Product;
import com.atroshonok.services.ProductService;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class ShowProductsCommand implements ActionCommand {
	
	private static final String REQUEST_PARAM_CATEGORYID = "categoryid";

	/* (non-Javadoc)
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		int categoryID = Integer.parseInt(request.getParameter(REQUEST_PARAM_CATEGORYID));
		ProductService productService = new ProductService();
		List<Product> products = productService.getProductsByCategoryID(categoryID);
		request.getSession().setAttribute("productsList", products);
		
		String page = ConfigurationManager.getProperty("path.page.products");
		return page;
	}

}
