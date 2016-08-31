/**
 * 
 */
package com.atroshonok.dao.entities;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

/**
 * @author Atroshonok Ivan
 *
 */

@javax.persistence.Entity
@Table(name = "product_categories")
@Proxy(lazy = false)
public class ProductCategory implements Entity {

    private static final long serialVersionUID = 2438212583980527672L;

    private Long id;
    @Id
    @Column(name = "categoryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
	return id;
    }

    private String categoryName;
    @Column(name = "categoryName")
    public String getCategoryName() {
	return categoryName;
    }

    public ProductCategory() {
    }
    
    public ProductCategory(String categoryName) {
	super();
	this.categoryName = categoryName;
    }

    
    public ProductCategory(Long id) {
	super();
	this.id = id;
    }


    @Override
    public String toString() {
	return "ProductCategory [id=" + id + ", categoryName=" + categoryName + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	ProductCategory other = (ProductCategory) obj;
	if (categoryName == null) {
	    if (other.categoryName != null)
		return false;
	} else if (!categoryName.equals(other.categoryName))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

}
