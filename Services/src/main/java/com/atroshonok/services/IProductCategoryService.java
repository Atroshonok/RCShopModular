package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.ProductCategory;

public interface IProductCategoryService {

    /**
     * Returns an object of com.atroshonok.dao.entities.ProductCategory class
     * using the specified category id. The method returns null if hasn't found
     * any.
     * 
     * @param categoryId
     * @return
     */
    ProductCategory getCategoryById(Serializable categoryId);

    /**
     * Returns the list of the all product categories. This method returns an
     * empty collection if hasn't found any.
     * 
     * @return
     */
    List<ProductCategory> getAllProductCategories();

}
