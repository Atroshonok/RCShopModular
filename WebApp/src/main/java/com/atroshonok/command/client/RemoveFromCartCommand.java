/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderedProduct;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.ProductService;
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
	Product product = ProductService.getInstatnce().getProductById(productId);
	
	removeOrderedProduct(product, cart);
	decreaseAllProductsCount(cart);
	
	request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
	page = ConfigurationManager.getProperty("path.page.cart");
	return page;
    }


    private void removeOrderedProduct(Product product, Cart cart) {
	List<OrderedProduct> orderedProducts = cart.getOrderedProducts();
	OrderedProduct orderedProduct = getOrderedProductByProduct(orderedProducts, product);
	if (orderedProduct != null) {
	    if (orderedProduct.getCount() > 1) {
		decreaseCount(orderedProduct);
	    } else {
		orderedProducts.remove(orderedProduct);
	    } 
	}
	cart.setSumPrice(cart.getSumPrice() - product.getPrice());
    }


    private OrderedProduct getOrderedProductByProduct(List<OrderedProduct> orderedProducts, Product product) {
	for (OrderedProduct orderedProduct : orderedProducts) {
	    if (orderedProduct.getProduct().getId() == product.getId()) {
		return orderedProduct;
	    }
	}
	return null;
    }

    private void decreaseCount(OrderedProduct orderedProduct) {
	int oldCount = orderedProduct.getCount();
	orderedProduct.setCount(oldCount - 1);
    }

    private void decreaseAllProductsCount(Cart cart) {
	int allProductsCount = cart.getAllProductsCount();
	if (allProductsCount > 0) {
	    cart.setAllProductsCount(allProductsCount - 1);
	}
    }
}
