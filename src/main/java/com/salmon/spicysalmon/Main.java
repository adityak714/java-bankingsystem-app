package com.salmon.spicysalmon;

import com.salmon.spicysalmon.controllers.AuthenticationController;

public class Main {
    public static void main(String[] args) {
        AuthenticationController authenticationController = new AuthenticationController();
        int userInput = 0;
        do {
            System.out.println(Util.EOL + "Login Menu" + Util.EOL +
                    "1. Customer Login" + Util.EOL +
                    "2. Account Application" + Util.EOL +
                    "3. Employee Login" + Util.EOL);
            userInput = Util.readInt("Please choose an option: ");
            switch (userInput) {
                case 0 -> System.out.println("goodbye :(");
                case 1 -> authenticationController.customerLogin();
                case 2 -> System.out.println("Application login");
                case 3 -> authenticationController.loginEmployee();
                default -> System.out.println("Please input a valid option.");
            }
            /*
            if(userInput == 1){
                loginMenu.loginCustomer();
            } else if(userInput == 2){
                System.out.println("Application login");
            } else if(userInput == 3){
                loginMenu.loginEmployee();
            } else{
                System.out.println("Please input a valid option.");
            }
             */
        }while(userInput < 0 || userInput > 3);
    }
}
