/**
 * 
 */
package com.atroshonok.filter;

import java.io.IOException;

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
import com.atroshonok.utilits.ConfigurationManager;

/**
 * @author Atroshonok Ivan
 *
 */
public class PageRedirectSecurityFilter implements Filter {
    private Logger log = Logger.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	log.debug("PageRedirectSecurityFilter: method doFilter works");

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;

	httpRequest.getSession().setAttribute("userType", UserType.GUEST);
	httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getProperty("path.page.index"));

	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
