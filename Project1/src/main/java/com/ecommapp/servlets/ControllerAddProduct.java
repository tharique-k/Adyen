package com.ecommapp.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Products;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ControllerAddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAddProduct() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			RequestDispatcher rd = request.getRequestDispatcher("addProduct.jsp");
			rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		Alert alert = new Alert();
//		Boolean control_session = alert.Alert(request, response);
//		if(control_session == true) {
//			MongoClient client = MongoClients.create(MongoSettingLoc.URL);
//			MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
//			MongoCollection<Document> mc = mb.getCollection("products");
			
			MongoClient mongoClient = MongoSettingLoc.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
			
			MongoCollection<Products> items = db.getCollection("products",Products.class);
			
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String description = request.getParameter("description");
			String url = request.getParameter("url");
			if(name != "" && price!="" && description != "" && url!="") {
			
				Products newProduct = new Products(name, Long.parseLong(price), description, url);
				items.insertOne(newProduct);
				
			}
			else {
				//TODO do something with error
				String error = "Product information cannot be left blank!";
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("addProduct.jsp");
			rd.forward(request, response);
//		}
//		else {
//			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
//			rd.forward(request, response);
//		}
	}

}
