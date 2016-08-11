package com.atroshonok.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.command.factory.ActionFactory;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * Servlet implementation class FrontController
 */
//@WebServlet("/controller")
public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(getClass());
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Method GET begins to work");
		processRequest(request, response);
		log.debug("Method GET ends to work");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Method POST begins to work");
		processRequest(request, response);
		log.debug("Method POST ends to work");
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		// defining a command from JSP
	    ActionFactory client = new ActionFactory();
	    ActionCommand command = client.defineCommand(request);
	    /*
	     * Causing method execute() and redirecting the parameters to the command class, handling this command 
	     * 
	     */
	    page = command.execute(request);
	    
		// method returns a response page
	    if (page != null) {
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
	      // causing a response page
	      dispatcher.forward(request, response);
	    } else {
	      // setting error page
	      page = ConfigurationManager.getProperty("path.page.index");
	      request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
	      response.sendRedirect(request.getContextPath() + page);
	    }
		
	}

}
