package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;
import com.atroshonok.services.exceptions.ServiceException;

public interface IProductService {
    /**
     * 
     * @param categoryId
     * @return
     */
    List<Product> getProductsByCategoryId(Serializable categoryId);

    /**
     * 
     * @return
     */
    List<Product> getAllProducts();

    /**
     * 
     * @param productId
     * @return
     */
    Product getProductById(Serializable productId);

    /**
     * 
     * @param product
     * @throws ErrorUpdatingPoductServiceException
     */
    void updateProduct(Product product) throws ErrorUpdatingPoductServiceException;

    /**
     * 
     * @param product
     * @return
     * @throws ErrorAddingPoductServiceException
     */
    Serializable addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException;

    /**
     * 
     * @param clientFilter
     * @return
     */
    List<Product> getProductsByClientFilter(ClientFilter clientFilter);

    /**
     * 
     * @return
     */
    Long getTotalProductsCount();

    /**
     * 
     * @param clientFilter
     * @return
     */
    long getProductsCountAccordingClientFilter(ClientFilter clientFilter);

    /**
     * 
     * @param product
     * @throws ServiceException 
     */
    void deleteProduct(Product product) throws ServiceException;

}
