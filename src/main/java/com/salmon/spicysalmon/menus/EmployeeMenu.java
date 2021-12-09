package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.ApplicationController;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
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
            "0. Go back",
            "1. create customer",
            "2. delete customer",
            "3. Print all registered customers.",
            // is these 2 options (4, 5) agreed upon functions of the employee?
            "4. deposit money into bank account ", // this function is used when a customer meets with an employee in person and has cash that they want to deposit
            "5. withdraw money from bank account", // this function is used when a customer meets with an employee in person and has cash that they want to withdraw
            "6."
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
    public void show(){
        // use these objects to access the methods in the controllers
        // pass in controller into methods
        // remove userinput as arguments

        ApplicationController accountRequestController = new ApplicationController();
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();

        Menu employeeMenu = new Menu(EMPLOYEE_HEADING, EMPLOYEE_OPTIONS);
        int userInput = 0;
        do {
            System.out.println(employeeMenu);
            userInput = employeeMenu.getValidOption();
            switch (userInput) {
                case 0 -> System.out.println("goodbye");
                case 1 -> showCustomerMenu(customerController);
                case 2 -> showAccountRequestMenu(accountRequestController);
            }
        }while (userInput != 0);
    }
    // Customer menu that handles all the functionality were the Employee directly interacts with customers
    public void showCustomerMenu(CustomerController customerController){
        Menu employeeCustomerMenu = new Menu(EMPLOYEE_HEADING2, EMPLOYEE_OPTIONS2);
        System.out.println(employeeCustomerMenu);
        int userInput = employeeCustomerMenu.getValidOption();
        do {
            switch (userInput){
                case 1: // create customer
                    createCustomer(customerController);
                    break;
                case 2: // remove customer
                    removeCustomer(customerController);
                    break;
                case 3: // print all customers
                    printAllCustomers(customerController);
                    break;
                case 4:

                    break;
                case 5:

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
    }

    public void createCustomer(CustomerController customerController){
        System.out.println("You have chosen: Create a customer.");
        System.out.print("What is your SSN?: ");
        String socialSecurityNumber = UserIO.readStr();
        System.out.print("Create a new password: ");
        String password = UserIO.readStr();
        System.out.print("What is your first name?: ");
        String firstName = UserIO.readStr();
        System.out.print("What is your last name?: ");
        String lastName = UserIO.readStr();
        System.out.print("What is your salary?: ");
        int salary = UserIO.readInt();
        System.out.print("Where do you live?: ");
        String residentalArea = UserIO.readStr();
        System.out.print("What is your occupation?: ");
        String occupation = UserIO.readStr();
        customerController.createCustomer(socialSecurityNumber,password, firstName,lastName, salary, residentalArea, occupation);
        // should prob remove the excepction
    }
    public void removeCustomer(CustomerController customerController){
        System.out.println("You have chosen: Remove a customer.");
        System.out.print("What customer do you wish to remove? Enter SSN: ");
        String remove = UserIO.readStr();
        System.out.println(customerController.removeCustomer(remove));
    }
    public void printAllCustomers(CustomerController customerController){
        System.out.println("You have chosen: Print all registered customers.");
        System.out.println(customerController.printAllCustomers());
    }

    public void approveOrDenyUserAccountRequest(ApplicationController accountRequestController){
        String customer = Util.readLine("Which customers request do you want to look at?");
        accountRequestController.getBankAccountRequests();
    }
    public void approveOrDenyBankAccountRequest(ApplicationController accountRequestController){
        String customer = Util.readLine("Which customers request do you want to look at?");
        accountRequestController.getBankAccountRequests();
    }
    public void listAllCustomerAccountRequests(ApplicationController accountRequestController){
        accountRequestController.printallCustomerAccountRequests();
        Util.readLine("Which request do you want to check out?");
    }
    public void listAllBankAccountRequests(ApplicationController accountRequestController){
        ArrayList<BankAccountRequest> requests = accountRequestController.getallBankAccountRequests();
        accountRequestController.printallBankAccountRequests();
        int requestNum = Util.readInt("Which request do you want to check out?");
        BankAccountRequest request = requests.get(requestNum);
        System.out.println(request.toString());


        accountRequestController.get
    }

}

