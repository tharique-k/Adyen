package com.ecommapp.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("/")
public class AccessController {
	
	@GetMapping("/")
	public String previewLogin() {
		return "home";
	}
	
	@PostMapping("/login")
	public String doCustomerLogin(HttpServletRequest request) {
		
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
		}
		else {
			view = "no_login";
		}
		return view;
	}

}
