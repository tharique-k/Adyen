package com.ecommapp.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Cart;
import com.ecommapp.models.Products;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("/home")
public class HomePageController {
	
	@PostMapping("/login")
	public String doLogin(HttpServletRequest request) {
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
		String view = "";
		if (cursor.hasNext()) {
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(15*60); //15 minutes
			view = "home";
			session.setAttribute("name", name);
			session.setAttribute("type", "customer");
		} else {
			view = "no_login";
		}
		
		return view;
	}
	
	@GetMapping("/cart")
	public String getShoppingCart(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
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
			request.setAttribute("cart", cart);
			return "shoppingCart";
		}
		else {
			return "no_login";
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
			return "products";
		}
		else {
			return "no_login";
		}
		
	}
	
	@GetMapping("/home")
	public String getHome(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null && session.getAttribute("name") != null)  {
			return "home";
		}
		else {
			return "no_login";
		}
		
	}
	
	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null) {
			session.invalidate();
			
		}
		return "logged_out";
	}
	
	@GetMapping("/<>")
	public String getReplace(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null && session.getAttribute("name") != null)  {
			return "replace";
		}
		else {
			return "no_login";
		}
		
	}
	
	
}
