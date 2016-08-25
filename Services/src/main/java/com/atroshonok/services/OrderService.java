/**
 * 
 */
package com.atroshonok.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atroshonok.entities.Order;
import com.atroshonok.entities.Product;
import com.atroshonok.jdbcdao.OrderDAO;
import com.atroshonok.jdbcdao.dbconectutils.ConnectionPool;
import com.atroshonok.services.exceptions.ErrorSavingOrderServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class OrderService {
	private Logger log = Logger.getLogger(getClass());

	public List<Order> getAllUserOrders(long userID) {
		Connection connection = null;
		List<Order> orders = null;
		try {
			connection = ConnectionPool.getConnection();
			OrderDAO orderDAO = new OrderDAO(connection);
			orders = orderDAO.getOrdersByUserID(userID);

		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		}
		return orders;

	}


	public void saveOrderDataToDatabase(Order order) throws ErrorSavingOrderServiceException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		}

		if (connection != null) {
			try {
				connection.setAutoCommit(false);
				OrderDAO orderDAO = new OrderDAO(connection);
				long orderID = orderDAO.saveOrderAndGetItID(order);

				saveAllOrderedProducts(order, orderDAO, orderID);
				connection.commit();

			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					log.error("Error by rollback transaction: " + e1);
				}
				throw new ErrorSavingOrderServiceException(e);
			} finally {
				ConnectionPool.releaseConnection(connection);
			}
		} else {
			throw new ErrorSavingOrderServiceException();
		}
	}

	private void saveAllOrderedProducts(Order order, OrderDAO orderDAO, long orderID) {
		for (Map.Entry<Product, Integer> pair : order.getOrderedProducts().entrySet()) {
			orderDAO.saveOrderedProduct(orderID, pair.getKey(), pair.getValue());
		}
	}
}
