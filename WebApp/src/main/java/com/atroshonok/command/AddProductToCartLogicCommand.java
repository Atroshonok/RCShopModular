package com.atroshonok.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderLine;
import com.atroshonok.dao.entities.Product;

public class AddProductToCartLogicCommand {
    private static Logger log = Logger.getLogger(AddProductToCartLogicCommand.class);
    private static final String SESSION_ATTR_NAME_CART = "cart";

    public void execute(Product product, HttpServletRequest request) {
	Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
	addProductToCart(cart, product);
	cart.setAllProductsCount(cart.getAllProductsCount() + 1);
	request.getSession().setAttribute(SESSION_ATTR_NAME_CART, cart);
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
}
