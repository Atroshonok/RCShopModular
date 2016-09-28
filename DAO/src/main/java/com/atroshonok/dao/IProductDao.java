package com.atroshonok.dao;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;

public interface IProductDao extends IDao<Product> {

    /**
     * Returns an objects list of com.atroshonok.dao.entities.Product class
     * containing the all products. This method returns an empty collection if
     * has not found any.
     * 
     * @return
     */
    List<Product> getAllProducts();

    /**
     * Returns an objects list of com.atroshonok.dao.entities.Product class
     * using the product category id. This method returns an empty collection if
     * has not found any.
     * 
     * @param categoryId
     * @return
     */
    List<Product> getProductsByCategoryId(Serializable categoryId);

    /**
     * Returns an objects list of com.atroshonok.dao.entities.Product class
     * according to the client filter parameters. This method returns an empty
     * collection if has not found any.
     * 
     * @param clientFilter.
     * @return
     */
    List<Product> getProductsByClientFilter(ClientFilter clientFilter);

    /**
     * Returns a number of the all products.
     * 
     * @return
     */
    long getTotalProductsCount();

    /**
     * Returns the number of products according to the client filter parameters.
     * 
     * @param clientFilter
     * @return
     */
    long getProductsCountAccordingClientFilter(ClientFilter clientFilter);

}
