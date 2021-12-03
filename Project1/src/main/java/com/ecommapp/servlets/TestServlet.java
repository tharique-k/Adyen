package com.ecommapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
public void service (HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		Item newItem = new Item("two","item2");		
		MongoCollection<Item> items = db.getCollection("items",Item.class);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Item item = items.find(eq("name","two")).first();
		out.println("<html><body><h1>Your values from the DB</h1>");
		out.println("<li>" + item.getName()+ " : " +item.getValue() + "</li>");
		/*MongoCursor<Item> cursor = items.find().iterator();
		Item itemN;
		while (cursor.hasNext()) {
		itemN = cursor.next();
		out.println("<li>" + itemN.getName()+ " : " +itemN.getValue() + "</li>");
	}
	*/
		
	out.println("</body></html>");
	out.close();
	
	}
}

