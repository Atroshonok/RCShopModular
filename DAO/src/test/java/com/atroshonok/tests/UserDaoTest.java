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

import com.atroshonok.dao.IDao;
import com.atroshonok.dao.UserDaoImpl;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Atroshonok Ivan
 *
 */
public class UserDaoTest extends BaseDaoCRUDTest<User> {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Override
    protected IDao<User> getEntityDao() {
	return new UserDaoImpl();
    }

    @Override
    protected User createAndInitEntity() {
	User user = new User();
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
	return user;
    }

    @Override
    protected void changeEntity(User entity) {
	entity.setAge(56);

    }

}
