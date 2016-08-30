/**
 * 
 */
package com.atroshonok.dao.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;

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

    private List<OrderedProduct> orderedProducts;
    @OneToMany
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
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
	result = prime * result + ((orderedProducts == null) ? 0 : orderedProducts.hashCode());
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
	if (orderedProducts == null) {
	    if (other.orderedProducts != null)
		return false;
	} else if (!orderedProducts.equals(other.orderedProducts))
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
