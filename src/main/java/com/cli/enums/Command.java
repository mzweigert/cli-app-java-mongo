package com.cli.enums;

import com.cli.util.Properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Command {

    EXIT("exit"),
    BACK("back"),
    HELP("help"),
    FIND_BY_TAXI_ID("findByTaxiId"),
    FIND("find"),
    FIND_ALL("findAll"),
    DELETE("delete"),
    EDIT("edit"),
    NEXT("next"),
    YES("yes"),
    NO("no"),
    COUNT("count"),
    SHOW("show");

    private String value;
    private String description;

    Command(String value) {
        this.value = value;
        this.description = Properties.getProgramCodeDescription(this);
    }

    public static String getMainMenuCommandsDescription() {
        return createCommandsDescription(getMainMenuCommands());
    }

    public static List<Command> getMainMenuCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(FIND_BY_TAXI_ID);
        commands.add(FIND);
        commands.add(FIND_ALL);
        commands.add(HELP);
        return commands;
    }

    public static String getFindOptionCommandsDescription() {
        return createCommandsDescription(getFindOptionCommands());
    }

    public static List<Command> getFindOptionCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(DELETE);
        commands.add(EDIT);
        commands.add(COUNT);
        commands.add(SHOW);
        commands.add(NEXT);
        commands.add(HELP);
        commands.add(BACK);
        return commands;
    }

    private static String createCommandsDescription(List<Command> commands) {
        return commands.stream()
                .map(c -> String.format(" %s - %s |",
                        c.value,
                        c.description))
                .reduce("", String::concat);
    }

    public boolean isEqual(String value) {
        return this.value.equalsIgnoreCase(value);
    }

    public boolean isNotEqual(String value) {
        return !isEqual(value);
    }

    public boolean startsWith(String line) {
        return line.toLowerCase().startsWith(this.value.toLowerCase());
    }

    public static boolean isMainMenuCommand(String choose) {
        return getMainMenuCommands().stream()
                .anyMatch(c -> c.isEqual(choose));
    }

    public static boolean isFindOptionCommand(String choose) {
        return getFindOptionCommands().stream()
                .anyMatch(c -> c.isEqual(choose));
    }

    public static boolean isNotAnswerWord(String answer) {
        return YES.isNotEqual(answer) && NO.isNotEqual(answer) && BACK.isNotEqual(answer);
    }
}
