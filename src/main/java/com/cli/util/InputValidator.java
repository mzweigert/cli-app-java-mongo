package com.cli.util;

import com.cli.enums.ProgramCode;

import java.util.Optional;
import java.util.Scanner;

public class InputValidator {

    private InputValidator(){

    }

    public static InputValidator instance(){
        return new InputValidator();
    }

    public Optional<Long> tryParseLong(Scanner scanner) {
        boolean correct = false;
        Long valueLong = null;
        do {
            String line = scanner.nextLine();
            if(ProgramCode.BACK.isEqual(line)){
                return Optional.empty();
            }
            try {
                valueLong = Long.parseLong(line);
                correct = true;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect number format, try again!");
            }
        } while (!correct);

        return Optional.of(valueLong);
    }
}
