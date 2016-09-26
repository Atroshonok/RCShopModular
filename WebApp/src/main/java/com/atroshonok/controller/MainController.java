package com.atroshonok.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Ivan Atroshonok
 *
 */
@Controller
public class MainController {

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMainPage(Model model) {
	return "main";
    }

    /**
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
	session.invalidate();
	return "redirect:/main";
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/access/denied")
    public String showAccessDeniedPage() {
	return "accessdenied";
    }

}
