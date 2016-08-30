///**
// * 
// */
//package com.atroshonok.tests;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.atroshonok.dao.Dao;
//import com.atroshonok.dao.OrderDao;
//import com.atroshonok.dao.entities.Order;
//import com.atroshonok.dao.entities.OrderState;
//import com.atroshonok.dao.entities.OrderedProduct;
//import com.atroshonok.dao.entities.Product;
//import com.atroshonok.dao.entities.User;
//
///**
// * @author Ivan Atroshonok
// *
// */
//
//public class OrderDaoTest extends BaseDaoCRUDTest<Order> {
//
//    @Override
//    protected Dao<Order> getEntityDao() {
//	return new OrderDao();
//    }
//
//    @Override
//    protected Order createAndInitEntity() {
//	Order order = new Order();
//	order.setOrderedProducts(createOrderedProducts());
//	order.setOrderState(OrderState.OPEN);
//	order.setUser(createUser());
//	return order;
//    }
//
//    private User createUser() {
//	User user = new User();
//	user.setLogin("Client");
//	util.getSession().save(user);
//	return user;
//    }
//
//    private List<OrderedProduct> createOrderedProducts() {
//	List<OrderedProduct> orderedProducts = new ArrayList<>();
//	orderedProducts.add(createNewOrderedProduct("MIG-31"));
//	orderedProducts.add(createNewOrderedProduct("MST-01D"));
//	orderedProducts.add(createNewOrderedProduct("FAT Shark googles"));
//	return orderedProducts;
//    }
//
//    private OrderedProduct createNewOrderedProduct(String productName) {
//	OrderedProduct orderedProduct = new OrderedProduct();
//	Product product = new Product(productName);
//	orderedProduct.setOrderedProduct(product);
//	util.getSession().save(product);
//	util.getSession().save(orderedProduct);
//	return orderedProduct;
//    }
//
//    @Override
//    protected void changeEntity(Order entity) {
//	entity.getOrderedProducts().add(createNewOrderedProduct("Taranis"));
//    }
//
//}
