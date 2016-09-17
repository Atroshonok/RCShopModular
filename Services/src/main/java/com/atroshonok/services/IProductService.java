package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.ClientFilter;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;

public interface IProductService {

    List<Product> getProductsByCategoryId(Serializable categoryId);

    List<Product> getAllProducts();

    Product getProductById(Serializable productId);

    void updateProduct(Product product) throws ErrorUpdatingPoductServiceException;

    void addNewProductToDatabase(Product product) throws ErrorAddingPoductServiceException;

    List<Product> getProductsByClientFilter(ClientFilter clientFilter);

    Long getTotalProductsCount();

    long getProductsCountAccordingClientFilter(ClientFilter clientFilter);

}
