package com.ecommapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * Servlet implementation class Controller_customerRegister
 */
public class ControllerCustomerRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerCustomerRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MongoClient client = MongoClients.create(MongoSettingLoc.URL);
		MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> mc = mb.getCollection("users");
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		String customerName = request.getParameter("username");
		String customerPassword = request.getParameter("password");
		String url;
		MongoCursor<Document> cursor = mc.find(Filters.eq("name",customerName)).iterator();
		if (cursor.hasNext()) {
			url = "login_exists.jsp";
		}
		else {
			Document doc = new Document();
			doc.append("name", customerName).append("password", customerPassword);
			mc.insertOne(doc);
			url="registered.jsp";
		}
		response.sendRedirect(url);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
