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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atroshonok.dao.enums.UserType;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */

public class AccessLevelSecurityFilter implements Filter {

    private static Logger log = Logger.getLogger(AccessLevelSecurityFilter.class);
    private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
    private static List<String> deniedAdminURIs;
    private static List<String> deniedClientURIs;
    private static List<String> deniedGuestURIs;
   
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	log.info("method doFilter() works");

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	HttpSession session = httpRequest.getSession();

	UserType userType = (UserType) session.getAttribute(SESSION_ATTR_NAME_USERTYPE);
	String requestedURI = httpRequest.getRequestURI();
	log.debug("Requested URI: " + requestedURI);

		// consider extracting conditions to a method
		// http://refactoring.com/catalog/decomposeConditional.html
	if ((deniedClientURIs.contains(requestedURI) && userType.equals(UserType.CLIENT)) || 
		(deniedAdminURIs.contains(requestedURI) && userType.equals(UserType.ADMIN)) || 
			(deniedGuestURIs.contains(requestedURI) && userType.equals(UserType.GUEST))) {
	    session.setAttribute("errorAccessMessage", getMessageByKeyAndLocale("message.accessdenied"));
	    httpResponse.sendRedirect(httpRequest.getContextPath() + "/access/denied");
	    return;
	}
	chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
	deniedAdminURIs = new ArrayList<>();
	deniedClientURIs = new ArrayList<>();
	deniedGuestURIs = new ArrayList<>();

	initClientDeniedURIsList();
	initAdminDeniedURIsList();
	initGuestDeniedURIsList();
    }
    
    @Override
    public void destroy() {
    }

    private String getMessageByKeyAndLocale(String key) {
	return MessageManager.getProperty(key);
    }

    private void initGuestDeniedURIsList() {
	
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/cart.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/admin.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/orders.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/orders.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/orders.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/orders.jsp");
    }

    private void initAdminDeniedURIsList() {
    }

    private void initClientDeniedURIsList() {
	deniedClientURIs.add("/MRCShop/WEB-INF/jsp/admin.jsp");
    }

}
