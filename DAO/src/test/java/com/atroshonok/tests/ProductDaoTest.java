package com.atroshonok.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.atroshonok.dao.IDao;
import com.atroshonok.dao.ProductDaoImpl;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;

public class ProductDaoTest extends BaseDaoCRUDTest<Product> {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Override
    protected IDao<Product> getEntityDao() {
	return new ProductDaoImpl();
    }

    @Override
    protected Product createAndInitEntity() {
	Product product = new Product();
	product.setName("Plane MIG-29");
	product.setCategory(new ProductCategory("Planes"));
	product.setCount(10);
	product.setPrice(200.4);
	product.setDescription("The good thing");
	return product;
    }

    @Override
    protected void changeEntity(Product entity) {
	entity.setCount(100);
	entity.setPrice(300.45);
    }

}
