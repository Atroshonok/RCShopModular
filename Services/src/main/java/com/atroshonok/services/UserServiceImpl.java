/**
 * 
 */
package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IUserDao;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;

/**
 * @author Atroshonok Ivan
 *
 */

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);
    
    @Autowired
    private IUserDao userDao;
    @Autowired
    private MessageSource messageSource;

    /**
     * Returns an object of user class by login and password. If user is not
     * found in a database this method returns null
     */
    @Override
    public User getUserByLoginPassword(String login, String password) {
	log.info("Starting method getUserByLoginPassword(String login, String password)");
	User user = null;
	try {
	    user = userDao.getUserByLoginPassword(login, password);
	} catch (DaoException e) {
	    log.error("Error getting user by login and password.");
	    //TODO throw new ServiceException
	}
	log.info("Ending method getUserByLoginPassword(String login, String password)");
	return user;
    }
    
    @Override
    public void saveUserToDataBase(User user) throws ErrorAddingUserServiceException {
	log.info("Starting method saveUserToDataBase(User user)");
	try {
	    userDao.save(user);
	    log.info("Saved user to DB: " + user);
	} catch (DaoException e) {
	    log.error("Error saving user to database.");
	    throw new ErrorAddingUserServiceException(messageSource.getMessage("error.save.user", null, Locale.getDefault()), e);
	}
	log.info("Ending method saveUserToDataBase(User user)");
    }

    @Override
    public List<User> getAllUsers() {
	log.info("Starting method getAllUsers()");
	List<User> users = null;
	try {
	    users = userDao.getAllUsers();
	} catch (HibernateException e) {
	    log.error("Error getting all users from database");
	} // TODO throw an exception
	log.info("Ending method getAllUsers()");
	return users;
    }

    @Override
    public User getUserById(Serializable userId) {
	log.info("Starting method getUserById(long userId)");
	User user = null;
	try {
	    user = userDao.get(userId);
	    log.info("Got user: " + user);
	} catch (DaoException e) {
	    log.error("Error getting user by id = " + userId);
	}
	log.info("Ending method getUserById(long userId)");
	return user;
    }

    @Override
    public void updateUserData(User user) throws ErrorUpdatingUserServiceException {
	log.info("Starting method updateUserData(User user)");
	try {
	    userDao.saveOrUpdate(user);
	    log.info("Updated user: " + user);
	} catch (DaoException e) {
	    log.error("Error updating user.");
	    throw new ErrorUpdatingUserServiceException(messageSource.getMessage("error.update.user", null, Locale.getDefault()), e);
	}
	log.info("Ending method updateUserData(User user)");
    }
}
