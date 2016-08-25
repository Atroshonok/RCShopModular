/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.services.OrderService;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class ShowUserOrdersCommand implements ActionCommand {

	private static final String SESSION_ATTR_NAME_USERID = "userID";
	private Logger log = Logger.getLogger(getClass());
	/*
	 * (non-Javadoc)
	 * 
	 * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	public String execute(HttpServletRequest request) {
		long userID = (Long) request.getSession().getAttribute(SESSION_ATTR_NAME_USERID);
		log.debug("UserID = " + userID);
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getAllUserOrders(userID);
		
		if (!orders.isEmpty() && (orders !=null)) {
			request.setAttribute("orders", orders);
		} else {
			request.setAttribute("noOrdersMassage", MessageManager.getProperty("message.noorders"));
		}
		return ConfigurationManager.getProperty("path.page.orders");
	}

}
