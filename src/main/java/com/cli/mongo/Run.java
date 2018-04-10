package com.cli.mongo;

import com.cli.service.ActionService;
import com.cli.util.AppScanner;
import com.cli.util.Properties;
import com.cli.enums.Command;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {

    public static void main(String[] args) {
        Logger.getLogger( "org.mongodb.driver" ).setLevel(Level.WARNING);
        ActionService actionService = ActionService.instance();
        actionService.rootAction(actionService::mainMenuAction);
        System.out.println("Bye bye!");
        ClientConnection.close();
    }
}
