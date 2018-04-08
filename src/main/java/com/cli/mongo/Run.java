package com.cli.mongo;

import com.cli.util.InputValidator;
import com.cli.util.Properties;
import com.cli.enums.ProgramCode;
import com.cli.service.TaxiRideService;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Optional;
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
            } else {
                Run.doAction(choose, scanner);
            }
        } while (ProgramCode.EXIT.isNotEqual(choose));
        System.out.println("Bye bye!");
        mongo.close();
    }

    private static void doAction(String choose, Scanner scanner) {
        if(ProgramCode.GET_BY_TAXI_ID.isEqual(choose)){
            System.out.println("Type taxi id or exit to return");
            Optional<Long> result = InputValidator.instance().tryParseLong(scanner);
            result.ifPresent(id -> TaxiRideService.instance(mongo).findByTaxiId(id));
        }
    }

}
