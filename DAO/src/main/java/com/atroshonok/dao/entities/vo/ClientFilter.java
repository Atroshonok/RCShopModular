/**
 * 
 */
package com.atroshonok.dao.entities.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Atroshonok
 */
public class ClientFilter {
    // the default client filter values
    // you can omit filter prefix in these varialbes, i.e. categoryIds, priceFrom, priceTo
    private List<Long> filterCategoriesId = new ArrayList<>();
    private Double filterPriceFrom = 0.0;
    private Double filterPriceTo = 100000.0;
    private Integer itemsPerPage = 1;
    private Integer currentPage = 1;
    // consider renaming to orderBy
    private Integer sorting = 0;

    public ClientFilter() {
	super();
    }

    /**
     * @return the filterCategoriesId
     */
    public List<Long> getFilterCategoriesId() {
	return filterCategoriesId;
    }

    /**
     * @param categoriesId
     *            the filterCategoriesId to set
     */
    public void setFilterCategoriesId(List<Long> categoriesId) {
	this.filterCategoriesId = categoriesId;
    }

    /**
     * @return the filterPriceFrom
     */
    public Double getFilterPriceFrom() {
	return filterPriceFrom;
    }

    /**
     * @param filterPriceFrom
     *            the filterPriceFrom to set
     */
    public void setFilterPriceFrom(Double filterPriceFrom) {
	this.filterPriceFrom = filterPriceFrom;
    }

    /**
     * @return the filterPriceTo
     */
    public Double getFilterPriceTo() {
	return filterPriceTo;
    }

    /**
     * @param filterPriceTo
     *            the filterPriceTo to set
     */
    public void setFilterPriceTo(Double filterPriceTo) {
	this.filterPriceTo = filterPriceTo;
    }

    /**
     * @return the itemsPerPage
     */
    public Integer getItemsPerPage() {
	return itemsPerPage;
    }

    /**
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
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(Integer currentPage) {
	this.currentPage = currentPage;
    }

    /**
     * @return the sorting
     */
    public Integer getSorting() {
	return sorting;
    }

    /**
     * @param sorting
     *            the sorting to set
     */
    public void setSorting(Integer sorting) {
	this.sorting = sorting;
    }

}
