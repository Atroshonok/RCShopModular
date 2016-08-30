package com.atroshonok.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author Atroshonok Ivan
 */
public class BaseDao<T> implements Dao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);
    protected HibernateUtil util = HibernateUtil.getInstance();

    public BaseDao() {

    }

    public void saveOrUpdate(T t) throws DaoException {
	log.info("SaveOrUpdate entity: " + t.getClass().getName());
	try {
	    Session session = util.getSession();
	    session.saveOrUpdate(t);
	    log.info("Saved or updated entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error saving or updating entity of " + t.getClass().getName() + "class in Dao" + e);
	    throw new DaoException(e);
	}
    }

    public Serializable save(T t) throws DaoException {
	log.info("Save entity: " + t.getClass().getName());
	Serializable id = null;
	try {
	    Session session = util.getSession();
	    id = session.save(t);
	    log.info("Saved entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error saving entity of " + t.getClass().getName() + " class in Dao" + e);
	    throw new DaoException(e);
	}
	return id;
    }

    public T get(Serializable id) throws DaoException {
	log.info("Get entity by id: " + id);
	T t = null;
	try {
	    Session session = util.getSession();
	    t = (T) session.get(getPersistentClass(), id);
	    log.info("Got entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error getting entity of " + getPersistentClass().getName() + "class in Dao" + e);
	    throw new DaoException(e);
	}
	return t;
    }

    public T load(Serializable id) throws DaoException {
	log.info("Load entity by id: " + id);
	T t = null;
	try {
	    Session session = util.getSession();
	    t = (T) session.load(getPersistentClass(), id);
	    log.info("Loaded entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error loading entity of " + getPersistentClass().getName() + " class in Dao" + e);
	    throw new DaoException(e);
	}
	return t;
    }

    public void delete(T t) throws DaoException {
	log.info("Delete entity: " + t);
	try {
	    Session session = util.getSession();
	    session.delete(t);
	    log.info("Deleted entity: " + t);
	} catch (HibernateException e) {
	    log.error("Error deleting entity of " + t.getClass().getName() + " class in Dao" + e);
	    throw new DaoException(e);
	}
    }
    
    private Class<T> getPersistentClass() {
	return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
