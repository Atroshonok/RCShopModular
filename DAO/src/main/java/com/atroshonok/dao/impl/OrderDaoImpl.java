/**
 * 
 */
package com.atroshonok.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.IOrderDao;
import com.atroshonok.dao.entities.Order;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class OrderDaoImpl extends DaoImpl<Order> implements IOrderDao {

    @SuppressWarnings("unchecked")
    public List<Order> getOrdersByUserId(Long userId) {
	List<Order> orders = new ArrayList<>();
	String hql = "FROM Order o WHERE o.user.id=:userId";
	Query query = getSession().createQuery(hql);
	query.setParameter("userId", userId);
	orders = query.list();
	return orders;
    }
}
