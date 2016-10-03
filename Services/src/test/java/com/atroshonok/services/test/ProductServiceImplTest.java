package com.atroshonok.services.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.services.IProductService;
import com.atroshonok.services.exceptions.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-services-test-config.xml"})
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductServiceImplTest {
    
    @Autowired
    private IProductService productService;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void testGetProductsByCategoryId() {
	fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public void testGetAllProducts() {
	fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public void testGetProductById() {
	fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public void testUpdateProduct() {
	fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public void testAddNewProductToDatabase() {
	fail("Not yet implemented"); // TODO
    }

    @Test(expected = ServiceException.class)
    public void testGetProductsByClientFilter() throws ServiceException {
	ClientFilter filter = createNewClientFilter();
	setInvalidData(filter);
	productService.getProductsByClientFilter(filter);
    }
    
    @Test(expected = ServiceException.class)
    public void testGetProductsByClientFilterWithNullFilter() throws ServiceException {
	ClientFilter filter = null;
	productService.getProductsByClientFilter(filter);
	
    }
    
    @Test(expected = ServiceException.class)
    public void testGetProductsByClientFilterWithWrongFilter() throws ServiceException {
	ClientFilter filterB = new ClientFilter();
	filterB.getFilterCategoriesId().add(1L);
	filterB.setCurrentPage(-1);
	productService.getProductsByClientFilter(filterB);
    }
    
    @Test
    public void testGetProductsByClientFilterWithValidFilter() throws ServiceException {
	ClientFilter filterB = new ClientFilter();
	filterB.getFilterCategoriesId().add(1L);
	filterB.setCurrentPage(1);
	filterB.setFilterPriceFrom(0.0);
	filterB.setFilterPriceTo(1000.0);
	filterB.setItemsPerPage(1);
	filterB.setSorting(0);
	
	List<Product> actualProducts = productService.getProductsByClientFilter(filterB);
	assertTrue(actualProducts.isEmpty());
    }

    private ClientFilter createNewClientFilter() {
	ClientFilter filter = new ClientFilter();
	return filter;
    }

    private void setInvalidData(ClientFilter filter) {
	filter.setCurrentPage(-1);
	filter.setFilterCategoriesId(null);
	filter.setFilterPriceFrom((double) -2);
	filter.setFilterPriceTo((double) -8);
	filter.setItemsPerPage(0);
	filter.setSorting(-1);
    }

    @Ignore
    @Test
    public void testGetTotalProductsCount() {
	fail("Not yet implemented"); // TODO
    }

    @Test(expected = ServiceException.class)
    public void testGetProductsCountAccordingClientFilter() throws ServiceException {
	ClientFilter filter = createNewClientFilter();
	setInvalidData(filter);
	productService.getProductsCountAccordingClientFilter(filter);
    }

    @Ignore
    @Test
    public void testDeleteProduct() {
	fail("Not yet implemented"); // TODO
    }

}
