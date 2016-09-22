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

import com.atroshonok.dao.entities.UserType;
import com.atroshonok.utilits.AdminPageConfigManager;
import com.atroshonok.utilits.MessageManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class AccessLevelSecurityFilter implements Filter {

    private static final String SESSION_ATTR_NAME_USERTYPE = "userType";
    private static List<String> deniedAdminURIs;
    private static List<String> deniedClientURIs;
    private static List<String> deniedGuestURIs;

    private Logger log = Logger.getLogger(getClass());

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	log.info(AccessLevelSecurityFilter.class + ": method doFilter() works");

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;

	UserType userType = (UserType) httpRequest.getSession().getAttribute(SESSION_ATTR_NAME_USERTYPE);
	String requestedURI = httpRequest.getRequestURI();

	if ((deniedClientURIs.contains(requestedURI) && userType.equals(UserType.CLIENT)) ||
			(deniedAdminURIs.contains(requestedURI) && userType.equals(UserType.ADMIN)) ||
					(deniedGuestURIs.contains(requestedURI) && userType.equals(UserType.GUEST))) {
	    httpRequest.getSession().setAttribute("errorAccessMessage", MessageManager.getProperty("message.accessdenied"));
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

    private void initGuestDeniedURIsList() {
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/cart.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/admin.jsp");
	deniedGuestURIs.add("/MRCShop/WEB-INF/jsp/orders.jsp");
    }

    private void initAdminDeniedURIsList() {
    }

    private void initClientDeniedURIsList() {
	deniedAdminURIs.add("/MRCShop/WEB-INF/jsp/admin.jsp");
    }

}
