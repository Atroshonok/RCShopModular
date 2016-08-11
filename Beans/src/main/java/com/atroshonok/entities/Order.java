/**
 * 
 */
package com.atroshonok.entities;

import java.util.Map;

/**
 * @author Atroshonok Ivan
 *
 */
public class Order extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2178928344941296596L;

	private long userId;
	private double sumPrice;
	private OrderState state;
	private Map<Product, Integer> orderedProducts;

	/**
	 * 
	 */
	public Order() {
		super();
	}

	/**
	 * @param id
	 */
	public Order(long id) {
		super(id);
	}

	/**
	 * @param userId
	 * @param sumPrice
	 * @param state
	 * @param products
	 */
	public Order(long userId, double sumPrice, OrderState state) {
		super();
		this.userId = userId;
		this.sumPrice = sumPrice;
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [userId=" + userId + ", sumPrice=" + sumPrice + ", state=" + state + ", id=" + id + "]";
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((orderedProducts == null) ? 0 : orderedProducts.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		long temp;
		temp = Double.doubleToLongBits(sumPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderedProducts == null) {
			if (other.orderedProducts != null)
				return false;
		} else if (!orderedProducts.equals(other.orderedProducts))
			return false;
		if (state != other.state)
			return false;
		if (Double.doubleToLongBits(sumPrice) != Double.doubleToLongBits(other.sumPrice))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/**
	 * @return the sumPrice
	 */
	public double getSumPrice() {
		return sumPrice;
	}

	/**
	 * @param sumPrice
	 *            the sumPrice to set
	 */
	public void setSumPrice(double sumPrice) {
		this.sumPrice = sumPrice;
	}

	/**
	 * @return the state
	 */
	public OrderState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(OrderState state) {
		this.state = state;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the orderedProducts
	 */
	public Map<Product, Integer> getOrderedProducts() {
		return orderedProducts;
	}

	/**
	 * @param orderedProducts the orderedProducts to set
	 */
	public void setOrderedProducts(Map<Product, Integer> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

}
