package com.atroshonok.tests;

import org.hibernate.SQLQuery;
import org.junit.AfterClass;

import com.atroshonok.dao.Dao;
import com.atroshonok.dao.ProductDao;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;

public class ProductDaoTest extends BaseDaoCRUDTest<Product> {

    @Override
    protected Dao<Product> getEntityDao() {
	return new ProductDao();
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
    
    /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	dropEntityTable();
	util.getSession().close();
  }

    private static void dropEntityTable() {
	SQLQuery sqlQuery = util.getSession().createSQLQuery("DROP TABLE products;");
	sqlQuery.executeUpdate();
	SQLQuery sqlQuery2 = util.getSession().createSQLQuery("DROP TABLE product_categories;");
	sqlQuery2.executeUpdate();
    }

}
