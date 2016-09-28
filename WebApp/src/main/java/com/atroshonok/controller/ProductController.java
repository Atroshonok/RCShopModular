package com.atroshonok.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.entities.vo.ClientFilter;
import com.atroshonok.dao.entities.vo.ProductVO;
import com.atroshonok.services.IProductCategoryService;
import com.atroshonok.services.IProductService;
import com.atroshonok.services.exceptions.ErrorAddingPoductServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;
import com.atroshonok.services.exceptions.ServiceException;
import com.atroshonok.utilits.AdminConfigManager;

/**
 * 
 * @author Ivan Atroshonok
 *
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private static Logger log = Logger.getLogger(ProductController.class);

    private static final String ATTRIBUTE_CATEGORY_LIST = "categoryList";
    private static final String ATTRIBUTE_FRAGMENT_PATH = "fragmentPath";
    private static final String REQUEST_ATTR_INFO_MESSAGE = "infoMessage";

    private static final double CLIENT_FILTER_PRICE_TO_DEFAULT = 100000.0;
    private static final double CLIENT_FILTER_PRICE_FROM_DEFAULT = 0.0;

    private static final String REQUEST_PARAM_IS_FILTER_CHANGED = "isFilterChanged";
    private static final String REQUEST_PARAM_IS_CATEGORY_CHANGED = "isCategoriesChanged";
    private static final String REQUEST_PARAM_FILTER_CURRENT_PAGE = "currentPage";
    private static final String REQUEST_PARAM_FILTER_PRICE_FROM = "filterPriceFrom";
    private static final String REQUEST_PARAM_FILTER_PRICE_TO = "filterPriceTo";
    private static final String REQUEST_PARAM_FILTER_ITEMS_PER_PAGE = "itemsPerPage";
    private static final String REQUEST_PARAM_SORTING = "sorting";

    private static final String SESSION_ATTR_PRODUCT_LIST = "productsList";
    private static final String SESSION_ATTR_CLIENT_FILTER = "clientFilter";
    private static final String SESSION_ATTR_PRODUCT_CATEGORIES = "categories";
    private static final String SESSION_ATTR_PAGE_NUMBER_LIST = "pageNumberList";

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductCategoryService productCategoryService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String showProducts(HttpServletRequest request, HttpSession session) {

	List<ProductCategory> categories = productCategoryService.getAllProductCategories();
	session.setAttribute(SESSION_ATTR_PRODUCT_CATEGORIES, categories);

	ClientFilter clientFilter = createClientFilterByRequestParam(request, session, categories);

	List<Product> products = productService.getProductsByClientFilter(clientFilter);
	session.setAttribute(SESSION_ATTR_PRODUCT_LIST, products);

	// for pagination
	long totalPages = productService.getProductsCountAccordingClientFilter(clientFilter);
	List<Integer> pageNumberList = createPageNumberList(totalPages, request, clientFilter);
	session.setAttribute(SESSION_ATTR_PAGE_NUMBER_LIST, pageNumberList);

	return "products";
    }

    /**
     * 
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(path = "/reset", method = RequestMethod.GET)
    public String resetClientFilter(HttpServletRequest request, HttpSession session) {
	session.removeAttribute(SESSION_ATTR_CLIENT_FILTER);
	return "redirect:all";
    }

    /**
     * 
     * @param productId
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(path = "/delete/{productId}", method = RequestMethod.GET)
    public String getDeteteProductDialog(@PathVariable("productId") Long productId, Model model, Locale locale) {
	Product product = productService.getProductById(productId);
	model.addAttribute("product", product);
	model.addAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.product.before-deleting", locale) + product.toString());
	model.addAttribute(ATTRIBUTE_FRAGMENT_PATH, AdminConfigManager.getProperty("path.fragment.delproductdialog"));
	return "admin";
    }

    /**
     * 
     * @param productId
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @RequestMapping(path = "/delete/{productId}", method = RequestMethod.POST)
    public String deteteProductFromDB(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes, Locale locale) {
	Product product = productService.getProductById(productId);
	try {
	    productService.deleteProduct(product);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.product.deteted", locale));
	} catch (ServiceException e) {
	    log.error("Error deleting product: " + product, e);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.product.delete.error", locale));
	}
	return "redirect:/products/admin";
    }

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String getAddNewProductForm(Model model) {
	ProductVO newProduct = new ProductVO();
	model.addAttribute("newProduct", newProduct);
	List<ProductCategory> categoryList = productCategoryService.getAllProductCategories();
	model.addAttribute(ATTRIBUTE_CATEGORY_LIST, categoryList);
	model.addAttribute(ATTRIBUTE_FRAGMENT_PATH, AdminConfigManager.getProperty("path.fragment.addproductform"));
	return "admin";
    }

    /**
     * 
     * @param newProduct
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String saveNewProduct(ProductVO newProduct, @RequestParam(value = "productImage", required = false) MultipartFile image, RedirectAttributes redirectAttributes, Locale locale) {
	Product product = new Product();
	setProductFields(product, newProduct);
	try {
	    Long productId = (Long) productService.addNewProductToDatabase(product);
	    if ((image != null) && !image.isEmpty()) {
		validateImage(image);
		saveImage("product-" + productId + ".jpg", image);
	    }
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.newproductadded", locale));
	} catch (IOException e) {
	    log.error("Error saving product image. ", e);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, e.getMessage());
	} catch (ErrorAddingPoductServiceException e) {
	    log.error("Error adding new product to DB", e);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.error.addnewproduct ", locale));
	}
	return "redirect:admin";
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage() {
	return "admin";
    }

    /**
     * 
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping(path = "/edit/{productId}", method = RequestMethod.GET)
    public String getEditProductForm(@PathVariable Long productId, Model model) {
	Product product = productService.getProductById(productId);
	ProductVO editedProduct = new ProductVO(product);
	List<ProductCategory> categoryList = productCategoryService.getAllProductCategories();
	model.addAttribute(ATTRIBUTE_CATEGORY_LIST, categoryList);
	model.addAttribute("editedProduct", editedProduct);
	model.addAttribute(ATTRIBUTE_FRAGMENT_PATH, AdminConfigManager.getProperty("path.fragment.editproductform"));
	return "admin";
    }

    /**
     * 
     * @param editedProduct
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String saveEditedProduct(ProductVO editedProduct, RedirectAttributes redirectAttributes, Locale locale) {
	Product product = productService.getProductById(editedProduct.getId());
	changheProductFields(product, editedProduct);
	try {
	    productService.updateProduct(product);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.productupdated", locale));
	} catch (ErrorUpdatingPoductServiceException e) {
	    log.error("Error updating product: " + product, e);
	    redirectAttributes.addFlashAttribute(REQUEST_ATTR_INFO_MESSAGE, getMessageByKey("message.error.updateproduct", locale));
	}
	return "redirect:admin";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
	log.error("Exception occured. ", e);
	ModelAndView mav = new ModelAndView();
	mav.addObject("error", e.getMessage());
	mav.setViewName("error/error");
	return mav;
    }
    
    private void saveImage(String filename, MultipartFile image) throws IOException {
	File file = new File(AdminConfigManager.getProperty("path.images") + filename);
	FileUtils.writeByteArrayToFile(file, image.getBytes());
    }

    private void validateImage(MultipartFile image) throws IOException {
	if (!image.getContentType().equals("image/jpeg")) {
	    throw new IOException("Only JPEG images accepted");
	}
    }

    private void setProductFields(Product product, ProductVO newProduct) {
	product.setName(newProduct.getName());
	product.setPrice(newProduct.getPrice());
	product.setCount(newProduct.getCount());
	product.setDescription(newProduct.getDescription());
	Long categoryId = newProduct.getProductCategoryId();
	ProductCategory category = productCategoryService.getCategoryById(categoryId);
	product.setCategory(category);
    }

    private ClientFilter createClientFilterByRequestParam(HttpServletRequest request, HttpSession session, List<ProductCategory> categories) {
	ClientFilter clientFilter = (ClientFilter) session.getAttribute(SESSION_ATTR_CLIENT_FILTER);
	if (clientFilter == null) {
	    clientFilter = new ClientFilter();
	    initDefaultClientFilterCategoriesId(clientFilter, categories);
	} else {
	    setClientFilterParametersByRequestParam(request, clientFilter, categories);
	}
	session.setAttribute(SESSION_ATTR_CLIENT_FILTER, clientFilter);
	return clientFilter;
    }

    // Sets all product categories to the clientFilter
    private void initDefaultClientFilterCategoriesId(ClientFilter clientFilter, List<ProductCategory> categories) {
	List<Long> categoriesId = new ArrayList<>();
	for (ProductCategory productCategory : categories) {
	    categoriesId.add(productCategory.getId());
	}
	clientFilter.setFilterCategoriesId(categoriesId);
    }

    private void setClientFilterParametersByRequestParam(HttpServletRequest request, ClientFilter clientFilter, List<ProductCategory> categories) {
	String isFilterChanged = request.getParameter(REQUEST_PARAM_IS_FILTER_CHANGED);
	String isCategoriesChanged = request.getParameter(REQUEST_PARAM_IS_CATEGORY_CHANGED);

	setItemsPerPageAndCurrentPage(request, clientFilter);

	List<Long> oldCategoryList = clientFilter.getFilterCategoriesId();
	if ((isCategoriesChanged != null) || (isFilterChanged != null)) {
	    setFilterCategoriesIdByRequestParam(request, clientFilter, categories);
	}
	setFirstPageAsCurrentIfCategoriesChanged(clientFilter, oldCategoryList);

	if (isFilterChanged != null) {
	    setPriceFrom(request, clientFilter);
	    setPriceTo(request, clientFilter);
	    setSortKind(request, clientFilter);
	}
    }

    /*
     * Sets the first page as a current page if a client has changed the product
     * types in the filter
     */
    private void setFirstPageAsCurrentIfCategoriesChanged(ClientFilter clientFilter, List<Long> oldCategoryList) {
	if (!oldCategoryList.equals(clientFilter.getFilterCategoriesId())) {
	    int pageNumber = 1;
	    clientFilter.setCurrentPage(pageNumber);
	}
    }

    private void setPriceFrom(HttpServletRequest request, ClientFilter clientFilter) {
	String priceFrom = request.getParameter(REQUEST_PARAM_FILTER_PRICE_FROM);
	if ((priceFrom != null) && !(priceFrom.isEmpty())) {
	    clientFilter.setFilterPriceFrom(Double.parseDouble(priceFrom));
	} else if (priceFrom.isEmpty()) {
	    clientFilter.setFilterPriceFrom(CLIENT_FILTER_PRICE_FROM_DEFAULT);
	}
    }

    private void setPriceTo(HttpServletRequest request, ClientFilter clientFilter) {
	String priceTo = request.getParameter(REQUEST_PARAM_FILTER_PRICE_TO);
	if ((priceTo != null) && !(priceTo.isEmpty())) {
	    clientFilter.setFilterPriceTo(Double.parseDouble(priceTo));
	} else if (priceTo.isEmpty()) {
	    clientFilter.setFilterPriceTo(CLIENT_FILTER_PRICE_TO_DEFAULT);
	}
    }

    // Sets the number defining a sorting type
    private void setSortKind(HttpServletRequest request, ClientFilter clientFilter) {
	String sorting = request.getParameter(REQUEST_PARAM_SORTING);
	if (sorting != null) {
	    clientFilter.setSorting(Integer.parseInt(sorting));
	}
    }

    private void setFilterCategoriesIdByRequestParam(HttpServletRequest request, ClientFilter clientFilter, List<ProductCategory> categories) {
	List<Long> chosenCategories = new ArrayList<>();
	for (int i = 0; i < categories.size(); i++) {
	    String filterCategoryId = request.getParameter("filterCategoriesId[" + i + "]");
	    if ((filterCategoryId != null) && (!filterCategoryId.isEmpty())) {
		Long categoryId = Long.parseLong(filterCategoryId);
		chosenCategories.add(categoryId);
	    }
	}
	clientFilter.setFilterCategoriesId(chosenCategories);
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

    // Returns the list of the page numbers for the pagination
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

    private void changheProductFields(Product product, ProductVO editedProduct) {
	product.setName(editedProduct.getName());
	product.setPrice(editedProduct.getPrice());
	product.setCount(editedProduct.getCount());
	product.setDescription(editedProduct.getDescription());
	Long categoryId = editedProduct.getProductCategoryId();
	ProductCategory category = productCategoryService.getCategoryById(categoryId);
	if (category != null) {
	    product.setCategory(category);
	}
    }

    // Returns a message by key using a locale
    private String getMessageByKey(String key, Locale locale) {
	return messageSource.getMessage(key, null, locale);
    }

}
