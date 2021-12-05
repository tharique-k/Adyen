package com.ecommapp.servlets;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Cart;
import com.ecommapp.models.Products;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
/**
 * Servlet implementation class ControllerAddToCart
 */
public class ControllerAddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAddToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		Long pid = Long.parseLong(request.getParameter("pid"));
		Cart cart;
		if(name!=null) {
			if (session.getAttribute("cart")==null) {
				cart = Cart.getCart(name);
				session.setAttribute("cart", cart);
			}
			else {
				cart = (Cart) session.getAttribute("cart");
			}
			MongoClient mongoClient = MongoSettingLoc.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
			MongoCollection<Products> items = db.getCollection("products",Products.class);
			MongoCursor<Products> cursor = items.find(eq("pid",pid)).iterator();
			while(cursor.hasNext()) {
				cart.addProduct(cursor.next());
			}
			request.setAttribute("shoppingProducts", cart.getProducts());
			ServletContext servletContext = getServletContext();
			RequestDispatcher rd = servletContext.getRequestDispatcher("/shoppingCart.jsp");
			rd.forward(request, response);
		}
		else {
			
			response.sendRedirect("logged_out.jsp");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
