package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.Order;

public interface IOrderDao extends IDao<Order> {
   
    List<Order> getOrdersByUserId(long userId);

}
