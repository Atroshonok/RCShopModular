/**
 * 
 */
package com.atroshonok.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atroshonok.dao.ProductCategoryDao;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */
public class ProductCategoryService {
    private static ProductCategoryService productCategoryService;
    private static Logger log = Logger.getLogger(ProductCategoryService.class);
    private HibernateUtil util = HibernateUtil.getInstance();
    private ProductCategoryDao productCategoryDao = new ProductCategoryDao();
    private Session session = null;
    private Transaction transaction = null;

    private ProductCategoryService() {
	super();
    }

    public static ProductCategoryService getInstance() {
	if (productCategoryService == null) {
	    productCategoryService = new ProductCategoryService();
	}
	return productCategoryService;
    }

    public ProductCategory getCategoryById(long categoryId) {
	ProductCategory productCategory = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    productCategory = productCategoryDao.get(categoryId);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error getting product category by id = " + categoryId + " in class: " + ProductCategoryService.class, e);
	    transaction.rollback();
	}
	return productCategory;
    }

    public List<ProductCategory> getAllProductCategories() {
	log.info("Starting method getAllProductCategories()");
	List<ProductCategory> categories = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    categories = productCategoryDao.getAllProductCategories();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting all product categories from database: ", e);
	    transaction.rollback();
	} // TODO
	log.info("Ending method getAllProductCategories()");
	return categories;

    }
}
