/**
 * 
 */
package com.atroshonok.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.entities.ProductCategory;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class ProductCategoryDaoImpl extends DaoImpl<ProductCategory> implements IProductCategoryDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductCategory> getAllProductCategories() {
	String hql = "FROM ProductCategory c";
	Query query = getSession().createQuery(hql);
        // you can return query list here (and in other similar cases) without saving it to temporary variable
	List<ProductCategory> results = query.list();
	return results;
    }

}
