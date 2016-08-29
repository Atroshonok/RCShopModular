/**
 * 
 */
package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atroshonok.dao.UserDao;
import com.atroshonok.dao.dbutils.ErrorMessageManager;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class UserService {
    private Logger log = Logger.getLogger(getClass());
    private UserDao userDao = new UserDao();
    private HibernateUtil util = HibernateUtil.getInstance();
    private Session session;
    private Transaction transaction;

    /**
     * Returns an object of user class by login and password. If user is not
     * found in a database this method returns null
     */
    public User getUserByLoginPassword(String login, String password) {
	log.info("Starting method getUserByLoginPassword(String login, String password)");
	User user = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    user = userDao.getUserByLoginPassword(login, password);
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting user by login and password in class: " + UserService.class, e);
	} // TODO HibernateExcepion в классе service-в?
	log.info("Ending method getUserByLoginPassword(String login, String password)");
	return user;
    }

    public void saveUserToDataBase(User user) throws ErrorAddingUserServiceException {
	log.info("Starting method saveUserToDataBase(User user)");
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    userDao.saveOrUpdate(user);
	    transaction.commit();
	    log.info("Saved user to DB: " + user);
	} catch (DaoException e) {
	    log.error("Error saving user to database in class: " + UserService.class, e);
	    transaction.rollback();
	    throw new ErrorAddingUserServiceException(ErrorMessageManager.getProperty("error.save.user"));
	}
	log.info("Ending method saveUserToDataBase(User user)");
    }

    public List<User> getAllUsers() {
	log.info("Starting method getAllUsers()");
	List<User> users = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    users = userDao.getAllUsers();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting all users from database in class: " + UserService.class, e);
	    transaction.rollback();
	} // TODO
	log.info("Ending method getAllUsers()");
	return users;
    }

    public User getUserByID(Serializable userId) {
	log.info("Starting method getUserByID(long userID)");
	User user = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    user = userDao.get(userId);
	    transaction.commit();
	    log.info("Got user: " + user);
	} catch (DaoException e) {
	    log.error("Error getting user by id = " + userId + " in class: " + UserService.class, e);
	    transaction.rollback();
	}
	log.info("Ending method getUserByID(long userID)");
	return user;
    }

    public void updateUserData(User user) throws ErrorUpdatingUserServiceException {
	log.info("Starting method updateUserData(User user)");
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    userDao.saveOrUpdate(user);
	    transaction.commit();
	    log.info("Updated user: " + user);
	} catch (DaoException e) {
	    log.error("Error updating user in class: " + UserService.class, e);
	    transaction.rollback();
	    throw new ErrorUpdatingUserServiceException(ErrorMessageManager.getProperty("error.update.user"));
	}
    }
}
