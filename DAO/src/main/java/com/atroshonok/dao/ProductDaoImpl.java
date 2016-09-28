/**
 * 
 */
package com.atroshonok.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;

/**
 * @author Ivan Atroshonok
 *
 */

@Repository
public class ProductDaoImpl extends DaoImpl<Product> implements IProductDao {

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
    public List<Product> getProductsByCategoryId(Serializable categoryId) {
	List<Product> products = new ArrayList<>();
	String hql = "FROM Product p WHERE p.category.id=:productCategoryId";
	Query query = getSession().createQuery(hql);
	query.setParameter("productCategoryId", categoryId);
	products = query.list();
	return products;
    }

    @Override
    public long getTotalProductsCount() {
	String hql = "SELECT count(*) FROM Product p";
	Query query = getSession().createQuery(hql);
	long totalCount = (long) query.uniqueResult();
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
	Criteria criteria = getSession().createCriteria(Product.class);
	createCriteriaAccordingClientFilter(clientFilter, criteria);

	int startPosition = (clientFilter.getCurrentPage() - 1) * clientFilter.getItemsPerPage();
	criteria.setFirstResult(startPosition);

	int maxResult = clientFilter.getItemsPerPage();
	criteria.setMaxResults(maxResult);

	List<Product> results = criteria.list();
	return results;
    }

    private void createCriteriaAccordingClientFilter(ClientFilter clientFilter, Criteria criteria) {
	
	addCategoryIdRestrictions(clientFilter, criteria);

	addPriceRestriction(clientFilter, criteria);
	
	addSortingConditions(clientFilter, criteria);
    }

    private void addSortingConditions(ClientFilter clientFilter, Criteria criteria) {
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

    private void addPriceRestriction(ClientFilter clientFilter, Criteria criteria) {
	Double priceFrom = clientFilter.getFilterPriceFrom();
	if (priceFrom > 0) {
	    criteria.add(Restrictions.gt("price", priceFrom));
	}
	
	Double priceTo = clientFilter.getFilterPriceTo();
	if ((priceTo > 0) && (priceTo >= priceFrom)) {
	    criteria.add(Restrictions.le("price", priceTo));
	}
    }

    private void addCategoryIdRestrictions(ClientFilter clientFilter, Criteria criteria) {
	Disjunction disjun = Restrictions.disjunction();
	for (int i = 0; i < clientFilter.getFilterCategoriesId().size(); i++) {
	    Long categoryId = clientFilter.getFilterCategoriesId().get(i);
	    Criterion criterion = (Criterion) Restrictions.eq("category.id", categoryId);
	    disjun.add(criterion);
	}
	criteria.add(Restrictions.or(disjun));
    }

}
