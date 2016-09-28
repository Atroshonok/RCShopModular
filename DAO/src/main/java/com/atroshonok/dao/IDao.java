package com.atroshonok.dao;

import java.io.Serializable;

import com.atroshonok.dao.exceptions.DaoException;

public interface IDao<T> {

    /**
     * Saves or updates the given entity.
     * 
     * @param t
     * @throws DaoException
     */
    void saveOrUpdate(T t) throws DaoException;

    /**
     * Returns an entity from the database using its id. The method returns null if has not
     * found any entity with specified id.
     * 
     * @param id
     * @return
     * @throws DaoException
     */
    T get(Serializable id) throws DaoException;

    /**
     * Returns an entity from the database using its id. Throws an exception of
     * com.atroshonok.dao.DaoException class if has not found any entity with
     * specified id
     * 
     * @param id
     * @return
     * @throws DaoException
     */
    T load(Serializable id) throws DaoException;

    /**
     * Deletes the given entity from database.
     * 
     * @param t
     * @throws DaoException
     */
    void delete(T t) throws DaoException;

    /**
     * Saves the given entity to database and returns its id.
     * 
     * @param entity
     * @return
     * @throws DaoException
     */
    Serializable save(T entity) throws DaoException;
}
