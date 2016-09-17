/**
 * 
 */
package com.atroshonok.command.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.OrderLine;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.ProductServiceImpl;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class AddToCartCommand implements ActionCommand {
    private static Logger log = Logger.getLogger(AddToCartCommand.class);
    private static final String SESSION_ATTR_NAME_CART = "cart";
    private static final String REQUEST_PARAM_NAME_PRODUCTID = "productid";
    private static final String REQUEST_PARAM_NAME_PRODUCTNAME = "productname";
    private static final String REQUEST_PARAM_NAME_PRODUCTPRICE = "productprice";

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest) TODO реализовать учет товара на складе
     */
    @Override
    public String execute(HttpServletRequest request) {
	Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);

	long productId = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME_PRODUCTID));
	Product product = ProductServiceImpl.getInstatnce().getProductById(productId);
	//TODO Добавить проверку на null и вывод сообщения об отсутствии товара
	addProductToCart(cart, product);
	cart.setAllProductsCount(cart.getAllProductsCount() + 1);

	request.getSession().setAttribute("cart", cart);
	String page = ConfigurationManager.getProperty("path.page.products");
	request.setAttribute("productAddedMessage", MessageManager.getProperty("message.productadded"));
	log.debug("Got page address: " + page + " in class: " + AddToCartCommand.class);
	return page;
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
	newOrderLine.setCount(1);
	newOrderLine.setProduct(product);
	orderLines.add(newOrderLine);
    }
}
