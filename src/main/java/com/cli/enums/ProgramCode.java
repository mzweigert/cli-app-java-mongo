package com.cli.enums;

import com.cli.util.Properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ProgramCode {

    EXIT("exit"),
    BACK("back"),
    HELP("help"),
    FIND_BY_TAXI_ID("findByTaxiId");

    private String value;
    private String description;

    ProgramCode(String value) {
        this.value = value;
        this.description = Properties.getProgramCodeDescription(this);
    }

    public static String getOptionsWithDescription() {
        List<ProgramCode> programCodes = new ArrayList(Arrays.asList(values()));
        programCodes.remove(BACK);
        programCodes.remove(HELP);
        return programCodes.stream()
                .map(c -> String.format(" %s - %s |", c.value, c.description))
                .reduce("", String::concat);
    }

    public boolean isEqual(String value) {
        return this.value.equalsIgnoreCase(value);
    }

    public boolean isNotEqual(String value) {
        return !isEqual(value);
    }

    public static boolean codeExist(final String choose) {
        return Arrays.stream(values())
                .anyMatch(c -> c.isEqual(choose));
    }
}
