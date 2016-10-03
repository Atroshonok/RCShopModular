package com.atroshonok.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IUserDao;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.IUserService;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;
import com.atroshonok.services.exceptions.LoginAlreadyExistServiceException;
import com.atroshonok.services.exceptions.ServiceException;

/**
 * @author Atroshonok Ivan
 *
 */

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private static final String ERR_MSG_WRONG_LOGIN = "User with the same login already exist.";
    private static final String ERR_MSG_SAVE = "Error saving user to database.";
    private static final String ERR_MSG_GET_ALL = "Error getting all users from database.";
    private static final String ERR_MSG_UPDATE = "Error updating user.";

    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private MessageSource messageSource;

    @Override
    public User getUserByLoginPassword(String login, String password) {
	log.info("Starting method getUserByLoginPassword(String login, String password)");
	User user = null;
	try {
	    user = userDao.getUserByLoginPassword(login, password);
	} catch (DataAccessException e) {
	    log.error("Error getting user by login and password.");
	}
	log.info("Ending method getUserByLoginPassword(String login, String password)");
	return user;
    }

    @Override
	// ToDatabase is extra, please rename the method
    public void saveUserToDataBase(User user) throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException {
	log.info("Starting method saveUserToDataBase(User user)");
	try {
	    List<User> users = userDao.getUsersByLogin(user.getLogin());
	    if (users.isEmpty()) {
		userDao.save(user);
	    } else
		throw new LoginAlreadyExistServiceException(messageSource.getMessage("error.user.already-exist", null, ERR_MSG_WRONG_LOGIN, Locale.getDefault()));
	    log.info("Saved user to DB: " + user);
	} catch (DaoException e) {
	    log.error("Error saving user to database.");
	    throw new ErrorAddingUserServiceException(messageSource.getMessage("error.user.save", null, ERR_MSG_SAVE, Locale.getDefault()), e);
	}
	log.info("Ending method saveUserToDataBase(User user)");
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
	log.info("Starting method getAllUsers()");
	List<User> users = new ArrayList<>();
	try {
	    users = userDao.getAllUsers();
	} catch (DataAccessException e) {
	    log.error("Error getting all users from database.");
	    throw new ServiceException(messageSource.getMessage("error.users.get", null, ERR_MSG_GET_ALL, Locale.getDefault()), e);
	}
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
	// please rename to updateUser()
    public void updateUserData(User user) throws ErrorUpdatingUserServiceException {
	log.info("Starting method updateUserData(User user)");
	try {
	    userDao.saveOrUpdate(user);
	    log.info("Updated user: " + user);
	} catch (DaoException e) {
	    log.error("Error updating user.");
	    throw new ErrorUpdatingUserServiceException(messageSource.getMessage("error.user.update", null, ERR_MSG_UPDATE, Locale.getDefault()), e);
	}
	log.info("Ending method updateUserData(User user)");
    }
}
