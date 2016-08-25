/**
 * 
 */
package com.atroshonok.command.admin;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.ProductService;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class EditProductCommand implements ActionCommand {

	private static final String PARAM_NAME_PRODUCTID = "productid";

	/* (non-Javadoc)
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		
		ProductService productService = new ProductService();
		long productID = Long.parseLong(request.getParameter(PARAM_NAME_PRODUCTID));
		Product product = productService.getProductByID(productID);
		
		request.setAttribute("product", product);
		request.setAttribute("fragmentPath", ConfigurationManager.getProperty("path.fragment.editproductform"));
		
		String page = ConfigurationManager.getProperty("path.page.admin"); 
		
		return page;
	}
	
	String page = null;
	

}
