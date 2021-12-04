package com.ecommapp.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.tools.javac.util.List;

/**
 * Servlet Filter implementation class SessionControl
 */
public class SessionControl implements Filter {
	

    /**
     * Default constructor. 
     */
    public SessionControl() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;		
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String uri = req.getRequestURI();
		if(session != null && session.getAttribute("name") != null) {
			chain.doFilter(request, response);
		}
		else {
			if (uri.contains("admin")){
				RequestDispatcher rd = req.getRequestDispatcher("login_admin.jsp");
				rd.forward(req, res);
			}
			else {
			RequestDispatcher rd = req.getRequestDispatcher("logged_out.jsp");
			rd.forward(req, res);
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
