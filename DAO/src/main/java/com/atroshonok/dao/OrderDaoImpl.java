/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.entities.Order;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao {

    @SuppressWarnings("unchecked")
    public List<Order> getOrdersByUserId(long userId) {
	List<Order> orders = null;
	try {
	    String hql = "FROM Order o WHERE o.user.id=:userId";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("userId", userId);
	    orders = query.list();
	} catch (HibernateException e) {
	    log.error("Error getting user orders by user id = " + userId);
	}
	return orders;
    }
}