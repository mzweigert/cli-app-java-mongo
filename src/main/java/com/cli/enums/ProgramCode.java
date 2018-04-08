package com.cli.enums;

import java.util.Arrays;

public enum ProgramCode {

    EXIT("exit"),
    GET_BY_TAXI_ID("getByTaxiId");

    private String value;

    ProgramCode(String value) {
        this.value = value;
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