/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.dbutils.ErrorMessageManager;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

    public User getUserByLoginPassword(String login, String password) throws DaoException {
	User result = null;
	try {
	    String hql = "SELECT u FROM User u WHERE u.login=:userLogin AND u.password=:userPassword";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("userLogin", login);
	    query.setParameter("userPassword", password);
	    result = (User) query.uniqueResult();
	} catch (NonUniqueResultException e) {
	    log.error("Error getting user by login and password", e);
	    throw new DaoException(ErrorMessageManager.getProperty("error.user.get"), e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
	String hql = "FROM User u";
	Query query = getSession().createQuery(hql);
	List<User> results = query.list();
	return results;
    }

}
