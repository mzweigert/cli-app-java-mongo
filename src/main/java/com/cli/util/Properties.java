package com.cli.util;


import java.io.IOException;
import java.io.InputStream;

public class Properties {

    public static final String HOST = getProperty("app.host");
    public static final Integer PORT_REPLICA_1 = Integer.valueOf(getProperty("app.replica.port.first"));
    public static final Integer PORT_REPLICA_2 = Integer.valueOf(getProperty("app.replica.port.second"));
    public static final Integer PORT_REPLICA_3 = Integer.valueOf(getProperty("app.replica.port.third"));
    public static final String DB_NAME = getProperty("app.db.name");
    public static final String COLLECTION_NAME = getProperty("app.db.collection.name");

    private static String getProperty(String property) {
        java.util.Properties properties = new java.util.Properties();
        InputStream inputStream = null;
        try {
            inputStream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) properties.get(property);
    }
}
