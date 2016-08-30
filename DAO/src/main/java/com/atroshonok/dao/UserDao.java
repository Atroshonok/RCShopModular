/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.Query;

import com.atroshonok.dao.entities.User;

/**
 * @author Ivan Atroshonok
 *
 */
public class UserDao extends BaseDao<User> {
    
    public User getUserByLoginPassword(String login, String password) {
	String hql = "SELECT u FROM User u WHERE u.login=:userLogin AND u.password=:userPassword";
	Query query = util.getSession().createQuery(hql);
	query.setParameter("userLogin", login);
	query.setParameter("userPassword", password);
	User result = (User) query.uniqueResult();
	return result;
    }
    
    public List<User> getAllUsers() {
	String hql = "FROM User u";
	Query query = util.getSession().createQuery(hql);
	List<User> results = query.list();
	return results;
    }

}
