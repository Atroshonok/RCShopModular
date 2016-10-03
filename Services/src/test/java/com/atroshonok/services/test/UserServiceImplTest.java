package com.atroshonok.services.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.enums.UserType;
import com.atroshonok.services.IUserService;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.LoginAlreadyExistServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-services-test-config.xml" })
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserByLoginPassword() throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException {
	User expectedUserA = createNewUser("newLoginA");
	userService.saveUserToDataBase(expectedUserA);

	User actualUser = userService.getUserByLoginPassword("newLoginA", "newPassword");
	expectedUserA.setId(actualUser.getId());

	assertEquals(expectedUserA, actualUser);
    }

    @Test
    public void testSaveUserToDataBase() throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException {
	User expectedUserB = createNewUser("newLoginB");
	userService.saveUserToDataBase(expectedUserB);

	User actualUser = userService.getUserByLoginPassword("newLoginB", "newPassword");
	expectedUserB.setId(actualUser.getId());

	assertEquals(expectedUserB, actualUser);
    }

    @Test(expected = LoginAlreadyExistServiceException.class)
    public void testSaveUserToDataBaseWrongeLogin() throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException {
	User expectedUser = createNewUser("newLoginC");
	userService.saveUserToDataBase(expectedUser);

	User expectedUserB = createNewUser("newLoginC");
	userService.saveUserToDataBase(expectedUserB);
    }

    @Ignore
    @Test
    public void testGetAllUsers() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserById() throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException {
	User expectedUserD = createNewUser("newLoginD");
	userService.saveUserToDataBase(expectedUserD);
	expectedUserD = userService.getUserByLoginPassword("newLoginD", "newPassword");
	
	User actualUser = userService.getUserById(expectedUserD.getId());
	
	assertEquals(expectedUserD, actualUser);
    }

    @Ignore
    @Test
    public void testUpdateUserData() {
	fail("Not yet implemented"); // TODO
    }
    
    private User createNewUser(String login) {
  	User user = new User();
  	user.setAge(12);
  	user.setEmail("example@tut.by");
  	user.setFirstname("user");
  	user.setIsInBlackList(false);
  	user.setLastname("User");
  	user.setLogin(login);
  	user.setPassword("newPassword");
  	user.setRegistrDate(new Date());
  	user.setShippingAddress("Minsk");
  	user.setUserType(UserType.CLIENT);
  	return user;
      }

}
