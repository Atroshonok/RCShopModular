/**
 * 
 */
package com.atroshonok.command;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.UserService;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.DataEncryptor;
import com.atroshonok.utilits.InputDataValidator;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class RegistrCommand implements ActionCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_FIRSTNAME = "firstName";
    private static final String PARAM_NAME_LASTNAME = "lastName";
    private static final String PARAM_NAME_SHIP_ADDRESS = "shipAddress";
    private static final String PARAM_NAME_AGE = "age";

    private Logger log = Logger.getLogger(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see atroshonok.command.ActionCommand#execute(javax.servlet.http.
     * HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
	String page = null;

	if (!validateInputData(request)) {
	    request.setAttribute("registrInfoMessage", MessageManager.getProperty("message.invalidentereddata"));
	    page = ConfigurationManager.getProperty("path.page.registration");
	    return page;
	}

	User user = new User();
	initUserFields(request, user);

	UserService userService = new UserService();

	synchronized (this) {
	    if (checkLoginFree(user.getLogin(), userService)) {
		try {
		    userService.saveUserToDataBase(user);
		    request.setAttribute("mainInfoMessage", MessageManager.getProperty("message.goodregistration"));
		    page = ConfigurationManager.getProperty("path.page.main");
		} catch (ErrorAddingUserServiceException e) {
		    log.error("Error adding new user to Database: " + e);
		    request.setAttribute("registrInfoMessage", MessageManager.getProperty("message.error.registration"));
		    page = ConfigurationManager.getProperty("path.page.registration");
		}
	    } else {
		request.setAttribute("registrInfoMessage", MessageManager.getProperty("message.loginisnotfree"));
		page = ConfigurationManager.getProperty("path.page.registration");
	    }
	    return page;
	}
    }

    private boolean validateInputData(HttpServletRequest request) {
	if (!InputDataValidator.validateLogin(request.getParameter(PARAM_NAME_LOGIN))) {
	    return false;
	}
	if (!InputDataValidator.validatePassword(request.getParameter(PARAM_NAME_PASSWORD))) {
	    return false;
	}
	if (!InputDataValidator.validateEmail(request.getParameter(PARAM_NAME_EMAIL))) {
	    return false;
	}
	if (!InputDataValidator.validateFirstName(request.getParameter(PARAM_NAME_FIRSTNAME))) {
	    return false;
	}
	if (!InputDataValidator.validateLastName(request.getParameter(PARAM_NAME_LASTNAME))) {
	    return false;
	}
	if (!InputDataValidator.validateAge(request.getParameter(PARAM_NAME_AGE))) {
	    return false;
	}
	return true;
    }

    private boolean checkLoginFree(String login, UserService userService) {
	List<User> users = userService.getAllUsers();
	boolean isloginFree = true;
	for (User user : users) {
	    if (user.getLogin().equals(login)) {
		isloginFree = false;
		return isloginFree;
	    }
	}
	return isloginFree;
    }

    private void initUserFields(HttpServletRequest request, User user) {

	user.setLogin(request.getParameter(PARAM_NAME_LOGIN));
	String password = DataEncryptor.getPasswordHashCode(request.getParameter(PARAM_NAME_PASSWORD));
	user.setPassword(password);
	user.setEmail(request.getParameter(PARAM_NAME_EMAIL));
	user.setFirstname(request.getParameter(PARAM_NAME_FIRSTNAME));
	user.setLastname(request.getParameter(PARAM_NAME_LASTNAME));
	user.setShippingAddress(request.getParameter(PARAM_NAME_SHIP_ADDRESS));
	int age = Integer.parseInt(request.getParameter(PARAM_NAME_AGE));
	user.setAge(age);
	user.setUserType(UserType.CLIENT);

	// long registrDate = new GregorianCalendar().getTimeInMillis();
	user.setRegistrDate(new Date());
    }

}
