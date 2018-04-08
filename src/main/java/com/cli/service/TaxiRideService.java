package com.cli.service;

import com.cli.util.Properties;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class TaxiRideService {

    private final MongoCollection<Document> collection;

    private TaxiRideService(MongoClient client) {
        if(client == null) {
            throw new IllegalArgumentException("MongoClient cannot be null!");
        }
        this.collection = client
                .getDatabase(Properties.DB_NAME)
                .getCollection(Properties.COLLECTION_NAME);
    }

    public static TaxiRideService instance(MongoClient client) {
        return new TaxiRideService(client);
    }

    public List<Document> findByTaxiId(long taxiId){
        List<Document> resultList = new ArrayList<>();
        FindIterable<Document> iterable = collection.find(new BasicDBObject("taxi_id", taxiId));
        for (Object next : iterable) {
            resultList.add((Document) next);
            System.out.println(((Document) next).toJson());
        }
        return resultList;
    }
}
