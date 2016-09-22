/**
 * 
 */
package com.atroshonok.dao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Atroshonok Ivan
 *
 */

public class Cart implements Serializable {
    private static final long serialVersionUID = -8683532932268654018L;

    private int allProductsCount;
    private List<Order> orders;
    private List<OrderLine> orderLines;
    private Double sumPrice = 0.0;

    public Cart() {
	this.orders = new ArrayList<>();
	this.orderLines = new ArrayList<>();
    }

    public int getAllProductsCount() {
	return allProductsCount;
    }

    public void setAllProductsCount(int allProductsCount) {
	this.allProductsCount = allProductsCount;
    }

    public List<Order> getOrders() {
	return orders;
    }

    public void setOrders(List<Order> orders) {
	this.orders = orders;
    }

    public List<OrderLine> getOrderLines() {
	return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
	this.orderLines = orderLines;
    }

    public Double getSumPrice() {
	return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
	this.sumPrice = sumPrice;
    }

}
