package com.ecommapp.spring.controller;

import static com.mongodb.client.model.Filters.eq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.ecommapp.models.Cart;
import com.ecommapp.models.Products;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	

	
	@GetMapping("/showProduct")
	public String showProduct(HttpServletRequest request) {
		HttpSession session = request.getSession(false);	
		if(session != null && session.getAttribute("name") != null)  {
			
			long pid = Long.parseLong(request.getParameter("pid"));
			MongoClient client = MongoClients.create(MongoSettingLoc.URL);
			MongoDatabase mb = client.getDatabase(MongoSettingLoc.DbName);
			MongoCollection <Document> mc = mb.getCollection("products");
			MongoCursor<Document> cursor = mc.find(Filters.eq("pid",pid)).iterator();
			Document document = new Document(cursor.next());
			request.setAttribute("product", document);
			
			return "showProduct";
		}
		else {
			return "no_login";
		}
		
	}
	
	
	@GetMapping("addToCart")
	public String addToCart(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("name") != null) {
		String name = (String) session.getAttribute("name");
		Long pid = Long.parseLong(request.getParameter("pid"));
		Cart cart;
		
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
			request.setAttribute("cart", cart);
			return "shoppingCart";
		}
		else {
			
			return "no_login";
		}
		
	}
	@GetMapping("deleteFromCart")
	public String deleteFromCart (HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("name") != null) {
		String name = (String) session.getAttribute("name");
		Long pid = Long.parseLong(request.getParameter("pid"));
		Cart cart;
		
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
				cart.removeProduct(cursor.next());
			}
			request.setAttribute("cart", cart);
			return "shoppingCart";
		}
		else {
			return "no_login";
		}
	
	}
	
}
