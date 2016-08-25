/**
 * 
 */
package com.atroshonok.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.atroshonok.dao.entities.User;
import com.atroshonok.jdbcdao.UserDAO;
import com.atroshonok.jdbcdao.dbconectutils.ConnectionPool;
import com.atroshonok.jdbcdao.exception.ErrorAddingDAOException;
import com.atroshonok.jdbcdao.exception.ErrorUpdatingDAOException;
import com.atroshonok.jdbcdao.exception.NoExistUserDAOException;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class UserService {
	private Logger log = Logger.getLogger(getClass());

	public User getUserByLoginPassword(String login, String password) {
		User user = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			user = userDAO.getByLoginPassword(login, password);

		} catch (NoExistUserDAOException e) {
			log.error("That user is not found in the database: " + e);
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return user;
	}
	
	public void saveUserToDataBase(User user) throws ErrorAddingUserServiceException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			userDAO.addNew(user);

		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e); 
		} catch (ErrorAddingDAOException e) {
			throw new ErrorAddingUserServiceException(e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public List<User> getAllUsers() {
		Connection connection = null;
		List<User> users = null;
		try {
			connection = ConnectionPool.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			users = userDAO.getAll();

		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e); 
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return users;
	}

	public User getUserByID(long userID) {
		Connection connection = null;
		User user = null;
		try {
			connection = ConnectionPool.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			user = userDAO.getById(userID);

		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e); 
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return user;
	}

	public void updateUserData(User user) throws ErrorUpdatingUserServiceException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			userDAO.update(user);

		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e); 
		} catch (ErrorUpdatingDAOException e) {
			throw new ErrorUpdatingUserServiceException(e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		
	}

}
