package com.atroshonok.jdbcdao;

import java.util.List;

import com.atroshonok.jdbcdao.exception.ErrorAddingDAOException;
import com.atroshonok.jdbcdao.exception.ErrorUpdatingDAOException;



public interface IEntityDAO<T> {
	
	T getById(long id);

	List<T> getAll();

	void addNew(T entity) throws ErrorAddingDAOException;

	void update(T entity) throws ErrorUpdatingDAOException;

	void deleteById(long id);

	void delete(T entity);

}
