package com.cli.mongo;

import com.cli.util.Properties;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public final class ClientConnection {

    private static MongoClient mongo;

    public static void close() {
        getClient().close();
    }

    public static MongoClient getClient() {
        if (mongo == null) {
            mongo = new MongoClient(Arrays.asList(
                    new ServerAddress(com.cli.util.Properties.HOST, Properties.PORT_REPLICA_1)/*,
                    new ServerAddress(Properties.HOST, Properties.PORT_REPLICA_2),
                    new ServerAddress(Properties.HOST, Properties.PORT_REPLICA_3)*/
            ));
        }
        return mongo;
    }


}