package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.ApplicationController;
import com.salmon.spicysalmon.controllers.AuthenticationController;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Menu;

public class EmployeeMenu {
    String EMPLOYEE_HEADING = "Employee Menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS = {
            "Log out",
            "Menu for Customer handling",
            "Menu for Application handling",
    };
    String EMPLOYEE_HEADING2 = "Customer handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS2 = {
            "Go back",
            "Access specific customer",
            "create customer",
            "delete customer",
            "Print all registered customers.",
            // is these 2 options (4, 5) agreed upon functions of the employee?
            "deposit money into bank account ", // this function is used when a customer meets with an employee in person and has cash that they want to deposit
            "withdraw money from bank account", // this function is used when a customer meets with an employee in person and has cash that they want to withdraw
            ""
            // Continue to add more options here and to the switch case as you see fit, it's ok to create submenus if anyone want to do that,
            // just make a switch case inside the switch case (or make a seperate method that is called inside the switch case)
    };
    String EMPLOYEE_HEADING3 = "Account request handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS3 = {
            "Go back",
            "review specific customer account request",
            "review specific bank account requset",
            "list all customer account request",
            "list all bank account request"
    };

    // the first menu the employee will see, this then branches of into a Customer and a Account request Menu
    public void show(String SSN){
        // use these objects to access the methods in the controllers
        // pass in controller into methods
        // remove userinput as arguments

        ApplicationController accountRequestController = new ApplicationController();
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        AuthenticationController authenticationController = new AuthenticationController();

        Menu employeeMenu = new Menu(EMPLOYEE_HEADING, EMPLOYEE_OPTIONS);
        int userInput = 0;
        do {
            System.out.println(employeeMenu);
            userInput = employeeMenu.getValidOption();
            switch (userInput) {
                case 0 -> System.out.println("goodbye");
                case 1 -> showCustomerMenu(customerController, authenticationController);
                case 2 -> showAccountRequestMenu(accountRequestController);
            }
        }while (userInput != 0);
    }
    // Customer menu that handles all the functionality were the Employee directly interacts with customers
    public void showCustomerMenu(CustomerController customerController, AuthenticationController authenticationController){
        Menu employeeCustomerMenu = new Menu(EMPLOYEE_HEADING2, EMPLOYEE_OPTIONS2);
        System.out.println(employeeCustomerMenu);
        int userInput = employeeCustomerMenu.getValidOption();
        do {
            switch (userInput){
                case 1:
                    goToCustomer(authenticationController);
                    break;
                case 2: // create customer
                    createCustomer(customerController);
                    break;
                case 3: // remove customer
                    removeCustomer(customerController);
                    break;
                case 4: // print all customers
                    printAllCustomers(customerController);
                    break;
                case 5:

                    break;
                case 6:

                    break;
            }
            break;
        }while (userInput != 0);
    }

    // Account request menu that handles all the functionality were the Employee directly interacts with Account Requests
    public void showAccountRequestMenu(ApplicationController accountRequestController){
        Menu employeeAccountRequestMenu = new Menu(EMPLOYEE_HEADING3,EMPLOYEE_OPTIONS3);
        System.out.println(employeeAccountRequestMenu);
        int userInput = employeeAccountRequestMenu.getValidOption();
        /*

        switch (userInput){
            case 1: // approve/deny customer application
                approveOrDenyUserAccountRequest(accountRequestController);
                break;
            case 2: // approve/deny bank application
                approveOrDenyBankAccountRequest(accountRequestController);
                break;
            case 3:
                listAllCustomerAccountRequests(accountRequestController);
                break;
            case 4:
                listAllBankAccountRequests(accountRequestController);
                break;
        }

         */
    }

    public void goToCustomer(AuthenticationController authenticationController){
        System.out.println("type in the login info of the customer you want to access");
        authenticationController.customerLogin();
    }

    public void createCustomer(CustomerController customerController){
        System.out.println("You have chosen: Create a customer.");
        String socialSecurityNumber = Util.readLine("What is your SSN?: ");
        String password = Util.readLine("Create a new password: ");
        String firstName = Util.readLine("What is your first name?: ");
        String lastName = Util.readLine("What is your last name?: ");
        int salary = Util.readInt("What is your salary?: ");
        String residentalArea = Util.readLine("Where do you live?: ");
        String occupation = Util.readLine("What is your occupation?: ");
        customerController.createCustomer(socialSecurityNumber,password, firstName,lastName, salary, residentalArea, occupation);
        // should prob remove the excepction
    }
    public void removeCustomer(CustomerController customerController){
        System.out.println("You have chosen: Remove a customer.");
        String remove = Util.readLine("What customer do you wish to remove? Enter SSN: ");
        System.out.println(customerController.removeCustomer(remove));
    }
    public void printAllCustomers(CustomerController customerController){
        System.out.println("You have chosen: Print all registered customers.");
        System.out.println(customerController.printAllCustomers());
    }
/*
    public void approveOrDenyUserAccountRequest(ApplicationController accountRequestController){
        String SSN = Util.readLine("Which customers request do you want to look at?");
        CustomerAccountRequest CAR = accountRequestController.getCustomerAccountRequests(SSN);
        System.out.println(CAR.toString());
        Util.readLine("Approve" + Util.EOL + "Deny");
    }

    public void approveOrDenyBankAccountRequest(ApplicationController accountRequestController){
        String SSN = Util.readLine("Which customers request do you want to look at?");
        BankAccountRequest BAR = accountRequestController.getBankAccountRequests(SSN);
        System.out.println(BAR.toString());
        Util.readLine("Approve" + Util.EOL + "Deny");
    }

    public void listAllCustomerAccountRequests(ApplicationController accountRequestController){
        accountRequestController.printallCustomerAccountRequests();
        int requestNum = Util.readInt("Which request do you want to check out?");
        BankAccountRequest request = requests.get(requestNum);
        System.out.println(request.toString());
        // Util.readLine("Which request do you want to check out?");
    }

    public void listAllBankAccountRequests(ApplicationController accountRequestController){
        ArrayList<BankAccountRequest> requests = accountRequestController.getallBankAccountRequests();
        accountRequestController.printallBankAccountRequests();
        int requestNum = Util.readInt("Which request do you want to check out?");
        BankAccountRequest request = requests.get(requestNum);
        System.out.println(request.toString());


    }

 */

}

