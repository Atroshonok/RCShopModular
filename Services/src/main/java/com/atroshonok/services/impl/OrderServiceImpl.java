/**
 * 
 */
package com.atroshonok.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IOrderDao;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.IOrderService;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

/**
 * @author Atroshonok Ivan
 *
 */

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    private static final String ERR_MSG_SAVE = "Error saving order.";

    private static Logger log = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private MessageSource messageSource;

    @Override
    public List<Order> getAllUserOrders(long userId) {
	log.info("Starting method getAllUserOrders(long userId).");
	List<Order> orders = new ArrayList<>();
	try {
	    orders = orderDao.getOrdersByUserId(userId);
	} catch (DataAccessException e) {
		// hiding exceptions is bad practise
	    log.error("Error getting user orders by user id = " + userId, e);
	}
	log.info("Ending method getAllUserOrders(long userId)");
	return orders;

    }

    @Override
	// consider renaming to saveOrder()
    public void saveOrderData(Order order) throws ErrorSavingOrderServiceException {
	log.info("Starting method saveOrderData(Order order)");
	try {
	    orderDao.saveOrUpdate(order);
	    log.info("Saved order: " + order);
	} catch (DaoException e) {
	    log.error("Error saving order.");
	    throw new ErrorSavingOrderServiceException(messageSource.getMessage("error.save.order", null, ERR_MSG_SAVE, Locale.getDefault()), e);
	}
	log.info("Ending method saveOrderData(Order order)");
    }
}
