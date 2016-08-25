/**
 * 
 */
package com.atroshonok.dao.entities;

/**
 * @author Atroshonok Ivan
 *
 */
public class Product extends Entity {

	private static final long serialVersionUID = 1029931528433287929L;
	private String name;
	private double price;
	private ProductCategory category;
	private int count;
	private String description;

	public Product() {
		super();
	}

	/**
	 * @param id
	 */
	public Product(long id) {
		super(id);
	}

	/**
	 * @param name
	 * @param price
	 */
	public Product(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

	/**
	 * @param id
	 * @param name
	 * @param price
	 */
	public Product(long id, String name, double price) {
		super(id);
		this.name = name;
		this.price = price;
	}

	/**
	 * @param id
	 * @param name
	 * @param price
	 * @param category
	 */
	public Product(long id, String name, double price, ProductCategory category) {
		super(id);
		this.name = name;
		this.price = price;
		this.category = category;
	}

	/**
	 * @param name
	 * @param price
	 * @param category
	 * @param count
	 * @param description
	 */
	public Product(String name, double price, ProductCategory category, int count, String description) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		this.count = count;
		this.description = description;
	}


	public Product(String name, double price, ProductCategory category, String description) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
	}

	/**
	 * @param id
	 * @param name
	 * @param price
	 * @param category
	 * @param description
	 */
	public Product(long id, String name, double price, ProductCategory category, String description) {
		super(id);
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
	}
	
	
	
	/**
	 * @param id
	 * @param name
	 * @param price
	 * @param category
	 * @param count
	 * @param description
	 */
	public Product(long id, String name, double price, ProductCategory category, int count, String description) {
		super(id);
		this.name = name;
		this.price = price;
		this.category = category;
		this.count = count;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category.getName()
				+ ", count=" + count + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + count;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (count != other.count)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the category
	 */
	public ProductCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
