/**
 * 
 */
package com.atroshonok.command.client;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class AddToCartCommand implements ActionCommand {
	private static final String SESSION_ATTR_NAME_CART = "cart";
	private static final String REQUEST_PARAM_NAME_PRODUCTID = "productid";
	private static final String REQUEST_PARAM_NAME_PRODUCTNAME = "productname";
	private static final String REQUEST_PARAM_NAME_PRODUCTPRICE = "productprice";
	private Logger log = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);

		Product product = createProductBeanByRequestParam(request);
		Map<Product, Integer> orderedProducts = cart.getOrderedProducts();
		int allProductsCount = cart.getAllProductsCount();

//		cart.getProductsList().add(product);
		
		if (orderedProducts.containsKey(product)) {
			int count = orderedProducts.get(product) + 1;
			cart.getOrderedProducts().replace(product, count);
			allProductsCount++;
		} else {
			cart.getOrderedProducts().put(product, 1);
			allProductsCount++;
		}
		cart.setAllProductsCount(allProductsCount);

		request.getSession().setAttribute("cart", cart);
		String page = ConfigurationManager.getProperty("path.page.products");
		request.setAttribute("productAddedMessage", MessageManager.getProperty("message.productadded"));
		// String page1 = request.;
		log.debug("Got page address: " + page);

		return page;
	}

	private Product createProductBeanByRequestParam(HttpServletRequest request) {
		long productID = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_PRODUCTID));
		String productName = request.getParameter(REQUEST_PARAM_NAME_PRODUCTNAME);
		Double productPrice = Double.parseDouble(request.getParameter(REQUEST_PARAM_NAME_PRODUCTPRICE));
		Product product = new Product(productID, productName, productPrice);
		return product;
	}

}
