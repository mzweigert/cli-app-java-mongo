package com.cli.service;

import com.cli.mongo.ClientConnection;
import com.cli.util.Properties;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.*;


public class TaxiRideService {


    private final MongoCollection<Document> collection;

    private TaxiRideService() {
        this.collection = ClientConnection.getClient()
                .getDatabase(com.cli.util.Properties.DB_NAME)
                .getCollection(Properties.TAXI_RIDES_COLLECTION_NAME);
    }

    public static TaxiRideService instance() {
        return new TaxiRideService();
    }

    public FindIterable<Document> find(Map<Object, Object> criteria) {
        return collection.find(new BasicDBObject(criteria));
    }

    public FindIterable<Document> findAll() {
        return collection.find();
    }

    public void delete(Document document) {
        collection.deleteOne(document);
    }

    public void printAvailableKeys() {
        MongoCursor<Document> iterator = collection.find().limit(1).iterator();
        if (iterator.hasNext()) {
            System.out.println("Available keys: " + iterator.next().keySet());
        } else {
            System.out.println("Nothing found :( ");
        }
    }

    public long getCollectionCount() {
        return collection.count();
    }

    public long getCollectionCount(Map<Object, Object> criteria) {
        return collection.count(new BasicDBObject(criteria));
    }
}

