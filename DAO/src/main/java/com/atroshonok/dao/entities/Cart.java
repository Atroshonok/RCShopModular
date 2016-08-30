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

    private int allProductsCount;
    private List<Order> orders;
    private List<OrderedProduct> orderedProducts;
    private Double sumPrice = 0.0;

    public Cart() {
	this.orders = new ArrayList<>();
	this.orderedProducts = new ArrayList<>();
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

    public List<OrderedProduct> getOrderedProducts() {
	return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
	this.orderedProducts = orderedProducts;
    }

    public Double getSumPrice() {
	return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
	this.sumPrice = sumPrice;
    }

}
