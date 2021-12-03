package com.ecommapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.ecommapp.models.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;
import org.bson.types.ObjectId;


/**
 * Servlet implementation class Controller_session
 */
public class TestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }
public void service (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		
	MongoClient mongoClient = MongoSettingLoc.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);		
	MongoCollection<Item> items = db.getCollection("items",Item.class);
	Item item = items.find(eq("name","two")).first();
	request.setAttribute("name", item.getName());
	request.setAttribute("value", item.getValue());
	ServletContext context = getServletContext();
	RequestDispatcher dispatcher = context.getRequestDispatcher("/search_results.jsp");
	dispatcher.forward(request, response);
	
}

}