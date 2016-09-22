package com.atroshonok.controller;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.IUserService;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.utilits.DataEncryptor;

@Controller
public class UserController {
    private static final String SESSION_ATTR_CART = "cart";

    private static final String SESSION_ATTR_USER = "user";

    private static final String SESSION_ATTR_USER_TYPE = "userType";

    private static Logger log = Logger.getLogger(UserController.class);
    
    @Autowired
    private IUserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CookieLocaleResolver localeResolver;
    
    @RequestMapping(path = "/users/registration", method = RequestMethod.GET)
    public String createNewUser(Model model) {
	return "registration";
    }
    
    @RequestMapping(path = "/users/addnew", method = RequestMethod.POST)
    public String addUserFromRegistrationForm(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
	log.info("User is got from registration form: " + user);
	if (bindingResult.hasErrors()) {
	    return "registration";
	}
	initEmptyUserFields(user);
	try {
	    userService.saveUserToDataBase(user);
	    log.info("User: " + user + "is saved to DB successfully.");
	    request.getSession().setAttribute("mainInfoMessage",messageSource.getMessage("message.goodregistration", null, getUserLocale(request)));
	} catch (DataAccessException | ErrorAddingUserServiceException e) {
	    log.error("Error adding new user: " + user + " to DB.", e);
	    //TODO Проверка на некорректный логин?
	    request.getSession().setAttribute("registrInfoMessage",messageSource.getMessage("message.error.registration", null, getUserLocale(request)));
	    return "registration";
	}
	return "redirect:/main";
    }

    private Locale getUserLocale(HttpServletRequest request) {
	Locale userLocale = localeResolver.resolveLocale(request);
	return userLocale;
    }

    private void initEmptyUserFields(User user) {
	String enteredUserPassword = user.getPassword();
	String codedUserPassword = DataEncryptor.getPasswordHashCode(enteredUserPassword);
	user.setPassword(codedUserPassword);
	user.setIsInBlackList(false);
	user.setRegistrDate(new Date());
	user.setUserType(UserType.CLIENT);
    }
    
    @RequestMapping(path = "/users/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, Model model) {
	HttpSession session = request.getSession();
	User client = getUserByRequestParam(user);
	if (client == null) {
	    model.addAttribute("errorLoginPassMessage", messageSource.getMessage("message.loginerror", null, getUserLocale(request)));
	    return "main";
	} else if (client.getUserType().equals(UserType.ADMIN)) {
	    session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.ADMIN);	  
	    session.setAttribute(SESSION_ATTR_USER, client);
	    return "admin";
	} else if (client.getUserType().equals(UserType.CLIENT)) {
	    setClientSessionAttributes(session, client);
	    return "redirect:/products/all";
	} else {
	    session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.GUEST);
	    model.addAttribute("errorLoginPassMessage", messageSource.getMessage("message.loginerror", null, getUserLocale(request)));
	    return "main";
	}
    }

    private void setClientSessionAttributes(HttpSession session, User client) {
	session.setAttribute(SESSION_ATTR_CART, new Cart());
	session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.CLIENT);
	session.setAttribute(SESSION_ATTR_USER, client);
    }

    private User getUserByRequestParam(User user) {
	String enteredLogin = user.getLogin();
	String enteredPass = user.getPassword();
	String passwordHash = DataEncryptor.getPasswordHashCode(enteredPass);
	User client = userService.getUserByLoginPassword(enteredLogin, passwordHash);
	return client;
    }

}
