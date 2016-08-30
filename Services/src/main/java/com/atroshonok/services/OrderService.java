/**
 * 
 */
package com.atroshonok.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atroshonok.dao.OrderDao;
import com.atroshonok.dao.dbutils.ErrorMessageManager;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class OrderService {
    private static OrderService orderService;
    private Logger log = Logger.getLogger(getClass());
    private HibernateUtil util = HibernateUtil.getInstance();
    private OrderDao orderDao = new OrderDao();
    private Session session = null;
    private Transaction transaction = null;

    public OrderService() {
	super();
    }
    
    public static OrderService getInstance() {
	if (orderService == null) {
	    orderService = new OrderService();
	}
	return orderService;
    }

    public List<Order> getAllUserOrders(long userId) {
	log.info("Starting method getAllUserOrders(long userId) in " + OrderService.class);
	List<Order> orders = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    String hql = "FROM Order o WHERE o.user.id=:userId";
	    Query query = session.createQuery(hql);
	    query.setParameter("userId", userId);
	    orders = query.list();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting user orders by user id = " + userId + " in class: " + OrderService.class, e);
	    transaction.rollback();
	}
	log.info("Ending method getAllUserOrders(long userId) in " + OrderService.class);
	return orders;

    }

    public void saveOrderData(Order order) throws ErrorSavingOrderServiceException {
	log.info("Starting method saveOrderData(Order order) in class: " + OrderService.class);
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    orderDao.saveOrUpdate(order);
	    transaction.commit();
	    log.info("Saved order: " + order);
	} catch (DaoException e) {
	    log.error("Error saving order in class: " + OrderService.class, e);
	    transaction.rollback();
	    throw new ErrorSavingOrderServiceException(ErrorMessageManager.getProperty("error.save.order"));
	}
	log.info("Ending method saveOrderData(Order order) in class: " + OrderService.class);
    }
}
