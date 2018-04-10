package com.cli.service;

import com.cli.enums.Command;
import com.cli.util.AppScanner;
import com.cli.util.InputValidator;
import com.cli.util.Utilities;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ActionService {

    private final TaxiRideService taxiRideService;
    private final InputValidator inputValidator;
    private final Utilities utilities;

    private ActionService() {
        this.taxiRideService = TaxiRideService.instance();
        this.inputValidator = InputValidator.instance();
        this.utilities = new Utilities();
    }

    public static ActionService instance() {
        return new ActionService();
    }

    public void rootAction(Consumer<String> consumer){
        String choose;
        utilities.printMainInfo();
        do  {
            choose = AppScanner.nextLine();
            if(Command.isMainMenuCommand(choose)) {
                consumer.accept(choose);
            } else {
                System.out.println(String.format("Command : %s not found, type \"help\" to get commands", choose));
            }
        } while (Command.EXIT.isNotEqual(choose));
    }


    public void mainMenuAction(String choose) {
        if (Command.FIND.isEqual(choose)) {

            System.out.println("Type keys and values separate by space, e.g. key1 val1, key2 val2");
            taxiRideService.printAvailableKeys();
            String line = null;
            Map<Object, Object> criteria = null;
            do {
                line = AppScanner.nextLine();
                criteria = inputValidator.createCriteria(line);
                if(Command.BACK.isEqual(line)) {
                    System.out.println("Back ...");
                    utilities.printMainInfo();
                    return;
                } else if(criteria.size() == 0) {
                    System.out.println("Incorrect criteria");
                }
            } while (criteria.size() == 0);
            FindIterable<Document> documents = taxiRideService.find(criteria);
            findAction(documents, criteria);

        } else if (Command.FIND_BY_TAXI_ID.isEqual(choose)) {

            System.out.println("Type taxi id or back to return");
            Optional<Number> result = inputValidator.tryParseNumber();
            utilities.ifPresent(result, (id) -> {
                Map<Object, Object> criteria = new HashMap<>();
                criteria.put("taxi_id", id);
                FindIterable<Document> documents = taxiRideService.find(criteria);
                findAction(documents, criteria);
            });

        } else if (Command.FIND_ALL.isEqual(choose)) {

            FindIterable<Document> documents = taxiRideService.findAll();
            findAction(documents, new HashMap<>());

        } else if (Command.HELP.isEqual(choose)) {
            System.out.println(Command.getMainMenuCommandsDescription());
        }
    }

    public void findAction(FindIterable<Document> documents, Map<Object, Object> criteria) {
        MongoCursor<Document> iterator = documents.iterator();
        if(!iterator.hasNext()) {
            System.out.println("Nothing found :(");
            utilities.printMainInfo();
            return;
        }
        while (iterator.hasNext()) {
            Document document = iterator.next();
            System.out.println(document.toJson());
            System.out.println("What you want to do with it? ");
            System.out.println(Command.getFindOptionCommandsDescription());
            boolean goToTheNextOne = doOperationOrReturn(document, criteria);
            if(!goToTheNextOne){
                return;
            }
        }
    }

    private boolean doOperationOrReturn(Document document, Map<Object, Object> criteria) {
        String line = null;
        do {
            line = AppScanner.nextLine();
            if(!Command.isFindOptionCommand(line)){
                System.out.println(String.format("Command :%s not found, type \"help\" to get commands", line));
            } else if (Command.DELETE.isEqual(line)) {
                utilities.showDialog(document, taxiRideService::delete, (doc) -> System.out.println("Back.."));
            } else if (Command.EDIT.isEqual(line)) {
                //TODO
            } else if (Command.COUNT.isEqual(line)) {
                System.out.println("Records found: " + taxiRideService.getCollectionCount(criteria));
            } else if (Command.SHOW.isEqual(line)) {
                System.out.println(document.toJson());
            } else if (Command.HELP.isEqual(line)) {
                System.out.println(Command.getFindOptionCommandsDescription());
                taxiRideService.printAvailableKeys();
            } else if(Command.BACK.isEqual(line)) {
                System.out.println("Back..");
                utilities.printMainInfo();
                return false;
            }
        } while (!Command.NEXT.startsWith(line));
        return true;
    }

}
