/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.entities.OrderState;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.OrderService;
import com.atroshonok.services.UserService;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class OrderCommand implements ActionCommand {
    private Logger log = Logger.getLogger(getClass());

    private static final String SESSION_ATTR_NAME_CART = "cart";
    private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
    private static final String SESSION_ATTR_NAME_USERID = "userID";
    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */

    // TODO
    @Override
    public String execute(HttpServletRequest request) {
	String page = null;
	try {
	    Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
	    if (cart.getAllProductsCount() > 0) {
		Order order = initOrder(request, cart);
		OrderService.getInstance().saveOrderData(order);
		cart = updateCart(cart, order);
		request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
	    } else {
		request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.ordererror.addproduct"));
		return page;
	    }
	} catch (ErrorSavingOrderServiceException e) {
	    log.error("Error creating order in class: " + OrderCommand.class, e);
	    request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.ordererror.tryagain"));
	}
	request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.orderadded"));
	page = ConfigurationManager.getProperty("path.page.order");
	return page;
    }

    private Cart updateCart(Cart cart, Order order) {
	List<Order> tempOrders = cart.getOrders();
	tempOrders.add(order);
	cart = new Cart();
	cart.setOrders(tempOrders);
	return cart;
    }

    private Order initOrder(HttpServletRequest request, Cart cart) {
	Order order = new Order();
	long userId = (Long) request.getSession().getAttribute(SESSION_ATTR_NAME_USERID);
	User user = UserService.getInstance().getUserById(userId);
	order.setUser(user);
	order.setSumPrice(cart.getSumPrice());
	order.setOrderState(OrderState.OPEN);
	order.setOrderLines(cart.getOrderLines());
	return order;
    }

}
