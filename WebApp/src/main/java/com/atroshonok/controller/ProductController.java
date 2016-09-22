package com.atroshonok.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import com.atroshonok.command.AddProductToCartLogicCommand;
import com.atroshonok.command.RemoveProductFromCartLogicCommand;
import com.atroshonok.command.ShowProductsLogicCommand;
import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.Product;
import com.atroshonok.dao.entities.ProductCategory;
import com.atroshonok.dao.entities.ProductValueObject;
import com.atroshonok.services.IProductCategoryService;
import com.atroshonok.services.IProductService;
import com.atroshonok.services.exceptions.ErrorUpdatingPoductServiceException;
import com.atroshonok.utilits.AdminPageConfigManager;

@Controller
@RequestMapping("/products")
public class ProductController {
    private static final String ATTRIBUTE_FRAGMENT_PATH = "fragmentPath";
    private static final String REQUEST_ATTR_INFO_MESSAGE = "infoMessage";
    private static final String SESSION_ATTR_CART = "cart";
    private static Logger log = Logger.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductCategoryService productCategoryService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String showProducts(HttpServletRequest request, Model model) {
	ShowProductsLogicCommand command = new ShowProductsLogicCommand();
	command.execute(request, productService, productCategoryService);
	return "products";
    }

    @RequestMapping(path = "/add/{productId}", method = RequestMethod.GET)
    public String addProductToCart(@PathVariable("productId") Long productId, HttpServletRequest request) {
	Product product = productService.getProductById(productId);
	Integer productCount = product.getCount();
	Locale requestLocale = getRequestLocal(request);
	if (productCount > 0) {
	    new AddProductToCartLogicCommand().execute(product, request);
	    request.setAttribute(REQUEST_ATTR_INFO_MESSAGE, messageSource.getMessage("message.productadded", null, requestLocale));
	    // For updating the product count in the database
	    updateProduct(product);
	} else {
	    request.setAttribute(REQUEST_ATTR_INFO_MESSAGE, messageSource.getMessage("message.product.emptybase", null, requestLocale));
	}
	return "products";
    }

    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public String showCart() {
	return "cart";
    }

    @RequestMapping(path = "/remove/{productId}", method = RequestMethod.GET)
    public String removeProductFromCart(@PathVariable("productId") Long productId, HttpServletRequest request) {
	Product product = productService.getProductById(productId);
	Cart cart = (Cart) request.getSession().getAttribute(SESSION_ATTR_CART);
	if ((product != null) && (cart.getAllProductsCount() > 0)) {
	    new RemoveProductFromCartLogicCommand().execute(product, cart);
	    request.getSession().setAttribute(SESSION_ATTR_CART, cart);
	    // For updating the product count in the database
	    updateProduct(product);
	}
	return "cart";
    }

    @RequestMapping(path = "/edit/{productId}", method = RequestMethod.GET)
    public String getEditProductForm(@PathVariable Long productId, Model model) {
	Product product = productService.getProductById(productId);
	ProductValueObject editedProduct = new ProductValueObject(product);
	List<ProductCategory> categoryList = productCategoryService.getAllProductCategories();
	model.addAttribute("categoryList", categoryList);
	model.addAttribute("editedProduct", editedProduct);
	model.addAttribute(ATTRIBUTE_FRAGMENT_PATH, AdminPageConfigManager.getProperty("path.fragment.editproductform"));
	return "admin";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String saveEditedProduct(ProductValueObject editedProduct, HttpServletRequest request) {
	Product product = productService.getProductById(editedProduct.getId());
	changheProductFields(product, editedProduct);
	System.out.println(product);
	Locale requestLocale = getRequestLocal(request);
	if (updateProduct(product)) {
	    request.setAttribute(REQUEST_ATTR_INFO_MESSAGE, messageSource.getMessage("message.productupdated", null, requestLocale));
	} else {
	    request.setAttribute(REQUEST_ATTR_INFO_MESSAGE, messageSource.getMessage("message.error.updateproduct", null, requestLocale));
	}
	return "admin";
    }

    private void changheProductFields(Product product, ProductValueObject editedProduct) {
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

    private Locale getRequestLocal(HttpServletRequest request) {
	return localeResolver.resolveLocale(request);
    }

    private boolean updateProduct(Product product) {
	boolean isUpdatedProduct = false;
	try {
	    productService.updateProduct(product);
	    isUpdatedProduct = true;
	} catch (ErrorUpdatingPoductServiceException e) {
	    log.error("Error updating product: " + product, e);
	}
	return isUpdatedProduct;
    }
}
