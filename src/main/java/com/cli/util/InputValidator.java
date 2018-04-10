package com.cli.util;

import com.cli.enums.Command;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class InputValidator {

    private InputValidator(){

    }

    public static InputValidator instance(){
        return new InputValidator();
    }

    public Optional<Number> tryParseNumber() {
        boolean correct = false;
        Number valueLong = null;
        do {
            String line = AppScanner.nextLine();
            if(Command.BACK.isEqual(line)){
                return Optional.empty();
            }
            try {
                valueLong = NumberFormat.getInstance().parse(line);
                correct = true;
            } catch (ParseException e) {
                System.out.println("Incorrect number format, try again!");
            }
        } while (!correct);

        return Optional.of(valueLong);
    }

    public Map<Object, Object> createCriteria(String line) {
        Map<Object, Object> criteria = new HashMap<>();
        String[] values = line.split(",");
        for (String value : values){
            String[] keyValue = value.split("\\s*( |:|\\s)\\s*");
            if(keyValue.length == 2 && keyValueNotEmpty(keyValue[0], keyValue[1])){
                criteria.put(keyValue[0], checkTypeValue(keyValue[1]));
            }
        }
        return criteria;
    }

    private Object checkTypeValue(String value) {
        try {
            return NumberFormat.getInstance().parse(value);
        } catch (ParseException e) {
            return value;
        }
    }

    private boolean keyValueNotEmpty(String key, String value) {
        return key.trim().length() > 0 && value.trim().length() > 0;
    }
}
