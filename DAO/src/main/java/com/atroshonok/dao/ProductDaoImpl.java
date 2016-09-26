/**
 * 
 */
package com.atroshonok.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class ProductDaoImpl extends DaoImpl<Product> implements IProductDao {

    @Autowired
    private MessageSource messageSource;

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
	List<Product> products = new ArrayList<>();
	try {
	    String hql = "FROM Product p WHERE p.category.id=:productCategoryId";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("productCategoryId", categoryId);
	    products = query.list();
	} catch (HibernateException e) {
	    log.error("Error getting products by category = " + categoryId);
	    throw new DaoException(messageSource.getMessage("error.product.get", null, Locale.getDefault()), e);
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

    private void createCriteriaAccordingClientFilter(ClientFilter clientFilter, Criteria criteria) {
	Disjunction disjun = Restrictions.disjunction();
	for (int i = 0; i < clientFilter.getFilterCategoriesId().size(); i++) {
	    Long categoryId = clientFilter.getFilterCategoriesId().get(i);
	    Criterion criterion = (Criterion) Restrictions.eq("category.id", categoryId);
	    disjun.add(criterion);
	}
	criteria.add(Restrictions.or(disjun));

	Double priceFrom = clientFilter.getFilterPriceFrom();
	Double priceTo = clientFilter.getFilterPriceTo();

	if (priceFrom > 0) {
	    criteria.add(Restrictions.gt("price", priceFrom));
	}
	if ((priceTo > 0) && (priceTo >= priceFrom)) {
	    criteria.add(Restrictions.le("price", priceTo));
	}
	if (clientFilter.getSorting() != 0) {
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
