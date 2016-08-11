/**
 * 
 */
package com.atroshonok.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.atroshonok.dao.ProductDAO;
import com.atroshonok.dao.exception.ErrorAddingDAOException;
import com.atroshonok.dao.exception.ErrorUpdatingDAOException;
import com.atroshonok.entities.Product;
import com.atroshonok.dao.dbconectutils.ConnectionPool;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class ProductService {

	private Logger log = Logger.getLogger(getClass());

	public List<Product> getProductsByCategoryID(int categoryID) {
		Connection connection = null;
		List<Product> products = null;
		try {
			connection = ConnectionPool.getConnection();
			ProductDAO productDAO = new ProductDAO(connection);
			products = productDAO.getProductsByCategoryID(categoryID);
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return products;
	}

	public List<Product> getAllProducts() {
		Connection connection = null;
		List<Product> products = null;
		try {
			connection = ConnectionPool.getConnection();
			ProductDAO productDAO = new ProductDAO(connection);
			products = productDAO.getAll();
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return products;
	}

	public Product getProductByID(long productID) {
		Connection connection = null;
		Product product = null;
		try {
			connection = ConnectionPool.getConnection();
			ProductDAO productDAO = new ProductDAO(connection);
			product = productDAO.getById(productID);
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		return product;
	}

	public void updateProductInDatabase(Product product) throws ErrorUpdatingPoductServiceException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			ProductDAO productDAO = new ProductDAO(connection);
			productDAO.update(product);
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} catch (ErrorUpdatingDAOException e) {
			throw new ErrorUpdatingPoductServiceException(e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}

	}

	public void addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			ProductDAO productDAO = new ProductDAO(connection);
			productDAO.addNew(product);
		} catch (SQLException e) {
			log.error("Can't get connection from ConnectionPool: " + e);
		} catch (ErrorAddingDAOException e) {
			throw new ErrorAddingPoductServiceException(e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
		
	}
}
