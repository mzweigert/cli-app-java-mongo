package com.cli.util;

import com.cli.enums.Command;

import java.util.Scanner;

public class AppScanner {

    private static final Scanner scanner = new Scanner(System.in);

    public static String nextLine(){
        String choose = scanner.nextLine();
        if(Command.EXIT.isEqual(choose)) {
            System.out.println("Bye bye..");
            System.exit(0);
        }
        return choose;
    }
}
