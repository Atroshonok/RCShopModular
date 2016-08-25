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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atroshonok.dao.entities.UserType;
import com.atroshonok.utilits.ConfigurationManager;

public class ServletSecurityFilter implements Filter {
	private Logger log = Logger.getLogger(getClass());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("ServletSecurityFilter: method doFilter works");

		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		UserType type = (UserType) session.getAttribute("userType");

		if (type == null) {
			type = UserType.GUEST;
			session.setAttribute("userType", type);
			resp.sendRedirect(req.getContextPath() + ConfigurationManager.getProperty("path.page.main"));
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
