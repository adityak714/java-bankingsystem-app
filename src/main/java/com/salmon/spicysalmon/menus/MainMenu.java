package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.AccountRequestController;
import com.salmon.spicysalmon.controllers.AuthenticationController;
import com.salmon.spicysalmon.models.Menu;

public class MainMenu {

    private final String MAIN_HEADING = "Main Menu: Please choose an option below.";
    private final String[] MAIN_OPTIONS = {
            "Close System",
            "Login as an existing user",
            "Sign up for account",
            "Authorized Personnel"
    };

    public void show(){
        int userInput = 0;
        Menu mainMenu = new Menu(MAIN_HEADING, MAIN_OPTIONS);
        AuthenticationController authenticationController = new AuthenticationController();
        do{
            System.out.print(mainMenu);
            userInput = mainMenu.getValidOption();
            switch (userInput) {
                case 1:
                    authenticationController.customerLogin();
                    break;
                case 2:
                    createCustomerAccountRequest();
                    break;
                case 3:
                    authenticationController.employeeLogin();
                default:
                    break;
            }
        } while(userInput != 0);
    }

    // read the components necessary for a customer account request and then create that request
    public void createCustomerAccountRequest(){
        AccountRequestController accountRequestController = new AccountRequestController();
        String firstName = Util.readLine("Enter your first name: ");
        String lastName = Util.readLine("Enter your last name: ");
        String password = Util.readNewPassword();
        String SSN = Util.readLine("Enter your social security number: ");
        double salary = Util.readDouble("Enter your salary: ");
        String residentialArea = Util.readLine("Enter your area of residence: ");
        String occupation = Util.readLine("Enter your occupation: ");
        accountRequestController.createCustomerAccountRequest(SSN, password, firstName, lastName, salary, residentialArea, occupation);
        System.out.println("Your application has been sent. We will inform via mail when we have made a decision");
        System.out.println("Thank you for choosing Spicy Salmon Bank!");
    }
}
