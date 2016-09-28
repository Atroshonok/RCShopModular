package com.atroshonok.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IProductCategoryDao;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.exceptions.DaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-dao-test-config.xml")
@Transactional(propagation = Propagation.SUPPORTS)
public class DaoImplTest {
    private static final long WRONG_CATEGORY_ID = 100L;
    @Autowired
    private IProductCategoryDao productCategoryDao;
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session session;
    private ProductCategory categoryA;
    private ProductCategory categoryB;
    private ProductCategory categoryC;
    private ProductCategory categoryD;
    private ProductCategory categoryE;


    @Test
    public void testSaveOrUpdate() throws DaoException {
	categoryA = TestEntityFactory.createProductCategory("Planes");
	session = sessionFactory.getCurrentSession();
	session.save(categoryA);
	flushAndClearSession();
	
	categoryA.setCategoryName("Helicopters");
	productCategoryDao.saveOrUpdate(categoryA);
	
	flushAndClearSession();
	ProductCategory actualCategoryA = (ProductCategory) session.get(ProductCategory.class, categoryA.getId());
	
	assertEquals(categoryA, actualCategoryA);
    }

    private void flushAndClearSession() {
	session.flush();
	session.clear();
    }

    @Test
    public void testSave() throws DaoException {
	session = sessionFactory.getCurrentSession();
	categoryB = TestEntityFactory.createProductCategory("Phones");
	Long categoryId = (Long) productCategoryDao.save(categoryB);
	categoryB.setId(categoryId);
	
	flushAndClearSession();
	ProductCategory actualCategoryB = (ProductCategory) session.get(ProductCategory.class, categoryId);
	
	assertEquals(categoryB, actualCategoryB);
	
	flushAndClearSession();
    }

    @Test
    public void testGet() throws DaoException {
	categoryC = TestEntityFactory.createProductCategory("Cars");
	session = sessionFactory.getCurrentSession();
	session.save(categoryC);
	flushAndClearSession();
	ProductCategory actualCategoryC = productCategoryDao.get(categoryC.getId());
	assertEquals(categoryC, actualCategoryC);
	
	ProductCategory actualCategoryC1 = productCategoryDao.get(WRONG_CATEGORY_ID);
	assertNull(actualCategoryC1);
    }

    @Test
    public void testLoad() throws DaoException {
	categoryD = TestEntityFactory.createProductCategory("FPV");
	session = sessionFactory.getCurrentSession();
	session.save(categoryD);
	flushAndClearSession();
	ProductCategory actualCategoryD = productCategoryDao.load(categoryD.getId());
	assertEquals(categoryD, actualCategoryD);
    }
    
    @Test(expected = DaoException.class)
    public void testLoadWrong() throws DaoException {
	productCategoryDao.load(WRONG_CATEGORY_ID);
    }

    @Test
    public void testDelete() throws DaoException {
	categoryE = TestEntityFactory.createProductCategory("Gliders");
	session = sessionFactory.getCurrentSession();
	session.save(categoryE);
	flushAndClearSession();
	categoryE = (ProductCategory) session.get(ProductCategory.class, categoryE.getId());
	
	productCategoryDao.delete(categoryE);
	flushAndClearSession();
	
	ProductCategory actualCategoryE = (ProductCategory) session.get(ProductCategory.class, categoryE.getId());
	
	assertNull(actualCategoryE);
    }

}
