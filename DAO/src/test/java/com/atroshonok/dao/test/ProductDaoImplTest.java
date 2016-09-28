package com.atroshonok.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IProductDao;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.entities.vo.ClientFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-dao-test-config.xml")
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductDaoImplTest {

    @Autowired
    private IProductDao productDao;
    @Autowired
    private SessionFactory sessionFactory;

    private Product expectedProductA;
    private Product expectedProductB;
    private Product expectedProductC;
    private ProductCategory categoryA;
    private ProductCategory categoryB;
    private Session session;

    @Before
    public void setUp() throws Exception {
	categoryA = TestEntityFactory.createProductCategory("Planes");
	categoryB = TestEntityFactory.createProductCategory("Cars");

	createExpectedProdutcs(categoryA, categoryB);

	session = sessionFactory.getCurrentSession();
	saveProductCategories();
	saveProducts();
	session.flush();
	session.clear();
    }

    @After
    public void tearDown() throws Exception {
	session = sessionFactory.getCurrentSession();

	getExpectedProducts();
	getExpectedCategories();

	deleteCategories();
	deleteProducts();

	session.flush();
	session.clear();
    }

    @Test
    public void testGetAllProducts() {
	List<Product> products = productDao.getAllProducts();

	Product actualProductsA = products.get(0);
	Product actualProductsB = products.get(1);
	Product actualProductsC = products.get(2);

	assertEquals(expectedProductA, actualProductsA);
	assertEquals(expectedProductB, actualProductsB);
	assertEquals(expectedProductC, actualProductsC);
    }

    @Test
    public void testGetProductsByCategoryId() {
	Serializable categoryId = categoryA.getId();
	List<Product> products = productDao.getProductsByCategoryId(categoryId);

	Product actualProductsA = products.get(0);
	Product actualProductsB = products.get(1);

	assertEquals(expectedProductA, actualProductsA);
	assertEquals(expectedProductB, actualProductsB);
	assertTrue(products.size() == 2);
    }

    @Test
    public void testGetTotalProductsCount() {
	long actuallTotalCount = productDao.getTotalProductsCount();
	assertTrue(actuallTotalCount == 3);
    }

    @Test
    public void testGetProductsCountAccordingClientFilter() {
	ClientFilter clientFilter = TestEntityFactory.createClientFilter(categoryA.getId());
	long actualCountA = productDao.getProductsCountAccordingClientFilter(clientFilter);
	assertTrue(actualCountA == 2);
    }

    @Test
    public void testGetProductsByClientFilter() {
	ClientFilter clientFilter = TestEntityFactory.createClientFilter(categoryA.getId());

	List<Product> products = productDao.getProductsByClientFilter(clientFilter);
	Product actualProductsA = products.get(0);
	assertTrue(products.size() == 1);
	assertEquals(expectedProductA, actualProductsA);

	Integer itemsPerPage = 3;
	clientFilter.setItemsPerPage(itemsPerPage);
	List<Product> productsB = productDao.getProductsByClientFilter(clientFilter);
	Product actualProductsA1 = productsB.get(0);
	Product actualProductsB1 = productsB.get(1);
	assertTrue(productsB.size() == 2);
	assertEquals(expectedProductA, actualProductsA1);
	assertEquals(expectedProductB, actualProductsB1);
    }

    private void saveProducts() {
	session.save(expectedProductA);
	session.save(expectedProductB);
	session.save(expectedProductC);
    }

    private void saveProductCategories() {
	session.save(categoryA);
	session.save(categoryB);
    }

    private void createExpectedProdutcs(ProductCategory categoryA, ProductCategory categoryB) {
	expectedProductA = TestEntityFactory.createProduct(categoryA, "MIG-29");
	expectedProductB = TestEntityFactory.createProduct(categoryA, "MIG-31");
	expectedProductC = TestEntityFactory.createProduct(categoryB, "BMW");
    }

    private void deleteProducts() {
	session.delete(expectedProductA);
	session.delete(expectedProductB);
	session.delete(expectedProductC);
    }

    private void deleteCategories() {
	session.delete(categoryA);
	session.delete(categoryB);
    }

    private void getExpectedCategories() {
	categoryA = (ProductCategory) session.get(ProductCategory.class, categoryA.getId());
	categoryB = (ProductCategory) session.get(ProductCategory.class, categoryB.getId());
    }

    private void getExpectedProducts() {
	expectedProductA = (Product) session.get(Product.class, expectedProductA.getId());
	expectedProductB = (Product) session.get(Product.class, expectedProductB.getId());
	expectedProductC = (Product) session.get(Product.class, expectedProductC.getId());
    }

}
