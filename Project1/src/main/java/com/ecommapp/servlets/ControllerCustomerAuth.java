package com.ecommapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ControllerCustomerAuth extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		MongoClient client = MongoClients.create(MongoSettingLoc.URL);
		MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> mc = mb.getCollection("users");
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		list.add(new BasicDBObject("name",name));
		list.add(new BasicDBObject("password", pass));
		query.put("$and", list);
		MongoCursor<Document> cursor = mc.find(query).iterator();
		//System.out.println(mc.countDocuments());
		String url = "";
		if (cursor.hasNext()) {
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(15*60); //15 minutes
			url = "home.jsp";
			session.setAttribute("name", name);
			session.setAttribute("type", "customer");
		} else {
			url = "no_login.jsp";
		}
		
		response.sendRedirect(url);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
