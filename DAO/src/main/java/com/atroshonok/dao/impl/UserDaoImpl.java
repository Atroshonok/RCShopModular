/**
 * 
 */
package com.atroshonok.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.IUserDao;
import com.atroshonok.dao.entities.User;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class UserDaoImpl extends DaoImpl<User> implements IUserDao {

    private static final String NAMED_PARAM_USER_LOGIN = "userLogin";

    @Override
    public User getUserByLoginPassword(String login, String password) {
	String hql = "SELECT u FROM User u WHERE u.login=:userLogin AND u.password=:userPassword";
	Query query = getSession().createQuery(hql);
	query.setParameter(NAMED_PARAM_USER_LOGIN, login);
	query.setParameter("userPassword", password);
	User result = (User) query.uniqueResult();
	return result;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
	String hql = "FROM User u";
	Query query = getSession().createQuery(hql);
	List<User> results = query.list();
	return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByLogin(String login) {
	String hql = "FROM User u WHERE u.login=:userLogin";
	Query query = getSession().createQuery(hql);
	query.setParameter(NAMED_PARAM_USER_LOGIN, login);
	List<User> result = query.list();
	return result;
    }

}
