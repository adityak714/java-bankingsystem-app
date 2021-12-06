package com.salmon.spicysalmon;

import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
import com.salmon.spicysalmon.controllers.TransactionController;

import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Employee;
import com.salmon.spicysalmon.models.Transaction;

import java.util.ArrayList;

public class OldMain {
    private static OldMain mainMenu;
    private CustomerController customers;
    private EmployeeController employees;
    private TransactionController transactions;

    ArrayList <Employee> employeeList = new ArrayList<>();
    ArrayList <Transaction> transactionList = new ArrayList<>();

    public OldMain() {
        this.customers = new CustomerController();
        this.employees = new EmployeeController();
        this.transactions = new TransactionController();
    }

    public static void main(String[] args) throws Exception {
        mainMenu = new OldMain();
        mainMenu.printMainMenu();
        mainMenu.mainMenuHandler();
    }

    public void printMainMenu(){
        String EOL = System.lineSeparator();
        System.out.print("-------------------------------------------" + EOL +
                "Main Menu: Please choose among the options below." + EOL +

                "0. Close System." + EOL +
                "1. Open customer options." + EOL +
                "2. Open employee options." + EOL +
                "3. Open transaction options." + EOL +
                EOL +

                "Type an option number: ");
    }

    public void mainMenuHandler() throws Exception {
        int selection = UserIO.readInt();
        switch(selection){
            case 0:
                System.out.println("You have chosen: Close system.");
                System.exit(0);
            case 1:
                System.out.println("You have chosen: Open customer options.");
                printOpenCustomerOptions();
                optionCustomerHandler();
                break;
            case 2:
                System.out.println("You have chosen: Open employee options.");
                //printOpenReviewOptions();
                //openReviewHandler();
                break;
            case 3:
                System.out.println("You have chosen: Open transaction options.");
                //printTransactionHistoryOptions();
                //openTransactionHandler();
                break;
            default:
                System.out.print("Invalid option, Please try again: ");
                mainMenuHandler();
        }
    }

    public void printOpenCustomerOptions() {
        String EOL = System.lineSeparator();
        System.out.print("-------------------------------------------" + EOL +
                "Item options menu: " + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create a customer." + EOL +
                "2. Remove a customer." + EOL +
                "3. Print all registered customers." + EOL +
                "4. Create bank account for a customer." + EOL +
                "5. Print a specific customer." + EOL +
                EOL +

                "Type an option number: ");
    }

    public void optionCustomerHandler() throws Exception {
        while (true) {
            int selection = UserIO.readInt();
            switch (selection) {
                case 0:
                    System.out.println("You have chosen: Return to Main Menu.");
                    printMainMenu();
                    mainMenuHandler();
                    break;
                case 1:
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
                    customers.createCustomer(socialSecurityNumber,password, firstName,lastName, salary, residentalArea, occupation);
                    printOpenCustomerOptions();
                    break;
                case 2:
                    System.out.println("You have chosen: Remove a customer.");
                    System.out.print("What customer do you wish to remove? Enter SSN: ");
                    String remove = UserIO.readStr();
                    System.out.println(customers.removeCustomer(remove));
                    printOpenCustomerOptions();
                    break;
                case 3:
                    System.out.println("You have chosen: Print all registered customers.");
                    System.out.println(customers.printAllCustomers());
                    printOpenCustomerOptions();
                    break;
                case 4:
                    System.out.println("You have chosen: Create bank account for a customer.");
                    System.out.print("Which customer do you wish to create an account for? Enter SSN: ");
                    String SSN = UserIO.readStr();
                    System.out.print("Enter first name: ");
                    firstName = UserIO.readStr();
                    System.out.print("Enter last name: ");
                    lastName = UserIO.readStr();
                    Customer tempCustomer = customers.findCustomer(SSN);
                    tempCustomer.createBankAccount(SSN, firstName, lastName);
                    printOpenCustomerOptions();
                    break;
                case 5:
                    System.out.println("You have chosen: Print a specific customer.");
                    System.out.print("Enter customer SSN: ");
                    SSN = UserIO.readStr();
                    System.out.println(customers.printSpecificCustomer(SSN));
                    printOpenCustomerOptions();
                    break;
                default:
                    System.out.print("Invalid Option. Please try again: ");
            }
        }
    }
}
