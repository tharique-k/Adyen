package com.ecommapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Cart;
import com.ecommapp.models.Products;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet Filter implementation class Controller_shoppingCartJSP
 */
public class ControllerShoppingCartJSP extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor. 
     */
    public ControllerShoppingCartJSP() {
        // TODO Auto-generated constructor stub
    }

	
	public void doGet(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;		
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);	
		Cart cart;
		
		if(session != null && session.getAttribute("name") != null)  {
			String name = (String) session.getAttribute("name");
			
			//No cart in session - create one
			if(session.getAttribute("cart") == null) {
				cart = Cart.getCart(name);
			}
			//cart is in session, use it
			else {
				 cart = (Cart) session.getAttribute("cart");
				
			}
			request.setAttribute("shoppingProducts", cart.getProducts());
			ServletContext servletContext = getServletContext();
			RequestDispatcher rd = servletContext.getRequestDispatcher("shoppingCart.jsp");
			rd.forward(request, response);
		}
		else {
			res.sendRedirect("logged_out.jsp");
		}
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	

}
