package com.ecommapp.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Products;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@PostMapping("/login")
	public String doLogin(HttpServletRequest request) {
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		MongoClient client = MongoClients.create(MongoSettingLoc.URL);
		MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> mc = mb.getCollection("admins");
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		list.add(new BasicDBObject("name",name));
		list.add(new BasicDBObject("password", pass));
		query.put("$and", list);
		MongoCursor<Document> cursor = mc.find(query).iterator();
		if (cursor.hasNext()) {
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(15*60); //15 minutes
			session.setAttribute("name", name);
			session.setAttribute("type", "admin");
			return "home_admin";
		} else {
			return "error_login_admin";
		}
	}
	@GetMapping("/addProduct")
	public String getAddProductPage(HttpServletRequest request) {
		request.setAttribute("error","");
		return "addProduct";
	}
	@GetMapping("/home")
	public String getHome(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null && session.getAttribute("name") != null && session.getAttribute("type") =="admin")  {
			return "home_admin";
		}
		else {
			return "no_login_admin";
		}
	}
	
	@GetMapping("/products")
	public String getProducts(HttpServletRequest request, HttpServletRequest response) {
		HttpSession session = request.getSession(false);	
		if(session != null && session.getAttribute("name") != null)  {
			MongoClient mongoClient = MongoSettingLoc.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
			
			MongoCollection<Products> items = db.getCollection("products",Products.class);
			
			MongoCursor<Products> cursor = items.find().iterator();
			List<Products> products = new ArrayList<Products>();
	        while(cursor.hasNext()) {
	        	products.add(cursor.next());
	        }
	        request.setAttribute("prods", products);
			return "products_admin";
		}
		else {
			return "no_login";
		}
		
	}
	
	@PostMapping("/addProduct")
	public String addProduct(HttpServletRequest request){
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		String error = "";
		
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
			error = "Product information cannot be left blank!";
			request.setAttribute("error",error);
		}
		
		return "addProduct";
	}
	
	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null) {
			session.invalidate();
			
		}
		return "logged_out";
	}
}
