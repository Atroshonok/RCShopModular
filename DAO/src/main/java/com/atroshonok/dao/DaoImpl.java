package com.atroshonok.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Atroshonok Ivan
 */

@Repository
public class DaoImpl<T> implements IDao<T> {
    protected Logger log = Logger.getLogger(getClass());
    @Autowired
    protected SessionFactory sessionFactory;

    public DaoImpl() {

    }

    public void saveOrUpdate(T t) throws DaoException {
	log.info("SaveOrUpdate entity: " + t.getClass().getName());
	try {
	    getSession().saveOrUpdate(t);
	    log.info("Saved or updated entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error saving or updating entity of " + t.getClass().getName() + " class.");
	    throw new DaoException(e);
	}
    }

    public Serializable save(T t) throws DaoException {
	log.info("Save entity: " + t.getClass().getName());
	Serializable id = null;
	try {
	    id = getSession().save(t);
	    log.info("Saved entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error saving entity of " + t.getClass().getName() + " class.");
	    throw new DaoException(e);
	}
	return id;
    }

    @SuppressWarnings("unchecked")
    public T get(Serializable id) throws DaoException {
	log.info("Get entity by id: " + id);
	T t = null;
	try {
	    t = (T) getSession().get(getPersistentClass(), id);
	    log.info("Got entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error getting entity of " + getPersistentClass().getName() + " class.");
	    throw new DaoException(e);
	}
	return t;
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) throws DaoException {
	log.info("Load entity by id: " + id);
	T t = null;
	try {
	    t = (T) getSession().load(getPersistentClass(), id);
	    log.info("Loaded entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error loading entity of " + getPersistentClass().getName() + " class.");
	    throw new DaoException(e);
	}
	return t;
    }

    public void delete(T t) throws DaoException {
	log.info("Delete entity: " + t);
	try {
	    getSession().delete(t);
	    log.info("Deleted entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error deleting entity of " + t.getClass().getName() + " class.");
	    throw new DaoException(e);
	}
    }
    
    @SuppressWarnings("unchecked")
    private Class<T> getPersistentClass() {
	return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public Session getSession() {
	return sessionFactory.getCurrentSession();
    }

}
