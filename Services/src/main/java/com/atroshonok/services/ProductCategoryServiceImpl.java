/**
 * 
 */
package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IProductCategoryDao;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */
// TODO remake Exceptions handling

@Service
@Transactional
public class ProductCategoryServiceImpl implements IProductCategoryService {
    private static Logger log = Logger.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private IProductCategoryDao productCategoryDao;

    @Override
    public ProductCategory getCategoryById(Serializable categoryId) {
	ProductCategory productCategory = null;
	try {
	    productCategory = productCategoryDao.get(categoryId);
	} catch (DaoException e) {
	    log.error("Error getting product category by id = " + categoryId);
	}
	return productCategory;
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
	log.info("Starting method getAllProductCategories()");
	List<ProductCategory> categories = null;
	try {
	    categories = productCategoryDao.getAllProductCategories();
	} catch (HibernateException e) {
	    log.error("Error getting all product categories from database: ", e);
	}
	log.info("Ending method getAllProductCategories()");
	return categories;

    }
}
