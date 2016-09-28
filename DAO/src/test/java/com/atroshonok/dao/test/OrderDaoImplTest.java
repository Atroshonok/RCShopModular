package com.atroshonok.dao.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IOrderDao;
import com.atroshonok.dao.IUserDao;
import com.atroshonok.dao.entities.Order;
import com.atroshonok.dao.entities.OrderState;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.dao.exceptions.DaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-dao-test-config.xml")
@Transactional(propagation = Propagation.SUPPORTS)
public class OrderDaoImplTest {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IUserDao userDao;

    private Long userId;
    private List<Order> expectedsOrders = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
	User user = createUser();
	userId = (Long) userDao.save(user);

	Order orderA = createFirstOrder(user);
	orderDao.save(orderA);
	Order orderB = createSecondOrder(user);
	orderDao.save(orderB);

	expectedsOrders.add(orderA);
	expectedsOrders.add(orderB);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetOrdersByUserId() {
	List<Order> orders = orderDao.getOrdersByUserId(userId);
	assertEquals(expectedsOrders.get(0), orders.get(0));

	Long notExistingUserId = 100L;
	List<Order> emtyOrderList = orderDao.getOrdersByUserId(notExistingUserId);
	assertTrue(emtyOrderList.isEmpty());
    }

    private Order createSecondOrder(User user) throws DaoException {
	Order orderB = new Order();
	orderB.setUser(user);
	orderB.setOrderState(OrderState.OPEN);
	orderB.setSumPrice(700.26);
	return orderB;
    }

    private Order createFirstOrder(User user) {
	Order orderA = new Order();
	orderA.setUser(user);
	orderA.setOrderState(OrderState.OPEN);
	orderA.setSumPrice(500.56);
	return orderA;
    }

    private User createUser() {
	User user = new User();
	user.setLogin("TestLogin");
	user.setPassword("12345678");
	user.setEmail("Example.email@gmail.com");
	user.setFirstname("Smith");
	user.setLastname("John");
	user.setRegistrDate(new Date());
	user.setAge(29);
	user.setShippingAddress("Minsk");
	user.setUserType(UserType.CLIENT);
	user.setIsInBlackList(false);
	return user;
    }

}
