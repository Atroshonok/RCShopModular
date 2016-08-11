/**
 * 
 */
package com.atroshonok.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.atroshonok.entities.Product;

/**
 * @author Atroshonok Ivan
 *
 */
public class ProductDAO extends AbstractDAO<Product> {

	private static final String GET_PRODUCTS_BY_CATEGORY_ID_SQL = "SELECT * FROM products WHERE categoryID = ?;";

	public ProductDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected void initSQLQueries() {
		this.deleteByIdEntitySQL = "DELETE FROM products WHERE ID = ? LIMIT 1;";
		this.selectEntitiesSQL = "SELECT * FROM products;";
		this.selectEntityByIdSQL = "SELECT * FROM products WHERE products.ID = ?;";
		this.addEntitySQL = "INSERT INTO products (name, price, categoryID, count, description) VALUES (?,?,?,?,?);";
		this.updateEntitySQL = "UPDATE products SET name = ?, price = ?, categoryID = ?, count = ?, description = ? WHERE ID = ? LIMIT 1;";

	}

	@Override
	protected Product initEntity(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setId(resultSet.getLong(1));
		product.setName(resultSet.getString(2));
		product.setPrice(resultSet.getDouble(3));
		product.setCategory(new ProductCategoryDAO(connection).getById(resultSet.getLong(4)));
		product.setCount(resultSet.getInt(5));
		product.setDescription(resultSet.getString(6));
		return product;
	}

	// INSERT INTO products (name, price, categoryID, count, description) VALUES
	// (?,?,?,?,?);
	@Override
	protected PreparedStatement prepareInsertStatement(Product product, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, product.getName());
		statement.setDouble(2, product.getPrice());
		statement.setLong(3, product.getCategory().getId());
		statement.setInt(4, product.getCount());
		statement.setString(5, product.getDescription());
		return statement;
	}

	// UPDATE product SET name = ?, price = ?, categoryID = ?, count = ?,
	// description = ? WHERE ID = ?;
	@Override
	protected PreparedStatement prepareUpdateStatement(Product product, PreparedStatement statement)
			throws SQLException {
		// prepareInsertStatement(product, statement);
		statement.setString(1, product.getName());
		statement.setDouble(2, product.getPrice());
		statement.setLong(3, product.getCategory().getId());
		statement.setInt(4, product.getCount());
		statement.setString(5, product.getDescription());
		statement.setLong(6, product.getId());
		return statement;
	}
	
	public List<Product> getProductsByCategoryID(int categoryID) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Product> products = null;
		try {
			statement = connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_ID_SQL);
			statement.setInt(1, categoryID);
			resultSet = statement.executeQuery();
			products = initEntities(resultSet);

		} catch (SQLException e) {
			log.error("SQL exception getting products by categoryID: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return products;
		
	}

}
