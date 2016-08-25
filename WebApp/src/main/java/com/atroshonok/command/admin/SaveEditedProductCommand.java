/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.services.ProductService;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class SaveEditedProductCommand implements ActionCommand {
	private static final String REQUEST_PARAM_NAME_ID = "id";
	private static final String REQUEST_PARAM_NAME_NAME = "name";
	private static final String REQUEST_PARAM_NAME_PRICE = "price";
	private static final String REQUEST_PARAM_NAME_CATEGORYID = "categoryID";
	private static final String REQUEST_PARAM_NAME_COUNT = "count";
	private static final String REQUEST_PARAM_NAME_DESCRIPTION = "description";
	
	private Logger log = Logger.getLogger(getClass());

	/* (non-Javadoc)
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		Product product = createProductByRequestParam(request);
		ProductService productService = new ProductService();
		
		try {
			productService.updateProductInDatabase(product);
			request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.productupdated"));
		} catch (ErrorUpdatingPoductServiceException e) {
			log.error("Error updating product:" + e);
			request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.error.updateproduct"));
		}
		String page = ConfigurationManager.getProperty("path.page.admin");
		
		return page;
	}
	
	private Product createProductByRequestParam(HttpServletRequest request) {
		long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_ID));
		String name = request.getParameter(REQUEST_PARAM_NAME_NAME);
		double price = Double.parseDouble(request.getParameter(REQUEST_PARAM_NAME_PRICE));
		
		long categoryID = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_CATEGORYID));
		ProductCategory category = new ProductCategory(categoryID);
		
		int count = Integer.parseInt(request.getParameter(REQUEST_PARAM_NAME_COUNT));
		String description = request.getParameter(REQUEST_PARAM_NAME_DESCRIPTION);
		Product product = new Product(id, name, price, category, count, description);
		return product;
	}

}
