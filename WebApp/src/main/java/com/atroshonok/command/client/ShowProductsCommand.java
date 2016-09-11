/**
 * 
 */
package com.atroshonok.command.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.dao.entities.ClientFilter;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.services.ProductCategoryService;
import com.atroshonok.services.ProductService;
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class ShowProductsCommand implements ActionCommand {

    private static final double CLIENT_FILTER_PRICE_TO_DEFAULT = 100000.0;
    private static final double CLIENT_FILTER_PRICE_FROM_DEFAULT = 0.0;

    private static final String REQUEST_PARAM_IS_FILTER_CHANGED = "isFilterChanged";
    private static final String REQUEST_PARAM_FILTER_CATEGORIES_ID_ALL = "filterCategoriesId[0]";
    private static final String REQUEST_PARAM_FILTER_CURRENT_PAGE = "currentPage";
    private static final String REQUEST_PARAM_FILTER_PRICE_FROM = "filterPriceFrom";
    private static final String REQUEST_PARAM_FILTER_PRICE_TO = "filterPriceTo";
    private static final String REQUEST_PARAM_FILTER_ITEMS_PER_PAGE = "itemsPerPage";
    private static final String REQUEST_PARAM_SORTING = "sorting";

    private static final String SESSION_ATTR_CLIENT_FILTER = "clientFilter";
    private static final String SESSION_ATTR_PRODUCT_CATEGORIES = "categories";
    private static final String SESSION_ATTR_PAGE_NUMBER_LIST = "pageNumberList";

    private List<ProductCategory> categories = null;

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
	// for navbar and client filter in JSP
	categories = ProductCategoryService.getInstance().getAllProductCategories();
	request.getSession().setAttribute(SESSION_ATTR_PRODUCT_CATEGORIES, categories);

	ClientFilter clientFilter = createClientFilterByRequestParam(request);

	List<Product> products = ProductService.getInstatnce().getProductsByClientFilter(clientFilter);
	request.getSession().setAttribute("productsList", products);

	// for pagination
	long totalPages = ProductService.getInstatnce().getCountAccordingClientFilter(clientFilter);
	List<Integer> pageNumberList = createPageNumberList(totalPages, request, clientFilter);
	request.getSession().setAttribute(SESSION_ATTR_PAGE_NUMBER_LIST, pageNumberList);

	String page = ConfigurationManager.getProperty("path.page.products");
	return page;
    }

    /* Returns the list of page numbers for the pagination */
    private List<Integer> createPageNumberList(long totalPages, HttpServletRequest request, ClientFilter clientFilter) {
	List<Integer> list = new ArrayList<>();
	long length = totalPages / clientFilter.getItemsPerPage();
	if (totalPages % clientFilter.getItemsPerPage() != 0) {
	    length = length + 1;
	}
	for (int i = 0; i < length; i++) {
	    list.add(i + 1);
	}
	return list;
    }

    private ClientFilter createClientFilterByRequestParam(HttpServletRequest request) {
	ClientFilter clientFilter = (ClientFilter) request.getSession().getAttribute(SESSION_ATTR_CLIENT_FILTER);
	if (clientFilter == null) {
	    clientFilter = new ClientFilter();
	    initClientFilterCategoriesId(clientFilter);
	} else {
	    setFilterParameters(request, clientFilter);
	}
	request.getSession().setAttribute(SESSION_ATTR_CLIENT_FILTER, clientFilter);
	return clientFilter;
    }

    private void initClientFilterCategoriesId(ClientFilter clientFilter) {
	clientFilter.setCategoriesId(new ArrayList<String>(categories.size()));
	clientFilter.getCategoriesId().add("all");
	for (int i = 1; i <= categories.size(); i++) {
	    clientFilter.getCategoriesId().add(String.valueOf(i));
	}
    }

    private void setFilterParameters(HttpServletRequest request, ClientFilter clientFilter) {
	String isFilterChanged = request.getParameter(REQUEST_PARAM_IS_FILTER_CHANGED);
	
	setItemsPerPageAndCurrentPage(request, clientFilter);
	
	List<String> oldCategoryList = clientFilter.getCategoriesId();
	setCategoriesId(request, clientFilter);
	if (!oldCategoryList.equals(clientFilter.getCategoriesId())) {
	    int pageNumber = 1;
	    clientFilter.setCurrentPage(pageNumber);
	}
	
	if (isFilterChanged != null) {
	    setPriceFrom(request, clientFilter);
	    setPriceTo(request, clientFilter);
	    setSortBy(request, clientFilter);
	}
    }


    private void setSortBy(HttpServletRequest request, ClientFilter clientFilter) {
	String sorting = request.getParameter(REQUEST_PARAM_SORTING);
	if (sorting != null) {
	    clientFilter.setSorting(Integer.parseInt(sorting));
	}
	
    }

    private void setItemsPerPageAndCurrentPage(HttpServletRequest request, ClientFilter clientFilter) {
	String requestItemsPerPage = request.getParameter(REQUEST_PARAM_FILTER_ITEMS_PER_PAGE);
	Integer newItemsPerPage = null;
	if (requestItemsPerPage != null) {
	    newItemsPerPage = Integer.parseInt(requestItemsPerPage);
	}
	if ((newItemsPerPage != null) && (newItemsPerPage != clientFilter.getItemsPerPage())) {
	    Integer oldStartPosition = (clientFilter.getCurrentPage() - 1) * clientFilter.getItemsPerPage();
	    Integer newCurrentPage = (oldStartPosition / newItemsPerPage) + 1;
	    clientFilter.setCurrentPage(newCurrentPage);
	    clientFilter.setItemsPerPage(newItemsPerPage);
	} else {
	    setRequestCurrentPage(request, clientFilter);
	}
    }

    private void setRequestCurrentPage(HttpServletRequest request, ClientFilter clientFilter) {
	String currentPage = request.getParameter(REQUEST_PARAM_FILTER_CURRENT_PAGE);
	if (currentPage != null) {
	    clientFilter.setCurrentPage(Integer.parseInt(currentPage));
	}
    }

    private void setPriceFrom(HttpServletRequest request, ClientFilter clientFilter) {
	String priceFrom = request.getParameter(REQUEST_PARAM_FILTER_PRICE_FROM);
	if ((priceFrom != null) && !(priceFrom.isEmpty())) {
	    clientFilter.setPriceFrom(Double.parseDouble(priceFrom));
	} else if (priceFrom.isEmpty()) {
	    clientFilter.setPriceFrom(CLIENT_FILTER_PRICE_FROM_DEFAULT);
	}
    }

    private void setPriceTo(HttpServletRequest request, ClientFilter clientFilter) {
	String priceTo = request.getParameter(REQUEST_PARAM_FILTER_PRICE_TO);
	if ((priceTo != null) && !(priceTo.isEmpty())) {
	    clientFilter.setPriceTo(Double.parseDouble(priceTo));
	} else if (priceTo.isEmpty()) {
	    clientFilter.setPriceTo(CLIENT_FILTER_PRICE_TO_DEFAULT);
	}
    }

    private void setCategoriesId(HttpServletRequest request, ClientFilter clientFilter) {
	List<String> chosenCategories = new ArrayList<>();
	String allCategory = request.getParameter(REQUEST_PARAM_FILTER_CATEGORIES_ID_ALL);
	if (allCategory != null) {
	    chosenCategories.add(0, allCategory);
	} else {
	    chosenCategories.add(0, "");
	}
	for (int i = 1; i <= categories.size(); i++) {
	    String categoryId = request.getParameter("filterCategoriesId[" + i + "]");
	    if (categoryId != null) {
		chosenCategories.add(i, categoryId);
	    } else {
		chosenCategories.add(i, "");
	    }
	}
	clientFilter.setCategoriesId(chosenCategories);
    }

}
