package com.ecommapp.models;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
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
	private float total = 0.0F;
	
	
	public Cart() {
		
	}
	public Cart(String name) {
		this.name = name;
	}

	public Cart(String name, List<Products> products) {
		
		this.name = name;
		this.products  = products;
		Iterator<Products> itr = products.iterator();
		while(itr.hasNext()) {	
			this.total += itr.next().getPrice();
		}
		
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
			this.total+=product.getPrice();
			return true;
		
	}
	/**
	 * 
	 * @param pid
	 * @return true if the product is found and delted.
	 * false if the product is not found.
	 */
	public boolean removeProduct(Products product) {	
		
		if	(this.products.remove(product)) {
			total -= product.getPrice();
			return true;
		}
		else return false;	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	public float getTotal() {
		return this.total;
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
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", this.name);
		MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
		if(cursor.hasNext()) {
				
				Bson filter = eq("name", name);
				Bson update = addEachToSet("products",this.products);
				coll.updateOne(filter, update);
				update = set("total",this.total);
				coll.updateOne(filter, update);
				
		}
		else {

			MongoCollection<Cart> items = db.getCollection("carts",Cart.class);
			items.insertOne(this);
		}
	}



}