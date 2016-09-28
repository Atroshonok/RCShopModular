package com.atroshonok.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.IUserDao;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-dao-test-config.xml")
@Transactional(propagation = Propagation.SUPPORTS)
public class UserDaoImplTest {

    private static Logger log = Logger.getLogger(UserDaoImplTest.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private SessionFactory sessionFactory;

    private Session session;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserByLoginPassword() {
	User userA = createUser("LoginA", "PasswordA");
	saveUserAndClearSession(userA);

	User actualUserA = userDao.getUserByLoginPassword("loginA", "PasswordA");
	assertEquals(userA, actualUserA);

	User actualUserA1 = userDao.getUserByLoginPassword("loginA1", "PasswordA1");
	assertNull(actualUserA1);
    }

    @Test
    public void testGetAllUsers() {
	User userB1 = createUser("LoginB1", "PasswordB1");
	saveUserAndClearSession(userB1);
	
	User userB2 = createUser("LoginB2", "PasswordB2");
	saveUserAndClearSession(userB2);
	
	User userB3 = createUser("LoginB3", "PasswordB3");
	saveUserAndClearSession(userB3);
	
	List<User> actualUsers = userDao.getAllUsers();
	assertTrue(actualUsers.size() >= 3);
	
    }

    @Test
    public void testGetUsersByLogin() {
	User userC = createUser("LoginC", "PasswordC");
	saveUserAndClearSession(userC);

	List<User> users = userDao.getUsersByLogin("loginC");
	User actualUserC = users.get(0);
	assertEquals(userC, actualUserC);

	List<User> emptyUsers = userDao.getUsersByLogin("loginCCCCCCC");
	assertTrue(emptyUsers.isEmpty());

    }

    private void saveUserAndClearSession(User user) {
	session = sessionFactory.getCurrentSession();
	Long userId = (Long) session.save(user);
	user.setId(userId);
	session.flush();
	session.clear();
    }

    private User createUser(String login, String password) {
	User user = new User();
	user.setLogin(login);
	user.setPassword(password);
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
