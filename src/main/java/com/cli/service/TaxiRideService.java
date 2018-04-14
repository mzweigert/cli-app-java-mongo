package com.cli.service;

import com.cli.mongo.ClientConnection;
import com.cli.util.AppScanner;
import com.cli.util.Properties;
import com.cli.util.Utilities;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    public void createIndex() {

        final AtomicReference<String> correctKey = new AtomicReference<>();
        System.out.println("For what key you want add index?");
        printAvailableKeys();
        utilities.doActionOrBack((key) -> {
            if(availableKeys.contains(key)){
                 correctKey.set(key);
                 return true;
            } else {
                System.out.println("Typed key not found in available keys, try again.");
                return false;
            }
        });
        if(correctKey.get() != null){
            System.out.println("Type what type of index you want to crate for: descending or ascending");
            utilities.doActionOrBack((type) -> {
                Optional<Bson> index = createIndexByType(type, correctKey.get());
                if(index.isPresent()){
                    collection.createIndex(index.get());
                    System.out.println("Created index on: " + correctKey.get() + " of type: " + type);
                    return true;
                } else {
                    System.out.println("Incorrect index type. Try again or Back");
                    return false;
                }
            });
        }
    }

    private Optional<Bson> createIndexByType(String line, String key){
        if(IndexType.DESCENDING.isEqual(line)) {
            return Optional.ofNullable(Indexes.descending(key));
        } else if (IndexType.ASCENDING.isEqual(line)){
            return Optional.ofNullable(Indexes.ascending(key));
        }
        return Optional.empty();
    }

    enum IndexType {
        DESCENDING,
        ASCENDING;

        public boolean isEqual(String type){
            return this.name().equalsIgnoreCase(type);
        }

    }
    public void delete(Document document) {
        utilities.showDialog(document,
                (doc) -> {
                    DeleteResult deleteResult = collection.deleteOne(doc);
                    if (deleteResult.getDeletedCount() == 1) {
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

    public boolean edit(Document document) {
        System.out.println("Tell me, what values want to change? Type back to back to previous menu");
        printAvailableKeys();
        utilities.doActionOrBack((key) -> {
            String value;
            if (!availableKeys.contains(key)) {
                System.out.println("Key not exist in available keys set! Try again or back.");
            } else {
                System.out.println("Type new value");
                value = AppScanner.nextLine();
                Bson updateOperationDocument = new Document("$set", new BasicDBObject(key, value));
                UpdateResult updateResult = collection.updateOne(document, updateOperationDocument);
                if (updateResult.getModifiedCount() == 1) {
                    System.out.println("Updated!");
                } else {
                    System.out.println("Something gone wrong!");
                }
                System.out.println("Give other key or back.");
            }
            return false;
        });
        return false;
    }

    public void printAvailableKeys() {
        if (!availableKeys.isEmpty()) {
            System.out.println("Available keys: " + availableKeys);
        } else {
            System.out.println("No available keys ...");
        }
    }
}

