package com.atroshonok.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atroshonok.dao.entities.UserType;

public class ServletSecurityFilter implements Filter {
    private static final String SESSION_ATTR_USER_TYPE = "userType";
    private static Logger log = Logger.getLogger(ServletSecurityFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	log.debug("method doFilter() works");

	HttpServletRequest req = (HttpServletRequest) request;
	HttpSession session = req.getSession();

	UserType type = (UserType) session.getAttribute(SESSION_ATTR_USER_TYPE);

	if (type == null) {
	    type = UserType.GUEST;
	    session.setAttribute(SESSION_ATTR_USER_TYPE, type);
	}
	chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }


}
