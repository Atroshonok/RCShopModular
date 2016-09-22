package com.atroshonok.command;

import java.util.List;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderLine;
import com.atroshonok.dao.entities.Product;

public class RemoveProductFromCartLogicCommand {

    public void execute(Product product, Cart cart) {
	removeOrderedProduct(product, cart);
	decreaseAllProductsCount(cart);
	Integer productCount = product.getCount();
	product.setCount(productCount + 1);
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
