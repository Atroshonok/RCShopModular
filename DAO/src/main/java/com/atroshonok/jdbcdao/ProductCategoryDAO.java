/**
 * 
 */
package com.atroshonok.jdbcdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atroshonok.entities.ProductCategory;

/**
 * @author Atroshonok Ivan
 *
 */
public class ProductCategoryDAO extends AbstractDAO<ProductCategory> {

	public ProductCategoryDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected ProductCategory initEntity(ResultSet resultSet) throws SQLException {
		ProductCategory category = new ProductCategory();
		
		category.setId(resultSet.getLong(1));
		category.setName(resultSet.getString(2));
		return category;
	}

	@Override
	protected PreparedStatement prepareUpdateStatement(ProductCategory category, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, category.getName());
		statement.setLong(2, category.getId());
		return statement;
	}

	@Override
	protected PreparedStatement prepareInsertStatement(ProductCategory category, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, category.getName());
		return statement;
	}

	@Override
	protected void initSQLQueries() {
		this.deleteByIdEntitySQL = "DELETE FROM productcategories WHERE ID = ? LIMIT 1;";
		this.selectEntitiesSQL = "SELECT * FROM productcategories;";
		this.selectEntityByIdSQL = "SELECT * FROM productcategories WHERE ID = ?;";
		this.addEntitySQL = "INSERT INTO productcategories (categoryName) VALUES (?);";
		this.updateEntitySQL = "UPDATE productcategories SET categoryName = ? WHERE ID = ?;";
	}
	

}
