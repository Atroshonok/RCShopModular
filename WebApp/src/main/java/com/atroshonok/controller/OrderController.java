package com.atroshonok.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.entities.OrderState;
import com.atroshonok.dao.entities.User;
import com.atroshonok.services.IOrderService;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

@Controller
public class OrderController {

    private static final String REQUEST_ATTR_NAME_MESSAGE = "orderInfoMessage";

    private static final String SESSION_ATTR_NAME_USER = "user";

    private static final String SESSION_ATTR_NAME_CART = "cart";

    private static Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CookieLocaleResolver localeResolver;

//    @RequestMapping(method = RequestMethod.GET)
//    public String getUserOrdersPage() {
//	return "redirect:orders";
//    }

    @RequestMapping(path = "/orders/all/{userId}", method = RequestMethod.GET)
    public String showUserOrders(@PathVariable("userId") Integer userId, Model model, HttpServletRequest request) {
	List<Order> orders = orderService.getAllUserOrders(userId);
	if (!orders.isEmpty() && (orders != null)) {
	    model.addAttribute("orders", orders);
	} else {
	    Locale userLocale = localeResolver.resolveLocale(request);
	    model.addAttribute("ordersInfoMessage", messageSource.getMessage("message.noorders", null, userLocale));
	}
	return "orders";
    }

    @RequestMapping(path = "/orders/add", method = RequestMethod.POST)
    public String addNewOrder(HttpServletRequest request, HttpSession session) {
	Locale requestLocale = localeResolver.resolveLocale(request);
	try {
	    Cart cart = (Cart) session.getAttribute(SESSION_ATTR_NAME_CART);
	    if (cart.getAllProductsCount() > 0) {
		Order order = initNewOrder(session, cart);
		orderService.saveOrderData(order);
		cart = updateSessionCart(cart, order);
		session.setAttribute(SESSION_ATTR_NAME_CART, cart);
		request.setAttribute(REQUEST_ATTR_NAME_MESSAGE, messageSource.getMessage("message.orderadded", null, requestLocale));
	    } else {
		request.setAttribute(REQUEST_ATTR_NAME_MESSAGE, messageSource.getMessage("message.ordererror.addproduct", null, requestLocale));
	    }
	} catch (ErrorSavingOrderServiceException e) {
	    log.error("Error creating order: ", e);
	    request.setAttribute(REQUEST_ATTR_NAME_MESSAGE, messageSource.getMessage("message.ordererror.tryagain", null, requestLocale));
	}
	return "cart";
    }

    private Order initNewOrder(HttpSession session, Cart cart) {
	Order order = new Order();
	User user = (User) session.getAttribute(SESSION_ATTR_NAME_USER);
	if (user != null) {
	    order.setUser(user);
	    order.setSumPrice(cart.getSumPrice());
	    order.setOrderState(OrderState.OPEN);
	    order.setOrderLines(cart.getOrderLines());
	}
	return order;
    }

    private Cart updateSessionCart(Cart cart, Order order) {
	List<Order> tempOrders = cart.getOrders();
	tempOrders.add(order);
	cart = new Cart();
	cart.setOrders(tempOrders);
	return cart;
    }
}
