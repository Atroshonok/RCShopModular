package com.atroshonok.dao;

import java.io.Serializable;

import com.atroshonok.dao.exceptions.DaoException;


public interface Dao<T> {
    
    void saveOrUpdate(T t) throws DaoException;

    T get(Serializable id) throws DaoException;

    T load(Serializable id) throws DaoException;

    void delete(T t) throws DaoException;
}




