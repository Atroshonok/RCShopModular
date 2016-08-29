/**
 * 
 */
package com.atroshonok.dao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Atroshonok Ivan
 *
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = -8683532932268654018L;

    private Map<Product, Integer> orderedProducts;
    private int allProductsCount;
    private List<Order> orders;

    public Cart() {
	this.orderedProducts = new HashMap<>();
	this.orders = new ArrayList<>();
    }

    /**
     * @return the orderedProducts
     */
    public Map<Product, Integer> getOrderedProducts() {
	return orderedProducts;
    }

    /**
     * @param orderedProducts
     *            the orderedProducts to set
     */
    public void setOrderedProducts(Map<Product, Integer> orderedProducts) {
	this.orderedProducts = orderedProducts;
    }

    /**
     * @return the allProductsCount
     */
    public int getAllProductsCount() {
	return allProductsCount;
    }

    /**
     * @param allProductsCount
     *            the allProductsCount to set
     */
    public void setAllProductsCount(int allProductsCount) {
	this.allProductsCount = allProductsCount;
    }

    /**
     * @return the sumPrice
     */
    public double getSumPrice() {
	double sumPrice = 0.0;
	for (Map.Entry<Product, Integer> pair : orderedProducts.entrySet()) {
	    sumPrice = sumPrice + pair.getKey().getPrice() * pair.getValue();
	}
	return sumPrice;
    }

    /**
     * @return the orders
     */
    public List<Order> getOrders() {
	return orders;
    }

    /**
     * @param orders
     *            the orders to set
     */
    public void setOrders(List<Order> orders) {
	this.orders = orders;
    }

}
