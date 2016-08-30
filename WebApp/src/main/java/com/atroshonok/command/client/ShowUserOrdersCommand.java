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
	long userId = (Long) request.getSession().getAttribute(SESSION_ATTR_NAME_USERID);
	List<Order> orders = OrderService.getInstance().getAllUserOrders(userId);

	if (!orders.isEmpty() && (orders != null)) {
	    request.setAttribute("orders", orders);
	} else {
	    request.setAttribute("noOrdersMessage", MessageManager.getProperty("message.noorders"));
	}

	return ConfigurationManager.getProperty("path.page.orders");
    }

}
