package com.ecommapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Servlet implementation class Controller_shoppingCart
 */
public class ControllerShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static List<Document> list = new ArrayList<Document>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		long pid = Long.parseLong(request.getParameter("pid"));
		MongoClient client = MongoClients.create(MongoSettingLoc.URL);
		MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
		MongoCollection mc = mb.getCollection("products");
		MongoCursor<Document> cursor = mc.find(Filters.eq("pid", pid)).iterator();
		Document document = new Document(cursor.next());
		/*int quantity =(Integer) document.get("quantity");
		BasicDBObject query = new BasicDBObject();
		query.append("$set", new BasicDBObject().append("quantity", quantity+1));
		BasicDBObject searchQuery = new BasicDBObject().append("pid", pid);
		mc.updateOne(searchQuery, query); 
		MongoCursor<Document> cursor2 = mc.find(Filters.eq("pid", pid)).iterator() */;
		list.remove(cursor.next());
		response.sendRedirect("shoppingCart.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long pid = Long.parseLong(request.getParameter("pid"));
		MongoClient client = MongoClients.create(MongoSettingLoc.URL);
		MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
		MongoCollection mc = mb.getCollection("products");
		MongoCursor<Document> cursor = mc.find(Filters.eq("pid", pid)).iterator();
		Document document = new Document(cursor.next());
		/*int quantity =(Integer) document.get("quantity");
		BasicDBObject query = new BasicDBObject();
		query.append("$set", new BasicDBObject().append("quantity", quantity-1));
		BasicDBObject searchQuery = new BasicDBObject().append("pid", pid);
		mc.updateOne(searchQuery, query);*/
		list.add(document); 
		ServletContext servletContext = getServletContext();
		RequestDispatcher rd = servletContext.getRequestDispatcher("/shoppingCart.jsp");
		rd.forward(request, response);
//		request.setAttribute("shoppingProducts", list);
//		response.sendRedirect("shoppingCart.jsp");
	}

}
