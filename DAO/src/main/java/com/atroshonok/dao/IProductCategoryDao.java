package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.ProductCategory;

public interface IProductCategoryDao extends IDao<ProductCategory> {
    /**
     * Returns an objects list of com.atroshonok.dao.entities.ProductCategory
     * class that contains all of the product categories. The method returns an
     * empty collection if has found no one.
     * 
     * @return
     */
    List<ProductCategory> getAllProductCategories();
}
