/**
 * 
 */
package com.atroshonok.dao.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import com.atroshonok.dao.enums.OrderState;

/**
 * @author Atroshonok Ivan
 *
 */

@javax.persistence.Entity
@Table(name = "orders")
@Proxy(lazy = false)
public class Order implements Entity {

    private static final long serialVersionUID = 2178928344941296596L;

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    public Long getId() {
	return id;
    }

    private User user;

    @OneToOne
    @JoinColumn(name = "userID_FK", referencedColumnName = "userID")
    public User getUser() {
	return user;
    }

    private Double sumPrice;

    @Column(name = "sumPrice", precision = 10, scale = 2)
    public Double getSumPrice() {
	return sumPrice;
    }

    private OrderState orderState;

    @Column(name = "orderState", columnDefinition = "enum('PROCESSING','PROCESSED','OPEN')")
    @Enumerated(EnumType.STRING)
    public OrderState getOrderState() {
	return orderState;
    }

    private List<OrderLine> orderLines;

    @OneToMany
    @JoinTable(name = "orders_orderlines", joinColumns = @JoinColumn(name = "orderID_FK", referencedColumnName = "orderID"), inverseJoinColumns = @JoinColumn(name = "orderLineID_FK", referencedColumnName = "OrderLineID"))
    @Cascade(value = { CascadeType.SAVE_UPDATE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<OrderLine> getOrderLines() {
	return orderLines;
    }

    public Order() {
	super();
    }

    @Override
    public String toString() {
	return "Order [orderId=" + id + ", userId=" + user.getId() + ", sumPrice=" + sumPrice + ", orderState=" + orderState + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((orderLines == null) ? 0 : orderLines.hashCode());
	result = prime * result + ((sumPrice == null) ? 0 : sumPrice.hashCode());
	result = prime * result + ((user == null) ? 0 : user.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Order other = (Order) obj;
	if (id != other.id)
	    return false;
	if (orderLines == null) {
	    if (other.orderLines != null)
		return false;
	} else if (!orderLines.equals(other.orderLines))
	    return false;
	if (sumPrice == null) {
	    if (other.sumPrice != null)
		return false;
	} else if (!sumPrice.equals(other.sumPrice))
	    return false;
	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;
	return true;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.user = user;
    }

    /**
     * @param sumPrice
     *            the sumPrice to set
     */
    public void setSumPrice(Double sumPrice) {
	this.sumPrice = sumPrice;
    }

    /**
     * @param orderState
     *            the orderState to set
     */
    public void setOrderState(OrderState orderState) {
	this.orderState = orderState;
    }

    /**
     * @param orderLines
     *            the orderLines to set
     */
    public void setOrderLines(List<OrderLine> orderLines) {
	this.orderLines = orderLines;
    }

}
