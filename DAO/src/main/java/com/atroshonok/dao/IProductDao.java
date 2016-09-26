package com.atroshonok.dao;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.dao.exceptions.DaoException;

public interface IProductDao extends IDao<Product> {
    
    List<Product> getAllProducts();

    List<Product> getProductsByCategoryId(Serializable categoryId) throws DaoException;

    List<Product> getProductsByClientFilter(ClientFilter clientFilter);

    Long getTotalProductsCount();

    long getProductsCountAccordingClientFilter(ClientFilter clientFilter);

}
