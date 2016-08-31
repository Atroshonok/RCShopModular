/**
 * 
 */
package com.atroshonok.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

/**
 * @author Atroshonok Ivan
 *
 */

@javax.persistence.Entity
@Table(name = "products")
@Proxy(lazy = false)
public class Product implements Serializable, Entity {

    private static final long serialVersionUID = 1029931528433287929L;

    private Long id;

    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
	return id;
    }

    private String name;

    @Column(name = "name")
    public String getName() {
	return name;
    }

    private Double price;

    @Column(name = "price", precision = 10, scale = 2)
    public Double getPrice() {
	return price;
    }

    private ProductCategory category;
    @OneToOne
    @Cascade(value = { CascadeType.SAVE_UPDATE })
    @JoinColumn(name = "categoryID")
    public ProductCategory getCategory() {
	return category;
    }

    private Integer count;

    @Column(name = "count")
    public Integer getCount() {
	return count;
    }

    private String description;

    @Column(name = "description")
    @Type(type = "text")
    public String getDescription() {
	return description;
    }

    public Product() {
	super();
    }

    public Product(String name) {
	super();
	this.name = name;
    }

    public Product(Long id, String name, Double price) {
	super();
	this.id = id;
	this.name = name;
	this.price = price;
    }

    
    public Product(String name, Double price, ProductCategory category, Integer count, String description) {
	super();
	this.name = name;
	this.price = price;
	this.category = category;
	this.count = count;
	this.description = description;
    }


    @Override
    public String toString() {
	return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((category == null) ? 0 : category.hashCode());
	result = prime * result + ((count == null) ? 0 : count.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
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
	Product other = (Product) obj;
	if (category == null) {
	    if (other.category != null)
		return false;
	} else if (!category.equals(other.category))
	    return false;
	if (count == null) {
	    if (other.count != null)
		return false;
	} else if (!count.equals(other.count))
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	return true;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public void setCategory(ProductCategory category) {
	this.category = category;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
