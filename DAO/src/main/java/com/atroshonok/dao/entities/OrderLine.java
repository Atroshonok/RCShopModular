/**
 * 
 */
package com.atroshonok.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

/**
 * @author Ivan Atroshonok
 *
 */

@javax.persistence.Entity
@Table(name = "order_lines")
@Proxy(lazy = false)
public class OrderLine implements Serializable, Entity {
    
    private static final long serialVersionUID = 8812610388003827339L;
    
    private Long id;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    
    private Product product;
    @OneToOne
    @JoinColumn(name = "productID_FK", referencedColumnName = "productID")
    public Product getProduct() {
        return product;
    }
    
    private Integer count;
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }
   
    public OrderLine() {
	super();
    }

    @Override
    public String toString() {
	return "OrderLine [id=" + id + ", product=" + product + ", count=" + count + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((count == null) ? 0 : count.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((product == null) ? 0 : product.hashCode());
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
	OrderLine other = (OrderLine) obj;
	if (count == null) {
	    if (other.count != null)
		return false;
	} else if (!count.equals(other.count))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (product == null) {
	    if (other.product != null)
		return false;
	} else if (!product.equals(other.product))
	    return false;
	return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
