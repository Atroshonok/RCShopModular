/**
 * 
 */
package com.atroshonok.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.dbutils.ErrorMessageManager;
import com.atroshonok.dao.entities.ClientFilter;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getAllProducts() {
	String hql = "FROM Product p";
	Query query = getSession().createQuery(hql);
	List<Product> results = query.list();
	return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getProductsByCategoryId(Serializable categoryId) throws DaoException {
	List<Product> products = null;
	try {
	    String hql = "FROM Product p WHERE p.category.id=:productCategoryId";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("productCategoryId", categoryId);
	    products = query.list();
	} catch (HibernateException e) {
	    log.error("Error getting products by category = " + categoryId);
	    throw new DaoException(ErrorMessageManager.getProperty("error.product.get"), e);
	    // TODO to remake the exceptions handling
	}
	return products;
    }

    @Override
    public Long getTotalProductsCount() {
	Long totalCount = null;
	String hql = "SELECT count(*) FROM Product p";
	Query query = getSession().createQuery(hql);
	totalCount = (Long) query.uniqueResult();
	return totalCount;
    }

    @Override
    public long getProductsCountAccordingClientFilter(ClientFilter clientFilter) {
	Criteria criteria = getSession().createCriteria(Product.class);
	createCriteriaAccordingClientFilter(clientFilter, criteria);
	criteria.setProjection(Projections.rowCount());
	long result = (long) criteria.uniqueResult();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getProductsByClientFilter(ClientFilter clientFilter) {
	List<Product> results = null;
	try {
	    Criteria criteria = getSession().createCriteria(Product.class);
	    createCriteriaAccordingClientFilter(clientFilter, criteria);

	    int startPosition = (clientFilter.getCurrentPage() - 1) * clientFilter.getItemsPerPage();
	    criteria.setFirstResult(startPosition);

	    int maxResult = clientFilter.getItemsPerPage();
	    criteria.setMaxResults(maxResult);

	    results = criteria.list();
	} catch (HibernateException e) {
	    log.error("Error getting the products according to the client filter parameters");
	}
	return results;
    }

    // TODO refactor this method
    private void createCriteriaAccordingClientFilter(ClientFilter clientFilter, Criteria criteria) {
	Disjunction disjun = Restrictions.disjunction();
	String categoryAll = clientFilter.getCategoriesId().get(0);
	if ((categoryAll == null) || (categoryAll.isEmpty())) {
	    for (int i = 1; i < clientFilter.getCategoriesId().size(); i++) {
		String categoryId = clientFilter.getCategoriesId().get(i);
		if ((categoryId != null) && !(categoryId.isEmpty())) {
		    Criterion criterion = (Criterion) Restrictions.eq("category.id", Long.parseLong(categoryId));
		    disjun.add(criterion);
		}
	    }
	}
	criteria.add(Restrictions.or(disjun));

	if (clientFilter.getPriceFrom() > 0) {
	    criteria.add(Restrictions.gt("price", clientFilter.getPriceFrom()));
	}
	if ((clientFilter.getPriceTo() > 0) && (clientFilter.getPriceTo() >= clientFilter.getPriceFrom())) {
	    criteria.add(Restrictions.le("price", clientFilter.getPriceTo()));
	}
	if ((clientFilter.getSorting() != null) && (clientFilter.getSorting() != 0)) {
	    if (clientFilter.getSorting() == 1) {
		criteria.addOrder(Order.asc("price"));
	    } else if (clientFilter.getSorting() == 2) {
		criteria.addOrder(Order.desc("price"));
	    } else if (clientFilter.getSorting() == 3) {
		criteria.addOrder(Order.desc("category"));
	    }
	}
    }

}
