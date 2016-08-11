package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.exception.ErrorAddingDAOException;
import com.atroshonok.dao.exception.ErrorUpdatingDAOException;



public interface IEntityDAO<T> {
	
	T getById(long id);

	List<T> getAll();

	void addNew(T entity) throws ErrorAddingDAOException;

	void update(T entity) throws ErrorUpdatingDAOException;

	void deleteById(long id);

	void delete(T entity);

}
