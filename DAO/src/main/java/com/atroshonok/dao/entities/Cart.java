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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Cart [allProductsCount=" + allProductsCount + ", orderLines=" + orderLines + ", sumPrice=" + sumPrice + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + allProductsCount;
	result = prime * result + ((orderLines == null) ? 0 : orderLines.hashCode());
	result = prime * result + ((orders == null) ? 0 : orders.hashCode());
	result = prime * result + ((sumPrice == null) ? 0 : sumPrice.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Cart other = (Cart) obj;
	if (allProductsCount != other.allProductsCount)
	    return false;
	if (orderLines == null) {
	    if (other.orderLines != null)
		return false;
	} else if (!orderLines.equals(other.orderLines))
	    return false;
	if (orders == null) {
	    if (other.orders != null)
		return false;
	} else if (!orders.equals(other.orders))
	    return false;
	if (sumPrice == null) {
	    if (other.sumPrice != null)
		return false;
	} else if (!sumPrice.equals(other.sumPrice))
	    return false;
	return true;
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

    /**
     * @return the orderLines
     */
    public List<OrderLine> getOrderLines() {
	return orderLines;
    }

    /**
     * @param orderLines
     *            the orderLines to set
     */
    public void setOrderLines(List<OrderLine> orderLines) {
	this.orderLines = orderLines;
    }

    /**
     * @return the sumPrice
     */
    public Double getSumPrice() {
	return sumPrice;
    }

    /**
     * @param sumPrice
     *            the sumPrice to set
     */
    public void setSumPrice(Double sumPrice) {
	this.sumPrice = sumPrice;
    }

}
