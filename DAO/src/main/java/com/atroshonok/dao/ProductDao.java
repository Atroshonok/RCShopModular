/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.Query;

import com.atroshonok.dao.entities.Product;

/**
 * @author Ivan Atroshonok
 *
 */
public class ProductDao extends BaseDao<Product> {

    public List<Product> getAllProducts() {
   	String hql = "FROM Product p";
   	Query query = util.getSession().createQuery(hql);
   	List<Product> results = query.list();
   	return results;
       }

}
