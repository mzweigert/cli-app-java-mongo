package com.cli.util;

import com.cli.enums.Command;
import org.bson.Document;

import java.util.function.Consumer;
import java.util.function.Function;

public class Utilities {

    public void printMainInfo(){
        System.out.println("Tell me what you want do?");
        System.out.println("Type \"exit\" to quit app, help to print available commands");
    }

    public void showDialog(Document value, Consumer<Document> yesAction, Consumer<Document> noAction) {
        System.out.println("Are you sure? yes/ no or back");
        String answer = null;
        while (Command.BACK.isNotEqual(answer)) {
            answer = AppScanner.nextLine();
            if(Command.isNotAnswerWord(answer)){
                System.out.println("Command incorrect. Type yes / no or back");
            } else if (Command.YES.isEqual(answer)) {
                yesAction.accept(value);
                return;
            } else if (Command.NO.isEqual(answer)) {
                noAction.accept(value);
                return;
            }
        }
        System.out.println("Back..");
    }

    public void doActionOrBack(Function<String, Boolean> action){
        String line;
        boolean canBack;
        do {
            line = AppScanner.nextLine();
            if (Command.BACK.isEqual(line)) {
                System.out.println("Back...");
                printMainInfo();
                break;
            } else {
                canBack = action.apply(line);
            }
            if(canBack){
                break;
            }

        } while (Command.BACK.isNotEqual(line));

    }
}
