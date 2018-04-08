package com.cli.mongo;

import com.cli.Properties;
import com.cli.code.ProgramCode;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Arrays;
import java.util.Scanner;

public class Run {

    static MongoClient mongo = new MongoClient(new ServerAddress(Properties.HOST, Properties.PORT_REPLICA_1));
    /*
    static MongoClient mongo = new MongoClient(Arrays.asList(
            new ServerAddress(Properties.HOST, Properties.PORT_REPLICA_1),
            new ServerAddress( Properties.HOST, Properties.PORT_REPLICA_2),
            new ServerAddress( Properties.HOST, Properties.PORT_REPLICA_3)
    ));*/

    public static void main(String[] args) {
        System.out.println("Tell me what you want do?");
        System.out.println("Type \"exit\" to quit app");

        Scanner scanner = new Scanner(System.in);
        String choose = null;
        do  {
            choose = scanner.nextLine();
            if(!ProgramCode.codeExist(choose)) {
                System.out.println(String.format("Code : %s not found", choose));
            }
        } while (ProgramCode.EXIT.isNotEqual(choose));
        System.out.println("Bye bye!");
    }
}
