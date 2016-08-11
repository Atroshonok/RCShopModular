/**
 * 
 */
package com.atroshonok.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atroshonok.command.CommandEnum;
import com.atroshonok.entities.UserType;
import com.atroshonok.utilits.ConfigurationManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class AccessLevelSecurityFilter implements Filter {

	private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
	private static final String REQUEST_PARAM_NAME_COMMAND = "command";
	private static List<String> adminCommmands;
	private static List<String> clientCommmands;
	
	private Logger log = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.debug("AccessLevelSecurityFilter: method doFilter works");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		UserType userType = (UserType) httpRequest.getSession().getAttribute(SESSION_ATTR_NAME_USERTYPE);
		String command = request.getParameter(REQUEST_PARAM_NAME_COMMAND);

		if ((adminCommmands.contains(command.toUpperCase()) && !userType.equals(UserType.ADMIN))
				|| (clientCommmands.contains(command.toUpperCase())
						&& !(userType.equals(UserType.CLIENT) || userType.equals(UserType.ADMIN)))) {

			httpRequest.getSession().setAttribute(SESSION_ATTR_NAME_USERTYPE, UserType.GUEST);
			httpRequest.getSession().setAttribute("errorAccessMessage",	MessageManager.getProperty("message.accessdenied"));
			httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getProperty("path.page.accessdenied"));
			return;
		}

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		adminCommmands = new ArrayList<>();
		clientCommmands = new ArrayList<>();

		initClientCommandsList();
		initAdminCommandsList();
	}

	private void initAdminCommandsList() {
		adminCommmands.add(CommandEnum.ADDNEWPRODUCT.toString());
		adminCommmands.add(CommandEnum.BLACKLIST.toString());
		adminCommmands.add(CommandEnum.EDITPRODUCT.toString());
		adminCommmands.add(CommandEnum.SAVEEDITEDPRODUCT.toString());
		adminCommmands.add(CommandEnum.SAVEPRODUCT.toString());
		adminCommmands.add(CommandEnum.SHOWALLPRODUCTS.toString());
		adminCommmands.add(CommandEnum.SHOWALLUSERS.toString());
	}

	private void initClientCommandsList() {
		clientCommmands.add(CommandEnum.ADDTOCART.toString());
		clientCommmands.add(CommandEnum.ADDNEWPRODUCT.toString());
		clientCommmands.add(CommandEnum.ORDER.toString());
		clientCommmands.add(CommandEnum.REMOVEFROMCART.toString());
		clientCommmands.add(CommandEnum.SHOWCART.toString());
		clientCommmands.add(CommandEnum.SHOWUSERORDERS.toString());
	}

}
