/**
 * 
 */
package com.atroshonok.jdbcdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.entities.OrderState;
import com.atroshonok.dao.entities.Product;

/**
 * @author Atroshonok Ivan
 *
 */
public class OrderDAO extends AbstractDAO<Order> {
	private static final String ADD_PRODUCTS_TO_DB_SQL = "INSERT INTO orderedproductslist (orderID, productID, productCount) VALUES (?,?,?);";
	private static final String GET_LAST_INSERTED_ID_SQL = "SELECT LAST_INSERT_ID();";
	private static final String GET_ORDERED_PRODUCTS_MAP_SQL = "SELECT productID, productCount FROM orderedproductslist WHERE orderID = ?;";
	private static final String GET_ORDERS_BY_USER_ID_SQL = "SELECT * FROM orders WHERE userID = ?;";

	public OrderDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected void initSQLQueries() {
		deleteByIdEntitySQL = "DELETE FROM orders WHERE ID = ? LIMIT 1;";
		selectEntitiesSQL = "SELECT * FROM orders;";
		selectEntityByIdSQL = "SELECT * FROM orders WHERE ID = ?;";
		addEntitySQL = "INSERT INTO orders (userID, sumPrice, orderState) VALUES (?,?,?);";
		updateEntitySQL = "UPDATE orders SET userID = ?, sumPrice = ?, orderState = ? WHERE ID = ? LIMIT 1;";

	}

	@Override
	protected Order initEntity(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong(1));
		order.setUserId(resultSet.getLong(2));
		order.setSumPrice(resultSet.getDouble(3));
		order.setState(OrderState.valueOf(resultSet.getString(4).trim()));
		order.setOrderLines(getPoductsMapByOrderID(order.getId()));
		return order;
	}

	public void saveOrderedProduct(long orderID, Product product, int count) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(ADD_PRODUCTS_TO_DB_SQL);
			statement.setLong(1, orderID);
			statement.setLong(2, product.getId());
			statement.setInt(3, count);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL exception adding ordered product to database: " + e);
		} finally {
			closeStatement(statement);
		}
	}

	public long saveOrderAndGetItID(Order order) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(addEntitySQL);
			statement = prepareInsertStatement(order, statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("SQL exception adding order to database: " + e);
		}
		return getLastInsertedID(statement);
	}

	private long getLastInsertedID(Statement statement) {
		ResultSet resultSet = null;
		long id = 0;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(GET_LAST_INSERTED_ID_SQL);
			resultSet.next();
			id = resultSet.getLong(1);

		} catch (SQLException e) {
			log.error("SQL exception getting last inserted orderID from database: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return id;

	}

	public Map<Product, Integer> getPoductsMapByOrderID(long orderID) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Map<Product, Integer> products = new HashMap<>();
		try {
			statement = connection.prepareStatement(GET_ORDERED_PRODUCTS_MAP_SQL);
			statement.setLong(1, orderID);
			resultSet = statement.executeQuery();
			initOrderedProductMap(resultSet, products);

		} catch (SQLException e) {
			log.error("SQL exception getting ordered products map from database: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return products;
	}

	private void initOrderedProductMap(ResultSet resultSet, Map<Product, Integer> products) throws SQLException {
		while (resultSet.next()) {
			ProductDAO productDAO = new ProductDAO(connection);
			Product product = productDAO.getById(resultSet.getLong(1));
			int productCount = resultSet.getInt(2); 
			products.put(product, productCount);
		}
	}

	// INSERT INTO orders (userID, sumPrice, orderState) VALUES (?,?,?);
	@Override
	protected PreparedStatement prepareInsertStatement(Order order, PreparedStatement statement) throws SQLException {
		statement.setLong(1, order.getUserId());
		statement.setDouble(2, order.getSumPrice());
		statement.setString(3, order.getState().toString());
		return statement;
	}

	// UPDATE orders SET userID = ?, sumPrice = ?, orderState = ? WHERE ID = ?
	// LIMIT 1;
	@Override
	protected PreparedStatement prepareUpdateStatement(Order order, PreparedStatement statement) throws SQLException {
		prepareInsertStatement(order, statement);
		statement.setLong(4, order.getId());
		return statement;
	}

	public List<Order> getOrdersByUserID(long userID) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Order> orders = new ArrayList<>();
		try {
			statement = connection.prepareStatement(GET_ORDERS_BY_USER_ID_SQL);
			statement.setLong(1, userID);
			resultSet = statement.executeQuery();
			orders = initEntities(resultSet);

		} catch (SQLException e) {
			log.error("SQL exception getting orders by user ID from database: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return orders;
	}

}
