/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;
import java.util.Locale;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class UserDaoImpl extends DaoImpl<User> implements IUserDao {

    private static final String NAMED_PARAM_USER_LOGIN = "userLogin";
    @Autowired
    private MessageSource messageSourse;
    
    public User getUserByLoginPassword(String login, String password) throws DaoException {
	User result = null;
	try {
	    String hql = "SELECT u FROM User u WHERE u.login=:userLogin AND u.password=:userPassword";
	    Query query = getSession().createQuery(hql);
	    query.setParameter(NAMED_PARAM_USER_LOGIN, login);
	    query.setParameter("userPassword", password);
	    result = (User) query.uniqueResult();
	} catch (NonUniqueResultException e) {
	    log.error("Error getting user by login and password", e);
	    throw new DaoException(messageSourse.getMessage("error.user.get", null, Locale.getDefault()), e);
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

    @Override
    public List<User> getUsersByLogin(String login) {
	String hql = "FROM User u WHERE u.login=:userLogin";
	Query query = getSession().createQuery(hql);
	query.setParameter(NAMED_PARAM_USER_LOGIN, login);
	List<User> result = query.list();
	return result;
    }

}
