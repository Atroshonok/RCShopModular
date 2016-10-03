package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.Order;

public interface IOrderDao extends IDao<Order> {

    /**
     * Returns an objects list of a com.atroshonok.dao.entities.Order class
     * using the user id. If there is no one order this method returns an empty
     * collection. This method never returns null.
     * 
     * @param userId
     * @return
     */
    List<Order> getOrdersByUserId(Long userId);

}
