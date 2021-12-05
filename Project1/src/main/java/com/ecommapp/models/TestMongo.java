package com.ecommapp.models;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Updates.*;


public class TestMongo {
	
	public static void main(String args[]) {
		
		add();
//		update();
//		showAll();
//		remove();
//		removeAll();
		
	}
	public static void add() {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> coll = db.getCollection("carts");
		
		List<Long> products = new ArrayList<Long>();
		products.add(new Date().getTime());
		products.add(new Date().getTime()+2);
		products.add(new Date().getTime()+5);
		
		String name = "user1";
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", name);
		
		MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
		if (cursor.hasNext()) {
			
			Bson filter = eq("name", name);
			Bson update = pushEach("products", products);
			coll.updateOne(filter, update);
			System.out.println("updated existing");
			
		}
		else {
			
			Document doc = new Document("name", "user1").
			        append("products", products);
			coll.insertOne(doc);
			System.out.println("added new");
		}
	}
	
	public static void remove() {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);

		MongoCollection<Document> coll = db.getCollection("carts");
		String name = "user1";
		Long prod = 1638592572275L;
		
		Bson filter = eq("name", name);
		Bson update = pull("products", prod);
		UpdateResult result = coll.updateOne(filter, update);

		System.out.println("update Done");
		
	}
	public static void showAll() {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> coll = db.getCollection("carts");
		String name = "user1";
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", name);
		
		MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
		try {
			while (cursor.hasNext()) {
				Document crs = cursor.next();

			    List<Long> list = (List<Long>)crs.get("products");
			    Iterator<Long> itr = list.iterator();
			    while(itr.hasNext()) {
			    	System.out.println("value :"+itr.next());
			    }
			}
		}
		finally {
			System.out.println("update Done");
		}
	}
	public static void removeAll() {
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
		MongoCollection<Document> coll = db.getCollection("carts");
		String name = "user1";
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", name);
		
		MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
		try {
			while (cursor.hasNext()) {
				Document crs = cursor.next();

			    List<Long> list = (List<Long>)crs.get("products");
			    Iterator<Long> itr = list.iterator();
			    while(itr.hasNext()) {
			    	Bson filter = eq("name", name);
					Bson update = pull("products", itr.next());
					UpdateResult result = coll.updateOne(filter, update);
					
			    }
			}
		}
		finally {
			System.out.println("update Done");
		}
	}
	
	public static void update() {
		
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);

		MongoCollection<Document> coll = db.getCollection("carts");
		String name = "user1";
		
		Bson filter = eq("name", name);
		Bson update = push("products", new Date().getTime());
		UpdateResult result = coll.updateOne(filter, update);
		
	}
	
	public void oldTest() {
	/*	Item newItem = new Item("two","item2");
//		MongoCollection<Item> items = db.getCollection("items",Item.class);
//		items.insertOne(newItem);
//		Item item = items.find(eq("name","two")).first();
//		System.out.println(item.getValue());
		MongoCursor<Item> cursor = items.find().iterator();
		Item itemN;
		while (cursor.hasNext()) {
			itemN = cursor.next();
			System.out.println("-- "+ itemN.getName()+ " : " +itemN.getValue());
		}

	}*/}

}
