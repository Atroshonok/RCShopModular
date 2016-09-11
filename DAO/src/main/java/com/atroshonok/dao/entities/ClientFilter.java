/**
 * 
 */
package com.atroshonok.dao.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Atroshonok
 */
public class ClientFilter {
    // the default client filter values
    private List<String> categoriesId = null;
    private Double priceFrom = 0.0;
    private Double priceTo = 100000.0;
    private Integer itemsPerPage = 1;
    private Integer currentPage = 1;
    private Integer sorting = 0;

    public ClientFilter() {
	super();
    }

    /**
     * @return the categoriesId
     */
    public List<String> getCategoriesId() {
	return categoriesId;
    }

    /**
     * @param categoriesId
     *            the categoriesId to set
     */
    public void setCategoriesId(List<String> categoriesId) {
	this.categoriesId = categoriesId;
    }

    /**
     * @return the priceFrom
     */
    public Double getPriceFrom() {
	return priceFrom;
    }

    /**
     * @param priceFrom
     *            the priceFrom to set
     */
    public void setPriceFrom(Double priceFrom) {
	this.priceFrom = priceFrom;
    }

    /**
     * @return the priceTo
     */
    public Double getPriceTo() {
	return priceTo;
    }

    /**
     * @param priceTo
     *            the priceTo to set
     */
    public void setPriceTo(Double priceTo) {
	this.priceTo = priceTo;
    }

    /**
     * @return the itemsPerPage
     */
    public Integer getItemsPerPage() {
	return itemsPerPage;
    }

    /**
     * 
     * @param itemsPerPage
     *            the itemsPerPage to set
     */
    public void setItemsPerPage(Integer itemsPerPage) {
	this.itemsPerPage = itemsPerPage;
    }

    /**
     * @return the currentPage
     */
    public Integer getCurrentPage() {
	return currentPage;
    }

    /**
     * 
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(Integer currentPage) {
	this.currentPage = currentPage;
    }

    public Integer getSorting() {
	return sorting;
    }

    public void setSorting(Integer sorting) {
	this.sorting = sorting;
    }

}
