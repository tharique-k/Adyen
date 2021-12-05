package com.ecommapp.models;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pushEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Updates.*;

public class Cart implements HttpSessionBindingListener{

	private String name;
	private List<Products> products = new ArrayList<Products>();
	
	public Cart() {
		
	}
	public Cart(String name) {
		this.name = name;
	}

	public Cart(String name, List<Products> products) {
		
		this.name = name;
		this.products  = products;
		
	}
	public static Cart getCart(String name) {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Cart> items = db.getCollection("carts",Cart.class);
		MongoCursor<Cart> cursor = items.find(eq("name",name)).iterator();
		Cart cart;
		if(cursor.hasNext()) {
			cart = cursor.next();
		}
		else {
			cart = new Cart(name);
		}
        return cart;
	}
	
	
	 /*@Override
	    public void valueBound(HttpSessionBindingEvent event) {
	        this.persist();
	    } */

	    @Override
	    public void valueUnbound(HttpSessionBindingEvent event) {
	    	this.persistCart();
	    }
	/**
	 * 
	 * @return false if the product already exists in the cart
	 */
	public boolean addProduct(Products product) {	
		Iterator<Products> itr = this.products.iterator();
					while(itr.hasNext()) {
						if(itr.next().getPid() == product.getPid()) {
							return false;
						}
					}
			this.products.add(product);
			return true;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Products> getProducts() {
		return products;
	}


	public void setProducts(List<Products> products) {
		this.products = products;
	}
	public void persistCart() {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> coll = db.getCollection("carts");
		
		Iterator<Products> itr = this.products.iterator();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", this.name);
		MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
		if(cursor.hasNext()) {
				
				Bson filter = eq("name", name);
				Bson update = addEachToSet("carts",this.products);
				coll.updateOne(filter, update);
				
		}
		else {

			MongoCollection<Cart> items = db.getCollection("carts",Cart.class);
			items.insertOne(this);
		}
	}



}