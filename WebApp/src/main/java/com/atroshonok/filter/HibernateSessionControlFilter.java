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

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.atroshonok.dao.dbutils.HibernateUtil;

/**
 * @author Ivan Atroshonok
 *
 */
public class HibernateSessionControlFilter implements Filter {
    private static Logger log = Logger.getLogger(HibernateSessionControlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	chain.doFilter(request, response);
	HibernateUtil.getInstance().closeCurrentSession();
    }

    @Override
    public void destroy() {

    }

}
