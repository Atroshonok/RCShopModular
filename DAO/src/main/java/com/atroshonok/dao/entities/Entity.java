/**
 * 
 */
package com.atroshonok.dao.entities;

import java.io.Serializable;

/**
 * @author Atroshonok Ivan
 *
 */
public abstract class Entity implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4188849716037126912L;
	/**
	 * 
	 */
	protected long id;

	/**
	 * 
	 */
	public Entity() {
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}



	/* (non-Javadoc)
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
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}



	/**
	 * @param id
	 */
	public Entity(long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
