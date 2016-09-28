package com.atroshonok.services;

import java.util.List;

import com.atroshonok.dao.entities.Order;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

public interface IOrderService {

    /**
     * Returns a list of the user orders using user id. The method returns an
     * empty collection if hasn't found any.
     * 
     * @param userId
     * @return
     */
    List<Order> getAllUserOrders(long userId);

    /**
     * Saves the given order. This method throws an
     * com.atroshonok.services.exceptions.ErrorSavingOrderServiceException if
     * can't save an order.
     * 
     * @param order
     * @throws ErrorSavingOrderServiceException
     */
    void saveOrderData(Order order) throws ErrorSavingOrderServiceException;
}
