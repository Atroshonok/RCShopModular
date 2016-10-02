package com.atroshonok.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderLine;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.IProductService;

/**
 * 
 * @author Ivan Atroshonok
 *
 */
@Controller
public class CartController {

    private static final String REQUEST_ATTR_INFO_MESSAGE = "infoMessage";
    private static final String SESSION_ATTR_CART = "cart";

    @Autowired
    private IProductService productService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCartPage(Model model) {
	return "cart";
    }

    /**
     * 
     * @param productId
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping(path = "/cart/add/{productId}", method = RequestMethod.GET)
    public String addProductToCart(@PathVariable("productId") Long productId, HttpServletRequest request, Locale locale) {
	Product product = productService.getProductById(productId);

	Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_CART);
	addProductToCart(cart, product);
	cart.setAllProductsCount(cart.getAllProductsCount() + 1);
	request.getSession().setAttribute(SESSION_ATTR_CART, cart);

	request.setAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.productadded", locale));
	return "products";
    }

    /**
     * 
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(path = "/cart/remove/{productId}", method = RequestMethod.GET)
    public String removeProductFromCart(@PathVariable("productId") Long productId, HttpSession session) {
	Product product = productService.getProductById(productId);
	Cart cart = (Cart) session.getAttribute(SESSION_ATTR_CART);
		// what if allProductsCount was 0 or negative?
		// this would be clearly error and it should be processed
	if ((product != null) && (cart.getAllProductsCount() > 0)) {

	    removeOrderedProduct(product, cart);
	    decreaseAllProductsCount(cart);
	    Integer productCount = product.getCount();
	    product.setCount(productCount + 1);

	    session.setAttribute(SESSION_ATTR_CART, cart);
	}
	return "cart";
    }

    private void addProductToCart(Cart cart, Product product) {
	List<OrderLine> orderLines = cart.getOrderLines();
	OrderLine orderLine = getOrderLineByProduct(orderLines, product);

	if (orderLine != null) {
	    increaseProductCount(orderLine);
	} else {
	    addNewProductToOrderLines(product, orderLines);
	}
	cart.setSumPrice(cart.getSumPrice() + product.getPrice());
    }

    private OrderLine getOrderLineByProduct(List<OrderLine> orderLines, Product product) {
	for (OrderLine orderLine : orderLines) {
	    if (orderLine.getProduct().getId() == product.getId()) {
		return orderLine;
	    }
	}
	return null;
    }

    private void increaseProductCount(OrderLine orderLine) {
	int oldCount = orderLine.getCount();
	orderLine.setCount(oldCount + 1);
    }

    private void addNewProductToOrderLines(Product product, List<OrderLine> orderLines) {
	OrderLine newOrderLine = new OrderLine();
	Integer productCountInOrderLine = 1;
	newOrderLine.setCount(productCountInOrderLine);
	newOrderLine.setProduct(product);
	orderLines.add(newOrderLine);
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

    private void decreaseCount(OrderLine orderLine) {
	int oldCount = orderLine.getCount();
	orderLine.setCount(oldCount - 1);
    }

    private void decreaseAllProductsCount(Cart cart) {
	int allProductsCount = cart.getAllProductsCount();
	if (allProductsCount > 0) {
	    cart.setAllProductsCount(allProductsCount - 1);
	}
		// what if allProductsCount was 0 or negative?
		// this would be clearly error and it should be processed
    }

    // Returns a message by key using a locale
    private String getMessageByKey(String key, Locale locale) {
	return messageSource.getMessage(key, null, locale);
    }

}
