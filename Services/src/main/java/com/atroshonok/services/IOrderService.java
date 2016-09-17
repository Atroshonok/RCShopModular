package com.atroshonok.services;

import java.util.List;

import com.atroshonok.dao.entities.Order;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

public interface IOrderService {

    List<Order> getAllUserOrders(long userId);

    void saveOrderData(Order order) throws ErrorSavingOrderServiceException;
}
