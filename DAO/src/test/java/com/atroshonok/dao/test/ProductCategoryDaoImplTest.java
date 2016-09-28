package com.atroshonok.dao.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

import com.atroshonok.dao.IProductCategoryDao;
import com.atroshonok.dao.entities.ProductCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-dao-test-config.xml")
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductCategoryDaoImplTest {

    @Autowired
    private IProductCategoryDao productCategoryDao;
    @Autowired
    private SessionFactory sessionFactory;

    private Session session;
    private ProductCategory categoryA;
    private ProductCategory categoryB;
    private ProductCategory categoryC;
    private List<ProductCategory> expectedCategories = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
	categoryA = TestEntityFactory.createProductCategory("Biks");
	categoryB = TestEntityFactory.createProductCategory("Battery");
	categoryC = TestEntityFactory.createProductCategory("UPS");

	session = sessionFactory.getCurrentSession();
	session.save(categoryA);
	session.save(categoryB);
	session.save(categoryC);

	expectedCategories.add(categoryA);
	expectedCategories.add(categoryB);
	expectedCategories.add(categoryC);

	session.flush();
	session.clear();

    }

    @After
    public void tearDown() throws Exception {
	session = sessionFactory.getCurrentSession();

	categoryA = (ProductCategory) session.get(ProductCategory.class, categoryA.getId());
	categoryB = (ProductCategory) session.get(ProductCategory.class, categoryB.getId());
	categoryC = (ProductCategory) session.get(ProductCategory.class, categoryC.getId());

	session.delete(categoryA);
	session.delete(categoryB);
	session.delete(categoryC);

	session.flush();
	session.clear();

	expectedCategories.clear();
    }

    @Test
    public void testGetAllProductCategories() {
	List<ProductCategory> categories = productCategoryDao.getAllProductCategories();
	assertTrue(categories.size() >= 3);
    }

}
