/**
 * 
 */
package com.atroshonok.command.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.entities.Cart;
import com.atroshonok.entities.Order;
import com.atroshonok.entities.OrderState;
import com.atroshonok.entities.UserType;
import com.atroshonok.services.OrderService;
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

	@Override
	public String execute(HttpServletRequest request) {
		UserType userType = (UserType) request.getSession().getAttribute(SESSION_ATTR_NAME_USERTYPE);
		String page = null;
		try {
			if (userType.equals(UserType.CLIENT) || userType.equals(UserType.ADMIN)) {
				Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
				if (cart.getAllProductsCount() > 0) {
//					User user = geUserBean(request);
					Order order = new Order();
					initOrderFields(request, order, cart);
					saveOrderToDatabase(order);
					cart.getOrders().add(order);
					request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
				} else {
					request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.ordererror.addproduct"));
					page = ConfigurationManager.getProperty("path.page.order");
					return page;
				}

			} else {
				request.setAttribute("errorAccessMessage", MessageManager.getProperty("message.accessdenied"));
				page = ConfigurationManager.getProperty("path.page.accessdenied");
			}
		} catch (ErrorSavingOrderServiceException e) {
			log.error("Error saving order to database: " + e);
			request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.ordererror.tryagain"));
			page = ConfigurationManager.getProperty("path.page.order");
		}
		request.setAttribute("orderInfoMessage", MessageManager.getProperty("message.orderadded"));
		page = ConfigurationManager.getProperty("path.page.order");
		return page;
	}

	private void initOrderFields(HttpServletRequest request, Order order, Cart cart) {
		long userID = (Long) request.getSession().getAttribute(SESSION_ATTR_NAME_USERID);
		order.setUserId(userID);
		order.setSumPrice(cart.getSumPrice());
		order.setState(OrderState.OPEN);
		order.setOrderedProducts(cart.getOrderedProducts());
		
	}

	private void saveOrderToDatabase(Order order) throws ErrorSavingOrderServiceException {
		OrderService orderService = new OrderService();
		orderService.saveOrderDataToDatabase(order);
	}

//	private void initOrderFields(Cart cart, User user, Order order) {
//		order.setUserId(user.getId());
//		order.setSumPrice(cart.getSumPrice());
//		order.setState(OrderState.OPEN);
//		order.setOrderedProducts(cart.getOrderedProducts());
//	}

//	private User geUserBean(HttpServletRequest request) {
//		UserService userService = new UserService();
//		String login = (String) request.getSession().getAttribute(SESSION_ATTR_NAME_USER);
//		User user = userService.getUserByLogin(login);
//		return user;
//	}

}
