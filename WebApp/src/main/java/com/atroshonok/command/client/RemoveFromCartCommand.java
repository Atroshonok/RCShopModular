/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderLine;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.ProductServiceImpl;
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
	Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
	long productId = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_PRODUCTID));
	Product product = ProductServiceImpl.getInstatnce().getProductById(productId);
	
	removeOrderedProduct(product, cart);
	decreaseAllProductsCount(cart);
	
	request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
	page = ConfigurationManager.getProperty("path.page.cart");
	return page;
    }


    private void removeOrderedProduct(Product product, Cart cart) {
	List<OrderLine> orderLines = cart.getOrderLines();
	OrderLine orderLine = getOrderLineByProduct(orderLines, product);
	if (orderLine != null) {
	    if (orderLine.getCount() > 1) {
		decreaseCount(orderLine);
	    } else {
		orderLines.remove(orderLine);
	    } 
	}
	cart.setSumPrice(cart.getSumPrice() - product.getPrice());
    }


    private OrderLine getOrderLineByProduct(List<OrderLine> orderLines, Product product) {
	for (OrderLine orderLine : orderLines) {
	    if (orderLine.getProduct().getId() == product.getId()) {
		return orderLine;
	    }
	}
	return null;
    }

    private void decreaseCount(OrderLine orderLine) {
	int oldCount = orderLine.getCount();
	orderLine.setCount(oldCount - 1);
    }

    private void decreaseAllProductsCount(Cart cart) {
	int allProductsCount = cart.getAllProductsCount();
	if (allProductsCount > 0) {
	    cart.setAllProductsCount(allProductsCount - 1);
	}
    }
}
