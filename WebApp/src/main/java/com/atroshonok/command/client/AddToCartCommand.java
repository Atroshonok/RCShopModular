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
import com.atroshonok.dao.entities.OrderedProduct;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.ProductService;
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
	Product product = ProductService.getInstatnce().getProductById(productId);
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
	List<OrderedProduct> orderedProducts = cart.getOrderedProducts();
	OrderedProduct orderedProduct = getOrderLineByProduct(orderedProducts, product);
	
	if (orderedProduct != null) {
	    increaseProductCount(orderedProduct);
	} else {
	    addNewProductToList(product, orderedProducts);
	}
	cart.setSumPrice(cart.getSumPrice() + product.getPrice());
    }

    private OrderedProduct getOrderLineByProduct(List<OrderedProduct> orderedProducts, Product product) {
	for (OrderedProduct orderedProduct : orderedProducts) {
	    if (orderedProduct.getProduct().getId() == product.getId()) {
		return orderedProduct;
	    }
	}
	return null;
    }

    private void increaseProductCount(OrderedProduct orderedProduct) {
	int oldCount = orderedProduct.getCount();
	orderedProduct.setCount(oldCount + 1);
    }

    private void addNewProductToList(Product product, List<OrderedProduct> orderedProducts) {
	OrderedProduct newOrderedProduct = new OrderedProduct();
	newOrderedProduct.setCount(1);
	newOrderedProduct.setProduct(product);
	orderedProducts.add(newOrderedProduct);
    }
}
