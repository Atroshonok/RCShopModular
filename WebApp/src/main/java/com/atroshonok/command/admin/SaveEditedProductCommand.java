/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.services.ProductCategoryService;
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

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
	Product product = changeProductAccordingRequestParam(request);

	try {
	    ProductService.getInstatnce().updateProduct(product);
	    request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.productupdated"));
	} catch (ErrorUpdatingPoductServiceException e) {
	    log.error("Error saving updated product in class:" + SaveEditedProductCommand.class,e);
	    request.setAttribute("adminInfoMessage", MessageManager.getProperty("message.error.updateproduct"));
	}
	String page = ConfigurationManager.getProperty("path.page.admin");

	return page;
    }

    private Product changeProductAccordingRequestParam(HttpServletRequest request) {
	long productId = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_ID));
//	Product product = (Product) request.getAttribute("product");
	Product product = ProductService.getInstatnce().getProductById(productId);
	product.setName(request.getParameter(REQUEST_PARAM_NAME_NAME));
	product.setPrice(Double.parseDouble(request.getParameter(REQUEST_PARAM_NAME_PRICE)));
	
	long categoryId = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_CATEGORYID));
	
	ProductCategory category = ProductCategoryService.getInstance().getCategoryById(categoryId);
	product.setCategory(category);
	product.setCount(Integer.parseInt(request.getParameter(REQUEST_PARAM_NAME_COUNT)));
	product.setDescription(request.getParameter(REQUEST_PARAM_NAME_DESCRIPTION));
	return product;
    }

}
