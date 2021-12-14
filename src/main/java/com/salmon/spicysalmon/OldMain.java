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

    ArrayList<Employee> employeeList = new ArrayList<>();
    ArrayList<Transaction> transactionList = new ArrayList<>();

    public OldMain() {
        this.customers = new CustomerController();
        this.employees = new EmployeeController();
        this.transactions = new TransactionController();
    }

    public static void main(String[] args) {
        mainMenu = new OldMain();
        mainMenu.printMainMenu();
        mainMenu.mainMenuHandler();
    }

    public void printMainMenu() {
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

    public void mainMenuHandler() {
        int selection = UserIO.readInt();
        switch (selection) {
            case 0:
                System.out.println("You have chosen: Close system.");
                System.exit(0);
            case 1:
                System.out.println("You have chosen: Open customer options.");
                printCustomerOptions();
                openCustomerHandler();
                break;
            case 2:
                System.out.println("You have chosen: Open employee options.");
                //printOpenReviewOptions();
                //openReviewHandler();
                break;
            case 3:
                System.out.println("You have chosen: Open transaction options.");
                printTransactionOptions();
                openTransactionHandler();
                break;
            default:
                System.out.print("Invalid option, Please try again: ");
                mainMenuHandler();
        }
    }

    public void printCustomerOptions() {
        String EOL = System.lineSeparator();
        System.out.print("-------------------------------------------" + EOL +
                "Item options menu: " + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create a customer." + EOL +
                "2. Remove a customer." + EOL +
                "3. Print all registered customers." + EOL +
                "4. Create bank account for a customer." + EOL +
                "5. Print a specific customer." + EOL +
                "6. Deposit money" + EOL +
                "7. Delete Bank Account" + EOL +
                "8. Withdraw Money" + EOL +
                "9. Print Balance" + EOL +
                EOL +

                "Type an option number: ");
    }

    public void openCustomerHandler() {
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
                    String residentialArea = UserIO.readStr();
                    System.out.print("What is your occupation?: ");
                    String occupation = UserIO.readStr();
                    customers.createCustomer(socialSecurityNumber, password, firstName, lastName, salary, residentialArea, occupation);
                    printCustomerOptions();
                    break;
                case 2:
                    System.out.println("You have chosen: Remove a customer.");
                    System.out.print("What customer do you wish to remove? Enter SSN: ");
                    String remove = UserIO.readStr();
                    System.out.println(customers.removeCustomer(remove));
                    printCustomerOptions();
                    break;
                case 3:
                    System.out.println("You have chosen: Print all registered customers.");
                    System.out.println(customers.printAllCustomers());
                    printCustomerOptions();
                    break;
                case 4:
                    System.out.println("You have chosen: Create bank account for a customer.");
                    System.out.print("Which customer do you wish to create an account for? Enter SSN: ");
                    String SSN = UserIO.readStr();
                    System.out.print("Enter your first name: ");
                    firstName = UserIO.readStr();
                    System.out.print("Enter your last name: ");
                    lastName = UserIO.readStr();
                    System.out.println("Enter account name: ");
                    String accountName = UserIO.readStr();
                    customers.createBankAccount(SSN, SSN, firstName, lastName, accountName);
                    printCustomerOptions();
                    break;
                case 5:
                    System.out.println("You have chosen: Print a specific customer.");
                    System.out.print("Enter customer SSN: ");
                    SSN = UserIO.readStr();
                    System.out.println(customers.printSpecificCustomer(SSN));
                    printCustomerOptions();
                    break;
                case 6:
                    System.out.println("You have chosen: Deposit money.");
                    System.out.print("Enter customer SSN: ");
                    SSN = UserIO.readStr();
                    UserIO.SCANNER.nextLine();
                    System.out.print("Enter customer account number: ");
                    String accountNumber = UserIO.readStr();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = UserIO.readDouble();
                    customers.depositMoney(SSN, accountNumber, depositAmount);
                    printCustomerOptions();
                    break;
                case 7:
                    System.out.println("You have chosen: Delete bank account.");
                    System.out.println("What account do you wish to delete? Enter account number: ");
                    accountNumber = UserIO.readStr();
                    customers.deleteBankAccount(accountNumber);
                    printCustomerOptions();
                    break;
                case 8:
                    System.out.println("You have chosen: Withdraw Money");
                    System.out.println("Enter SSN: ");
                    SSN = UserIO.readStr();
                    System.out.println("Enter account number: ");
                    accountNumber = UserIO.readStr();
                    System.out.println("Enter amount: ");
                    double withdrawAmount = UserIO.readDouble();
                    customers.withdrawMoney(SSN, accountNumber, withdrawAmount);
                    printCustomerOptions();
                    break;
                case 9:
                    System.out.println("You have chosen: print balance");
                    System.out.println("Enter SSN: ");
                    SSN = UserIO.readStr();
                    customers.printBalance(SSN);
                    printCustomerOptions();
                    break;
                default:
                    System.out.print("Invalid Option. Please try again: ");
            }
        }
    }

    public void printTransactionOptions() {
        String EOL = System.lineSeparator();
        System.out.print("-------------------------------------------" + EOL +
                "Item options menu: " + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create a transaction." + EOL +
                "2. ascendingTransactionsByPriceForAccount" + EOL +
                "3. descendingTransactionsByPriceForAccount" + EOL +
                "4. printTransactionsForAnAccount" + EOL +
                "5. printTransactionsForAllAccounts" + EOL +
                "6. printTransactionsSortedEarliest" + EOL +
                "7. printTransactionsSortedLatest" + EOL +
                EOL +


                "Type an option number: ");
    }

    public void openTransactionHandler() {
        while (true) {
            int selection = UserIO.readInt();
            switch (selection) {
                case 0:
                    System.out.println("You have chosen: Return to Main Menu.");
                    printMainMenu();
                    mainMenuHandler();
                    break;
                case 1:
                    System.out.println("You have chosen: Create a transaction.");
                    System.out.print("Enter to account: ");
                    String accountTo = UserIO.readStr();
                    System.out.print("Enter from account: ");
                    String accountFrom = UserIO.readStr();
                    System.out.print("Enter amount: ");
                    double amount = UserIO.readDouble();
                    transactions.createTransaction(accountTo, accountFrom, amount);
                    break;
                case 2:
                    System.out.println("You have chosen: .");
                    break;
                case 3:
                    System.out.println("You have chosen: .");
                    break;
                case 4:
                    System.out.println("You have chosen: .");
                    break;
                case 5:
                    System.out.println("You have chosen:  .");
                    break;
                case 6:
                    System.out.println("You have chosen: .");
                    break;
                case 7:
                    System.out.println("You have chosen: .");
                    break;
                default:
                    System.out.print("Invalid Option. Please try again: ");
            }
        }
    }
}