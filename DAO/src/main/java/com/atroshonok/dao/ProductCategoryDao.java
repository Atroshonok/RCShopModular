/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.Query;

import com.atroshonok.dao.entities.ProductCategory;

/**
 * @author Ivan Atroshonok
 *
 */
public class ProductCategoryDao extends BaseDao<ProductCategory> {

    public List<ProductCategory> getAllProductCategories() {
	String hql = "FROM ProductCategory c";
	Query query = util.getSession().createQuery(hql);
	List<ProductCategory> results = query.list();
	return results;
    }

}
