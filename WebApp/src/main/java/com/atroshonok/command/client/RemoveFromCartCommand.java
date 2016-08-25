/**
 * 
 */
package com.atroshonok.command.client;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class RemoveFromCartCommand implements ActionCommand {

	private static final String SESSION_ATTR_NAME_CART = "cart";
	private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
	private static final String REQUEST_PARAM_NAME_PRODUCTID = "productid";
	private static final String REQUEST_PARAM_NAME_PRODUCTNAME = "productname";
	private static final String REQUEST_PARAM_NAME_PRODUCTPRICE = "productprice";

	/*
	 * (non-Javadoc)
	 * 
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		UserType userType = (UserType) request.getSession().getAttribute(SESSION_ATTR_NAME_USERTYPE);
		if (userType.equals(UserType.CLIENT) || userType.equals(UserType.ADMIN)) {
			Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
			Product product = createProductBeanByRequestParam(request);
			decreaseCountOrderedProduct(product, cart);
			request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
//			request.setAttribute("orderedProductsMap", cart.getOrderedProducts());
			page = ConfigurationManager.getProperty("path.page.cart");
		} else {
			request.setAttribute("errorAccessMessage", ConfigurationManager.getProperty("message.accessdenied"));
			page = ConfigurationManager.getProperty("path.page.accessdenied");
		}
		return page;
	}

	private void decreaseAllProductsCount(Cart cart) {
		int allProductsCount = cart.getAllProductsCount();
		if (allProductsCount > 0) {
			cart.setAllProductsCount(allProductsCount - 1);
		}

	}

	private void decreaseCountOrderedProduct(Product product, Cart cart) {
		Map<Product, Integer> orderedProducts = cart.getOrderedProducts();
		int count = orderedProducts.get(product) - 1;
		if (count >= 1) {
			orderedProducts.replace(product, count);
		} else {
			orderedProducts.remove(product);
		}
		cart.setOrderedProducts(orderedProducts);
		decreaseAllProductsCount(cart);
	}

	private Product createProductBeanByRequestParam(HttpServletRequest request) {
		long productID = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_PRODUCTID));
		String productName = request.getParameter(REQUEST_PARAM_NAME_PRODUCTNAME);
		Double productPrice = Double.parseDouble(request.getParameter(REQUEST_PARAM_NAME_PRODUCTPRICE));
		Product product = new Product(productID, productName, productPrice);
		return product;

	}
}
