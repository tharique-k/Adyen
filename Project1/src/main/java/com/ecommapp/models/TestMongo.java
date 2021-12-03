package com.ecommapp.models;

import static com.mongodb.client.model.Filters.eq;

import org.bson.types.ObjectId;

import com.ecommapp.database.mongo.MongoSettingLoc;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class TestMongo {
	
	public static void main(String args[]) {
		
		MongoClient mongoClient = MongoSettingLoc.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoSettingLoc.DbName);
//		Item newItem = new Item("two","item2");
		MongoCollection<Item> items = db.getCollection("items",Item.class);
//		items.insertOne(newItem);
//		Item item = items.find(eq("name","two")).first();
//		System.out.println(item.getValue());
		MongoCursor<Item> cursor = items.find().iterator();
		Item itemN;
		while (cursor.hasNext()) {
			itemN = cursor.next();
			System.out.println("-- "+ itemN.getName()+ " : " +itemN.getValue());
		}

	
	
		
	}

}
