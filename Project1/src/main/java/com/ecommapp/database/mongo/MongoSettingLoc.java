package com.ecommapp.database.mongo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoSettingLoc {
	public static  String URL = "mongodb+srv://testUser:testUser@cluster0.fsog7.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
	public static  String DbName ="ecomm"; 
	
	
	public static MongoClient getMongoClient() {
	ConnectionString connectionString = new ConnectionString(URL);
	CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
	CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
	MongoClientSettings clientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build();
	return MongoClients.create(clientSettings);
	}

}
