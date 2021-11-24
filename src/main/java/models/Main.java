package models;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String EOL = System.lineSeparator();
        System.out.println("Welcome to Salmon Bank!" + EOL +
                "Main Menu: Please choose among the options below." + EOL +

                "0. Exit" + EOL +
                "1. option1" + EOL +
                "2. option2" + EOL +
                "3. option3" + EOL +
                "4. option4" + EOL +
                "5. option5" + EOL);

        int digit = input.nextInt();
        input.nextLine();
        switch (digit) {
            case 0:
                System.out.println("You have chosen: Close system.");
                System.exit(0);
            case 1:
                System.out.println("You have chosen: <text>");
            case 2:
                System.out.println("You have chosen: <text>");
            case 3:
                System.out.println("You have chosen: <text>");
            case 4:
                System.out.println("You have chosen: <text>");
            case 5:
                System.out.println("You have chosen: <text>");
        }
        System.out.println("kladdkaka1");
    }
}
