/**
 * 
 */
package com.atroshonok.dao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.entities.vo.ClientFilter;

/**
 * @author Ivan Atroshonok
 *
 */
public class TestEntityFactory {
    
    public static ClientFilter createClientFilter(Long ...categoriesId) {
	ClientFilter clientFilter = new ClientFilter();
	List<Long> categoriesIdList = new ArrayList<>();
	categoriesIdList.addAll(Arrays.asList(categoriesId));
	
	clientFilter.setFilterCategoriesId(categoriesIdList);
	Integer itemsPerPage = 1;
	clientFilter.setItemsPerPage(itemsPerPage);
	
	return clientFilter;
    }
    
    public static Product createProduct(ProductCategory productCategory, String name) {
	Product product = new Product();
	product.setCategory(productCategory);
	product.setCount(10);
	product.setPrice(100.50);
	product.setName(name);
	product.setDescription("");
	return product;
    }

    public static ProductCategory createProductCategory(String categoryName) {
	ProductCategory productCategory = new ProductCategory();
	productCategory.setCategoryName(categoryName);
	return productCategory;
    }

}
