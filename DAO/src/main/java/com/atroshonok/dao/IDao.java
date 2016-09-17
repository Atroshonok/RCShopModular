package com.atroshonok.dao;

import java.io.Serializable;

import com.atroshonok.dao.exceptions.DaoException;


public interface IDao<T> {
    
    void saveOrUpdate(T t) throws DaoException;

    T get(Serializable id) throws DaoException;

    T load(Serializable id) throws DaoException;

    void delete(T t) throws DaoException;

    Serializable save(T entity) throws DaoException;
}




