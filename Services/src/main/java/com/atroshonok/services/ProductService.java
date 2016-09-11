/**
 * 
 */
package com.atroshonok.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.atroshonok.dao.ProductDao;
import com.atroshonok.dao.dbutils.ErrorMessageManager;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.ClientFilter;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.exceptions.DaoException;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;

/**
 * @author Atroshonok Ivan
 *
 */
public class ProductService {
    private static ProductService productService;
    private Logger log = Logger.getLogger(getClass());
    private HibernateUtil util = HibernateUtil.getInstance();
    private ProductDao productDao = new ProductDao();
    private Session session = null;
    private Transaction transaction = null;

    private ProductService() {
	super();
    }

    public static ProductService getInstatnce() {
	if (productService == null) {
	    productService = new ProductService();
	}
	return productService;
    }

    public List<Product> getProductsByCategoryId(long categoryId) {
	log.info("Starting method getProductsByCategoryId(long categoryId) in " + ProductService.class);
	List<Product> products = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    String hql = "FROM Product p WHERE p.category.id=:productCategoryId";
	    Query query = session.createQuery(hql);
	    query.setParameter("productCategoryId", categoryId);
	    products = query.list();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting products by category = " + categoryId + " in class: " + ProductService.class, e);
	    transaction.rollback();
	}
	log.info("Ending method getProductsByCategoryId(long categoryId) in " + ProductService.class);
	return products;
    }

    public List<Product> getAllProducts() {
	log.info("Starting method getAllProducts() in " + ProductService.class);
	List<Product> products = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    products = productDao.getAllProducts();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting product list in class: " + ProductService.class, e);
	    transaction.rollback();
	}
	log.info("Ending method getAllProducts() in " + ProductService.class);
	return products;
    }

    public Product getProductById(long productId) {
	log.info("Starting method getProductById(long productId)");
	Product product = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    product = productDao.get(productId);
	    transaction.commit();
	    log.info("Got product: " + product);
	} catch (DaoException e) {
	    log.error("Error getting product by id = " + productId + " in class: " + ProductService.class, e);
	    transaction.rollback();
	}
	log.info("Ending method getProductById(long productId)");
	return product;
    }

    public void updateProduct(Product product) throws ErrorUpdatingPoductServiceException {
	log.info("Starting method updateProductInDatabase(Product product)");
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    productDao.saveOrUpdate(product);
	    transaction.commit();
	    log.info("Updated product: " + product);
	} catch (DaoException e) {
	    log.error("Error updating product in class: " + ProductService.class, e);
	    transaction.rollback();
	    throw new ErrorUpdatingPoductServiceException(ErrorMessageManager.getProperty("error.update.product"));
	}
	log.info("Ending method updateProductInDatabase(Product product)");
    }

    public void addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException {
	log.info("Starting method addNewProductToDatabase(Product product)");
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    productDao.saveOrUpdate(product);
	    transaction.commit();
	    log.info("Saved product to DB: " + product);
	} catch (DaoException e) {
	    log.error("Error saving product to database: ", e);
	    transaction.rollback();
	    throw new ErrorAddingPoductServiceException(ErrorMessageManager.getProperty("error.save.product"));
	}
	log.info("Ending method addNewProductToDatabase(Product product)");
    }

    public List<Product> getProductsByClientFilter(ClientFilter clientFilter) {
	List<Product> results = null;

	try {
	    session = util.getSession();

	    Criteria criteria = session.createCriteria(Product.class);
	    createCriteriaAccordingClientFilter(clientFilter, criteria);

	    int startPosition = (clientFilter.getCurrentPage() - 1) * clientFilter.getItemsPerPage();
	    criteria.setFirstResult(startPosition);

	    int maxResult = clientFilter.getItemsPerPage();
	    criteria.setMaxResults(maxResult);

	    transaction = session.beginTransaction();
	    results = criteria.list();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting the products according to the client filter parameters", e);
	    transaction.rollback();
	}
	return results;
    }

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

    public Long getTotalCount() {
	Long totalCount = null;
	try {
	    session = util.getSession();
	    transaction = session.beginTransaction();
	    String hql = "SELECT count(*) FROM Product p";
	    Query query = session.createQuery(hql);
	    totalCount = (Long) query.uniqueResult();
	    transaction.commit();
	} catch (HibernateException e) {
	    log.error("Error getting total product count", e);
	    transaction.rollback();
	}
	return totalCount;
    }

    public long getCountAccordingClientFilter(ClientFilter clientFilter) {
	long result = 0L;
	try {
	    session = util.getSession();

	    Criteria criteria = session.createCriteria(Product.class);
	    createCriteriaAccordingClientFilter(clientFilter, criteria);
	    criteria.setProjection(Projections.rowCount());

	    transaction = session.beginTransaction();
	    result = (long) criteria.uniqueResult();
	    transaction.commit();
	} catch (HibernateException e) {
	    transaction.rollback();
	}
	return result;
    }
}
