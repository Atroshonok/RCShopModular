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
     * Returns the products list using the product category id. This method
     * returns an empty collection if has not found any.
     * 
     * @param categoryId
     * @return
     */
    List<Product> getProductsByCategoryId(Serializable categoryId);

    /**
     * Returns the all products list. This method returns an empty collection if
     * has not found any.
     * 
     * @return
     */
    List<Product> getAllProducts();

    /**
     * Returns the product using its id. This method returns null if hasn't
     * found any.
     * 
     * @param productId
     * @return
     */
    Product getProductById(Serializable productId);

    /**
     * Updates the given product. This method throws the
     * com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException if
     * can't update the product.
     * 
     * @param product
     * @throws ErrorUpdatingPoductServiceException
     */
    void updateProduct(Product product) throws ErrorUpdatingPoductServiceException;

    /**
     * Saves the given product and returns its id in the success case. The
     * method throws a
     * com.atroshonok.services.exceptions.ErrorAddingPoductServiceException and
     * returns null in an other case.
     * 
     * @param product
     * @return
     * @throws ErrorAddingPoductServiceException
     */
    // ToDatabase is extra, please rename
    Serializable addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException;

    /**
     * Returns the products list according to the client filter parameters. This
     * method returns an empty collection if has not found any. The method
     * throws the com.atroshonok.services.exceptions.ServiceException if the
     * given client filter contains an invalid data.
     * 
     * @param clientFilter
     * @return
     * @throws ServiceException
     */
    List<Product> getProductsByClientFilter(ClientFilter clientFilter) throws ServiceException;

    /**
     * Returns the count of products.
     * 
     * @return
     */
    long getTotalProductsCount();

    /**
     * Returns the count of products according to the client filter parameters.
     * 
     * @param clientFilter
     * @return
     * @throws ServiceException
     */
    long getProductsCountAccordingClientFilter(ClientFilter clientFilter) throws ServiceException;

    /**
     * Deletes the given product. The method throws a
     * com.atroshonok.services.exceptions.ServiceException if can't delete the
     * product. The method throws the
     * com.atroshonok.services.exceptions.ServiceException if the given client
     * filter contains an invalid data.
     * 
     * @param product
     * @throws ServiceException
     */
    void deleteProduct(Product product) throws ServiceException;

}
