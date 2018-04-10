package com.cli.service;

import com.cli.enums.Command;
import com.cli.mongo.ClientConnection;
import com.cli.util.AppScanner;
import com.cli.util.Properties;
import com.cli.util.Utilities;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;


public class TaxiRideService {

    private final MongoCollection<Document> collection;
    private Utilities utilities;
    private Set<String> availableKeys;

    private TaxiRideService() {
        this.utilities = new Utilities();
        this.collection = ClientConnection.getClient()
                .getDatabase(com.cli.util.Properties.DB_NAME)
                .getCollection(Properties.TAXI_RIDES_COLLECTION_NAME);
        MongoCursor<Document> iterator = collection.find().limit(1).iterator();
        this.availableKeys = iterator.hasNext() ? iterator.next().keySet() : new HashSet<>();
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
        utilities.showDialog(document,
                (doc) -> {
                    DeleteResult deleteResult = collection.deleteOne(doc);
                    if(deleteResult.getDeletedCount() == 1) {
                        System.out.println("Deleted!");
                    } else {
                        System.out.println("Something gone wrong!");
                    }
                },
                (doc) -> System.out.println("Back.."));
    }


    public void printCollectionCount(Map<Object, Object> criteria) {
        System.out.println("Records found: " + getCollectionCount(criteria));
    }

    public long getCollectionCount(Map<Object, Object> criteria) {
        return collection.count(new BasicDBObject(criteria));
    }

    public void edit(Document document) {
        System.out.println("Tell me, what values want to change? Type back to back to previous menu");
        printAvailableKeys();
        String key, value;
        do {
            key = AppScanner.nextLine();
            if(Command.BACK.isEqual(key)){
                return;
            } else if(!availableKeys.contains(key)){
                System.out.println("Key not exist in available keys set! Try again or back.");
            } else {
                System.out.println("Type new value");
                value = AppScanner.nextLine();
                Bson updateOperationDocument = new Document("$set", new BasicDBObject(key, value));
                UpdateResult updateResult = collection.updateOne(document, updateOperationDocument);
                if(updateResult.getModifiedCount() == 1){
                    System.out.println("Updated!");
                } else {
                    System.out.println("Something gone wrong!");
                }
                System.out.println("Give other key or back.");
            }
        } while (Command.BACK.isNotEqual(key));
    }

    public void printAvailableKeys() {
        if(!availableKeys.isEmpty()){
            System.out.println("Available keys: " + availableKeys);
        } else {
            System.out.println("No available keys ...");
        }
    }
}

