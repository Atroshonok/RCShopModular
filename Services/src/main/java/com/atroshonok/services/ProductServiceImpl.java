/**
 * 
 */
package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IProductDao;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;
import com.atroshonok.services.exceptions.ServiceException;

/**
 * @author Atroshonok Ivan
 *
 */

//TODO exception handling
@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    private static Logger log = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductDao productDao;
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public List<Product> getProductsByCategoryId(Serializable categoryId) {
	log.info("Starting method getProductsByCategoryId(long categoryId)");
	List<Product> products = null;
	try {
	    products = productDao.getProductsByCategoryId(categoryId);
	} catch (DataAccessException e) {
	    log.error("Error getting products by category = " + categoryId);
	}
	log.info("Ending method getProductsByCategoryId(long categoryId)");
	return products;
    }

    @Override
    public List<Product> getAllProducts() {
	log.info("Starting method getAllProducts()");
	List<Product> products = productDao.getAllProducts();
	log.info("Ending method getAllProducts()");
	return products;
    }

    @Override
    public Product getProductById(Serializable productId) {
	log.info("Starting method getProductById(Serializable productId)");
	Product product = null;
	try {
	    product = productDao.get(productId);
	    log.info("Got product: " + product);
	} catch (DaoException e) {
	    log.error("Error getting product by id = " + productId);
	}
	log.info("Ending method getProductById(Serializable productId)");
	return product;
    }

    @Override
    public void updateProduct(Product product) throws ErrorUpdatingPoductServiceException {
	log.info("Starting method updateProductInDatabase(Product product)");
	try {
	    productDao.saveOrUpdate(product);
	    log.info("Updated product: " + product);
	} catch (DaoException e) {
	    log.error("Error updating product in class: " + ProductServiceImpl.class, e);
	    throw new ErrorUpdatingPoductServiceException(messageSource.getMessage("error.update.product", null, Locale.getDefault()), e);
	}
	log.info("Ending method updateProductInDatabase(Product product)");
    }

    @Override
	// ToDatabase is extra. please rename the method
    public Serializable addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException {
	log.info("Starting method addNewProductToDatabase(Product product)");
	Serializable id = null; //Warning:(93, 20) Variable 'id' initializer 'null' is redundant - thanks to mu Intellij IDEA
	try {
	    id = productDao.save(product);
	    log.info("Saved product to DB: " + product);
	} catch (DaoException e) {
	    log.error("Error saving product to database: ", e);
	    throw new ErrorAddingPoductServiceException(messageSource.getMessage("error.save.product", null, Locale.getDefault()), e);
	}
	log.info("Ending method addNewProductToDatabase(Product product)");
	return id;
    }

    @Override
    public List<Product> getProductsByClientFilter(ClientFilter clientFilter) {
	List<Product> results = productDao.getProductsByClientFilter(clientFilter);
	return results;
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalProductsCount() {
	long totalCount = productDao.getTotalProductsCount();
	return totalCount;
    }

    // for pagination
    @Override
    @Transactional(readOnly = true)
    public long getProductsCountAccordingClientFilter(ClientFilter clientFilter) {
	long result = productDao.getProductsCountAccordingClientFilter(clientFilter);
	return result;
    }

    @Override
    public void deleteProduct(Product product) throws ServiceException {
	try {
	    productDao.delete(product);
	} catch (DaoException e) {
	    log.error("Error deleting product. ");
	    throw new ServiceException(messageSource.getMessage("error.delete.product", null, Locale.getDefault()), e);
	}
    }
}
