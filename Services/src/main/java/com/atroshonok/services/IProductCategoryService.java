package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.ProductCategory;

public interface IProductCategoryService {

    ProductCategory getCategoryById(Serializable categoryId);

    List<ProductCategory> getAllProductCategories();

}
