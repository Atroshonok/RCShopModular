package com.atroshonok.dao.entities;

import java.io.Serializable;

public class ProductValueObject implements Serializable {

    private static final long serialVersionUID = -751155082317142262L;

    private Long id;
    private String name;
    private Double price;
    private Long ProductCategoryId;
    private Integer count;
    private String description;

    public ProductValueObject() {
	super();
    }

    public ProductValueObject(Product product) {
	this.id = product.getId();
	this.name = product.getName();
	this.price = product.getPrice();
	this.ProductCategoryId = product.getCategory().getId();
	this.count = product.getCount();
	this.description = product.getDescription();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ProductCategoryId == null) ? 0 : ProductCategoryId.hashCode());
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
	ProductValueObject other = (ProductValueObject) obj;
	if (ProductCategoryId == null) {
	    if (other.ProductCategoryId != null)
		return false;
	} else if (!ProductCategoryId.equals(other.ProductCategoryId))
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getProductCategoryId() {
        return ProductCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        ProductCategoryId = productCategoryId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
