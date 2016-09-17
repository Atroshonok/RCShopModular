package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.ProductCategory;

public interface IProductCategoryDao extends IDao<ProductCategory> {
    List<ProductCategory> getAllProductCategories();
}
