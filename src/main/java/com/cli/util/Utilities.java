package com.cli.util;

import com.cli.enums.Command;
import org.bson.Document;

import java.util.Optional;
import java.util.function.Consumer;

public class Utilities {

    public <T> void ifPresent(Optional<T> value, Consumer<T> ifPresent) {
        if (value.isPresent()) {
            ifPresent.accept(value.get());
        } else {
            System.out.println("Back to main menu");
            printMainInfo();
        }
    }

    public void printMainInfo(){
        System.out.println("Tell me what you want do?");
        System.out.println("Type \"exit\" to quit app, help to print available commands");
    }

    public void showDialog(Document value, Consumer<Document> yesAction, Consumer<Document> noAction) {
        System.out.println("Are you sure? yes/ no, back");
        String answer = AppScanner.nextLine();
        do {
            if (Command.YES.isEqual(answer)) {
                yesAction.accept(value);
            } else if (Command.NO.isEqual(answer)) {
                noAction.accept(value);
            }
        } while (!Command.BACK.isEqual(answer));
        System.out.println("Back..");
    }
}
