package com.atroshonok.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atroshonok.dao.entities.Cart;
import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.services.IUserService;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.LoginAlreadyExistServiceException;
import com.atroshonok.services.exceptions.ServiceException;
import com.atroshonok.utilits.AdminPageConfigManager;
import com.atroshonok.utilits.DataEncryptor;

/**
 * 
 * @author Ivan Atroshonok
 *
 */
@Controller
public class UserController {
    private static final String ATTRIBUTE_REGISTR_INFO_MESSAGE = "registrInfoMessage";
    private static final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
    private static final String SESSION_ATTR_CART = "cart";
    private static final String SESSION_ATTR_USER = "user";
    private static final String SESSION_ATTR_USER_TYPE = "userType";
    private static final String ATTRIBUTE_FRAGMENT_PATH = "fragmentPath";

    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(path = "/users/registration", method = RequestMethod.GET)
    public String createNewUser(Model model) {
	return "registration";
    }

    /**
     * 
     * @param user
     * @param bindingResult
     * @param model
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping(path = "/users/addnew", method = RequestMethod.POST)
    public String addUserFromRegistrationForm(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
	log.info("User is got from registration form: " + user);
	if (bindingResult.hasErrors()) {
	    return "registration";
	}
	initEmptyUserFields(user);
	try {
	    userService.saveUserToDataBase(user);
	    log.info("User: " + user + "is saved to DB successfully.");
	    redirectAttributes.addFlashAttribute(ATTRIBUTE_REGISTR_INFO_MESSAGE, getMessageByKey("message.goodregistration", locale));
	} catch (LoginAlreadyExistServiceException e) {
	    log.error("Error adding new user to DB: " + e.getMessage());
	    request.setAttribute(ATTRIBUTE_REGISTR_INFO_MESSAGE, getMessageByKey("message.loginisnotfree", locale));
	    return "registration";
	} catch (ErrorAddingUserServiceException e) {
	    log.error("Error adding new user: " + user + " to DB.", e);
	    request.getSession().setAttribute(ATTRIBUTE_REGISTR_INFO_MESSAGE, getMessageByKey("message.error.registration", locale));
	    return "registration";
	}
	return "redirect:/users/registration";
    }

    /**
     * 
     * @param session
     * @param user
     * @param redirectAttributes
     * @param locale
     * @return
     * @throws ServiceException
     */
    @RequestMapping(path = "/users/login", method = RequestMethod.POST)
    public String login(HttpSession session, User user, RedirectAttributes redirectAttributes, Locale locale) throws ServiceException {
	User client = getUserByRequestParam(user);
	if (client == null) {
	    redirectAttributes.addFlashAttribute(ERROR_LOGIN_PASS_MESSAGE, getMessageByKey("message.loginerror", locale));
	    return "redirect:/main";
	} else if (client.getUserType().equals(UserType.ADMIN)) {
	    session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.ADMIN);
	    session.setAttribute(SESSION_ATTR_USER, client);
	    return "redirect:/products/all";
	} else if (client.getUserType().equals(UserType.CLIENT)) {
	    setClientSessionAttributes(session, client);
	    return "redirect:/products/all";
	} else {
	    session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.GUEST);
	    redirectAttributes.addFlashAttribute(ERROR_LOGIN_PASS_MESSAGE, getMessageByKey("message.loginerror", locale));
	    return "redirect:/main";
	}
    }

    /**
     * 
     * @param model
     * @return
     * @throws ServiceException
     */
    @RequestMapping(path = "/users/all", method = RequestMethod.GET)
    public String getAllUsers(ModelMap model) throws ServiceException {
	List<User> users = userService.getAllUsers();
	model.addAttribute("usersList", users);
	model.addAttribute(ATTRIBUTE_FRAGMENT_PATH, AdminPageConfigManager.getProperty("path.fragment.allusers"));
	return "admin";
    }

    /**
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
	log.error("Exception occured. ", e);
	ModelAndView mav = new ModelAndView();
	mav.addObject("error", e.getMessage());
	mav.setViewName("error/error");
	return mav;
    }

    private void initEmptyUserFields(User user) {
	String enteredUserPassword = user.getPassword();
	String codedUserPassword = DataEncryptor.getPasswordHashCode(enteredUserPassword);
	user.setPassword(codedUserPassword);
	user.setIsInBlackList(false);
	user.setRegistrDate(new Date());
	user.setUserType(UserType.CLIENT);
    }

    private void setClientSessionAttributes(HttpSession session, User client) {
	session.setAttribute(SESSION_ATTR_CART, new Cart());
	session.setAttribute(SESSION_ATTR_USER_TYPE, UserType.CLIENT);
	session.setAttribute(SESSION_ATTR_USER, client);
    }

    private User getUserByRequestParam(User user) throws ServiceException {
	String enteredLogin = user.getLogin();
	String enteredPass = user.getPassword();
	String passwordHash = DataEncryptor.getPasswordHashCode(enteredPass);
	User client = userService.getUserByLoginPassword(enteredLogin, passwordHash);
	return client;
    }

    // Returns a message by key using a locale
    private String getMessageByKey(String key, Locale locale) {
	return messageSource.getMessage(key, null, locale);
    }

}
