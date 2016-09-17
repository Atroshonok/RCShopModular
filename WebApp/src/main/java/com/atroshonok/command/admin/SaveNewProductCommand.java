/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.services.ProductServiceImpl;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class SaveNewProductCommand implements ActionCommand {

    private static final String REQUEST_PARAM_NAME_NAME = "name";
    private static final String REQUEST_PARAM_NAME_PRICE = "price";
    private static final String REQUEST_PARAM_NAME_CATEGORYID = "categoryID";
    private static final String REQUEST_PARAM_NAME_COUNT = "count";
    private static final String REQUEST_PARAM_NAME_DESCRIPTION = "description";

    private Logger log = Logger.getLogger(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {

	Product product = createProductByRequestParam(request);
	try {
	    ProductServiceImpl.getInstatnce().addNewProductToDatabase(product);
	    request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.productadded"));
	} catch (ErrorAddingPoductServiceException e) {
	    log.error("Error saving new product in class: " + SaveNewProductCommand.class, e);
	    request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.error.addnewproduct"));
	}
	String page = ConfigurationManager.getProperty("path.page.admin");

	return page;
    }

    private Product createProductByRequestParam(HttpServletRequest request) {
	String name = request.getParameter(REQUEST_PARAM_NAME_NAME);
	double price = Double.parseDouble(request.getParameter(REQUEST_PARAM_NAME_PRICE));

	long categoryId = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_CATEGORYID));
	ProductCategory category = new ProductCategory(categoryId);

	int count = Integer.parseInt(request.getParameter(REQUEST_PARAM_NAME_COUNT));
	String description = request.getParameter(REQUEST_PARAM_NAME_DESCRIPTION);
	Product product = new Product(name, price, category, count, description);
	return product;
    }
}
