/**
 * 
 */
package com.atroshonok.tests;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atroshonok.dao.UserDao;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Atroshonok Ivan
 *
 */
public class UserDaoTest {
    private static Logger log = Logger.getLogger(UserDaoTest.class);
    private static HibernateUtil util = HibernateUtil.getInstance();
    private static User user = null;
    private static UserDao userDao = new UserDao();
    private Transaction transaction;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    private static void initUserFields() {
	user.setLogin("TestLogin");
	user.setPassword("1234");
	user.setEmail("Example.email@gmail.com");
	user.setFirstname("Smith");
	user.setLastname("John");
	user.setRegistrDate(new Date());
	user.setAge(29);
	user.setShippingAddress("Minsk");
	user.setUserType(UserType.CLIENT);
	user.setIsInBlackList(false);
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	dropUserTable();
	util.getSession().close();
    }

    private static void dropUserTable() {
	SQLQuery sqlQuery = util.getSession().createSQLQuery("DROP TABLE 'users';");
	sqlQuery.executeUpdate();
    }

    @Before
    public void setUp() {
	user = new User();
	initUserFields();
	Long userId = saveUserToDB(user);
	user.setId(userId);
	util.getSession().clear();

    }

    private Long saveUserToDB(User user2) {
	Serializable userId = null;
	try {
	    transaction = util.getSession().beginTransaction();
	    userId = userDao.save(user);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error saving user in class: " + UserDaoTest.class, e);
	    transaction.rollback();
	}
	return Long.parseLong(userId.toString());//Нужна ли проверка на null
    }

    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#saveOrUpdate(java.lang.Object)}.
     */
    @Test
    public void testSaveOrUpdateLoad() {
	User loadUser = null;
	user.setAge(50);
	try {
	    transaction = util.getSession().beginTransaction();
	    userDao.saveOrUpdate(user);
	    transaction.commit();
	    util.getSession().clear();
	    loadUser = userDao.load(user.getId());
	} catch (DaoException e) {
	    log.error("Error updating and loading user in class: " + UserDaoTest.class, e);
	}
	compareUserWithLoadedUser(loadUser);
    }

    @Test
    public void testGet() {
	log.info("Starts testGet() method in class: " + UserDaoTest.class);
	User gotUser = getUser(user.getId());
	compareUserWithLoadedUser(gotUser);
    }


    private User getUser(Long userId) {
	User tempUser = null;
	try {
	    tempUser = userDao.get(userId);
	    log.info("Got User: " + tempUser);
	} catch (DaoException e) {
	    log.error("Error getting user in class: " + UserDaoTest.class, e);
	    transaction.rollback();
	}
	return tempUser;
    }

    private void compareUserWithLoadedUser(User loadedUser) {
	if (loadedUser != null) {
	    assertEquals(user, loadedUser);
	} else {
	    fail("test method does't work right in class: " + UserDao.class);
	}
    }
    
    /**
     * Test method for {@link com.atroshonok.dao.BaseDao#save(java.lang.Object)}
     * .
     */
//    @Test
//    public void testSave() {
//	saveUserToDB(user);
//	fail("Not yet implemented");
//    }

   
    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#load(java.io.Serializable)}.
     */
    @Test
    public void testLoad() {
	User loadedUser = null;
	try {
	   loadedUser = userDao.load(user.getId());
	} catch (DaoException e) {
	    log.error("Error loading user in class: " + UserDaoTest.class, e);
	}
	compareUserWithLoadedUser(loadedUser);
    }

    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#delete(java.lang.Object)}.
     */
    @Test
    public void testDelete() {
	util.getSession().saveOrUpdate(user);
	User gotUser = null;
	try {
	    userDao.delete(user);
	    gotUser = userDao.get(user.getId());
	} catch (DaoException e) {
	    log.error("Error deleting user in class: " + UserDaoTest.class, e);
	}
	assertNull(gotUser);
    }

}
