package com.salmon.spicysalmon.views;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.*;
import com.salmon.spicysalmon.models.*;
import java.util.ArrayList;

public class EmployeeMenu {
    String EMPLOYEE_HEADING = "Employee Menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS = {
            "Return to Main Menu",
            "Menu for Customer handling",
            "Menu for Application handling",
            "Menu for Transaction handling",
            "Menu for Settings"
    };

    String MANAGER_HEADING = "Manager Menu: Please choose a valid option.";
    String[] MANAGER_OPTIONS = {
            "Return to Main Menu",
            "Menu for Customer handling",
            "Menu for Application handling",
            "Menu for Transaction handling",
            "Menu for Settings",
            "Menu for Employee handling"
    };

    String MANAGER_HEADING2 = "Employee handling menu: Please choose a valid option.";
    String[] MANAGER_OPTIONS2 = {
            "Return to Main Menu",
            "List all registered employees",
            "Remove an employee",
            "Create an employee",
            "Create a new manager"
    };

    String EMPLOYEE_HEADING2 = "Customer handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS2 = {
            "Return to Main Menu",
            "Create customer",
            "Delete customer",
            "Create bank account for customer",
            "Delete customer bank account",
            "List all registered customers.",
            "List all registered bank accounts",
            "Deposit into customer account",
            "Withdraw from customer account"
    };
    String EMPLOYEE_HEADING3 = "Account request handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS3 = {
            "Return to Main Menu",
            "Review specific customer account request",
            "Review specific bank account request",
            "List all customer account request",
            "List all bank account request"
    };
    String EMPLOYEE_HEADING4 = "Transaction handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS4 = {
            "Return to Main Menu",
            "Show all transactions",
            "Show all transactions for a customer",
            "Show all transactions for an account"
    };
    String EMPLOYEE_HEADING5 = "Settings: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS5 = {
            "Return to Main Menu",
            "Update Password",
            "My Information"
    };

    // the first menu the employee will see, this then branches of into a Customer and a Account request Menu
    public void show(String SSN){

        // use these objects to access the methods in the controllers
        // pass in controller into methods
        // remove userinput as arguments


        AccountRequestController accountRequestController = new AccountRequestController();
        CustomerController customerController = new CustomerController();
        AuthenticationController authenticationController = new AuthenticationController();
        TransactionController transactionController = new TransactionController();
        EmployeeController employeeController = new EmployeeController();

        // show relevant menu to employee based on whether they are regular or a manager
        Employee currentEmployee = employeeController.getEmployee(SSN);
        Menu menu;
        if(currentEmployee.getClass() == Manager.class){
            menu = new Menu(MANAGER_HEADING, MANAGER_OPTIONS);
        } else{
            menu = new Menu(EMPLOYEE_HEADING, EMPLOYEE_OPTIONS);
        }

        int userInput = 0;
        do {
            System.out.print(menu);
            userInput = menu.getValidOption();
            switch (userInput) {
                case 1 -> showCustomerMenu(customerController, authenticationController);
                case 2 -> showAccountRequestMenu(accountRequestController, customerController);
                case 3 -> showTransactionMenu(transactionController);
                case 4 -> showSettingsMenu(employeeController, SSN);
                case 5 -> {
                    if(currentEmployee.getClass() == Manager.class){
                        showEmployeeHandlingMenu(SSN, employeeController);
                    } else{
                        System.out.println("Invalid menu option. Please type another option.");
                    }
                }
            }
        }while (userInput != 0);
    }
    // Customer menu that handles all the functionality were the Employee directly interacts with customers
    public void showCustomerMenu(CustomerController customerController, AuthenticationController authenticationController){
        Menu employeeCustomerMenu = new Menu(EMPLOYEE_HEADING2, EMPLOYEE_OPTIONS2);
        int userInput = 0;
        do {
            System.out.print(employeeCustomerMenu);
            userInput = employeeCustomerMenu.getValidOption();
            switch (userInput){
                case 1: // create customer
                    createCustomer(customerController);
                    break;
                case 2: // remove customer
                    removeCustomer(customerController);
                    break;
                case 3:
                    createBankAccount(customerController);
                    break;
                case 4: // delete a bank account
                    deleteBankAccount(customerController);
                    break;
                case 5: // print all customers
                    printAllCustomers(customerController);
                    break;
                case 6: // print all bank accounts
                    printAllBankAccounts(customerController);
                    break;
                case 7: // deposit money into customer Account
                    depositIntoCustomer(customerController);
                    break;
                case 8:
                    withdrawFromCustomer(customerController);
                    break;
            }
        }while (userInput != 0);
    }

    // Account request menu that handles all the functionality were the Employee directly interacts with Account Requests

    public void showAccountRequestMenu(AccountRequestController accountRequestController, CustomerController customerController){

        Menu employeeAccountRequestMenu = new Menu(EMPLOYEE_HEADING3,EMPLOYEE_OPTIONS3);
        int userInput = 0;
        do {
            System.out.print(employeeAccountRequestMenu);
            userInput = employeeAccountRequestMenu.getValidOption();
            switch (userInput) {
                case 1: // approve/deny customer application
                    try {
                        specificCustomerAccountRequest(accountRequestController);
                    } catch (Exception IOException) {
                        System.out.println(IOException.getMessage());
                    }
                    break;
                case 2: // approve/deny bank application
                    try {
                        specificBankBankAccountRequest(accountRequestController);
                    } catch (Exception IOException) {
                        System.out.println(IOException.getMessage());
                    }
                    break;
                case 3: // look att all the customer account requests, then pick one you want to approve/deny
                    try {
                        listAllCustomerAccountRequests(accountRequestController);
                    } catch (Exception IOException) {
                        System.out.println(IOException.getMessage());
                    }
                    break;
                case 4: // look att all the bank account requests, then pick one you want to approve/deny
                    try {
                        listAllBankAccountRequests(accountRequestController);
                    } catch (Exception IOException) {
                        System.out.println(IOException.getMessage());
                    }
                    break;
            }
        }while (userInput != 0);

    }
    // menu for transactions
    public void showTransactionMenu(TransactionController transactionController){
        Menu employeeTransactionMenu = new Menu(EMPLOYEE_HEADING4, EMPLOYEE_OPTIONS4);
        int userInput = 0;
        do {
            System.out.print(employeeTransactionMenu);
            userInput = employeeTransactionMenu.getValidOption();
            switch (userInput){
                case 1: // print all transactions
                    printAllTransactions(transactionController);
                    break;
                case 2: // print all transactions for a customer
                    printSpecificCustomerTransactions(transactionController);
                    break;
                case 3: // print all transactions for an account
                    printSpecificBankAccountTransactions(transactionController);
                    break;
            }
        }while (userInput != 0);

    }
    // method to display menu for settings
    public void showSettingsMenu(EmployeeController employeeController, String SSN) {
        Menu employeeSettingsMenu = new Menu(EMPLOYEE_HEADING5, EMPLOYEE_OPTIONS5);
        int userInput = 0;
        do {
            System.out.print(employeeSettingsMenu);
            userInput = employeeSettingsMenu.getValidOption();
            switch (userInput) {
                case 1: // change password
                    try {
                        changePassword(employeeController, SSN);
                        System.out.println("password changed sucessfully");
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case 2: // my information
                    showUserInfo(employeeController, SSN);
                    break;

            }
            break;
        } while (userInput != 0);
    }
    // menu for employee handling
    private void showEmployeeHandlingMenu(String SSN, EmployeeController employeeController){
        Menu employeeHandlingMenu = new Menu(MANAGER_HEADING2, MANAGER_OPTIONS2);
        int userInput = 0;
        do {
            System.out.print(employeeHandlingMenu);
            userInput = employeeHandlingMenu.getValidOption();
            switch (userInput){
                case 1: // list all registered employees
                    System.out.println(employeeController.printAllEmployees());
                    break;
                case 2: // remove an employee
                    try{
                        removeEmployee(employeeController);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3: { // create a new employee
                    createEmployee(employeeController);
                    break;
                }
                case 4: { // create a new manager
                    createManager(employeeController);
                    break;
                }
                default:
                    break;
            }
        }while (userInput != 0);
    }

    // creates a new customer
    public void createCustomer(CustomerController customerController){
        System.out.println("You have chosen: Create a customer.");
        String firstName = Util.readLine("Enter your first name (type EXIT to exit this menu): ");
        if (firstName.equals("EXIT")) return;
        String lastName = Util.readLine("Enter your last name: ");
        String password = Util.readNewPassword();
        String SSN;
        // make sure correct SSN format is inputted.
        do{
            SSN = Util.readLine("Enter your social security number: ");
            if(!Util.isValidSSNFormat(SSN)){
                System.out.println("Please use format YYMMDDXXXX");
            }
        }while(!Util.isValidSSNFormat(SSN));
        double salary = Util.readDouble("Enter your salary: ");
        String residentialArea = Util.readLine("Enter your area of residence: ");
        String occupation = Util.readLine("Enter your occupation: ");
        try{
            customerController.createCustomer(SSN ,password, firstName,lastName, salary, residentialArea, occupation);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // removes a customer with the specified SSN
    public void removeCustomer(CustomerController customerController){
        System.out.println("You have chosen: Remove a customer.");
        String remove = Util.readLine("What customer do you wish to remove? Enter SSN (type EXIT to exit this menu): ");
        if (remove.equals("EXIT")) return;
        try{
            customerController.removeCustomer(remove);
            System.out.println("The customer was removed successfully.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // creates a bank account for a customer
    public void createBankAccount(CustomerController customerController){
        System.out.println("You have chosen: Create a bank account for a customer.");
        String SSN = Util.readLine("Please enter the Social Security Number of the customer (type EXIT to exit this menu): ");
        if (SSN.equals("EXIT")) return;
        String accountName = Util.readLine("Please enter a name for the new account: ");
        try{
            customerController.createBankAccount(SSN, accountName);
            System.out.println("Account "+accountName+" was created successfully.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // deletes a bank account with the specified accountNumber
    public void deleteBankAccount(CustomerController customerController){
        String accountNumber = Util.readLine("Type in the account number of the bank account do you want to remove (type EXIT to exit this menu): ");
        if (accountNumber.equals("EXIT")) return;
        try{
            customerController.deleteBankAccount(accountNumber);
            System.out.println("The bank account was removed successfully.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean customerAuthentication(String SSN, String testPassword, CustomerController customerController){
        Customer customer = customerController.findCustomer(SSN);
        return customer.verifyPassword(testPassword);
    }

    public void depositIntoCustomer(CustomerController customerController){
        TransactionController transactionController = new TransactionController();
        System.out.println("You have chosen: Deposit into customer account.");
        System.out.println("Please note that the customer must be present at the time of deposit.");
        String accountNumber;
        do{
            accountNumber = Util.readLine("Enter the account number for the account where you want to deposit (type EXIT to exit this menu): ");
            if (accountNumber.equals("EXIT")) return;
            if(!Util.isValidAccountNumberFormat(accountNumber)) {
                System.out.println("Please enter a valid account number.");
            }
        } while(!Util.isValidAccountNumberFormat(accountNumber));
        double amount = Util.readDouble("Enter the deposit amount: ");
        String customerPassword = Util.readPassword("Ask customer to type in their password in order to verify deposit: ");
        String SSN = accountNumber.substring(0,10);
        String accID = accountNumber.substring(10,12);
        if(customerAuthentication(SSN, customerPassword, customerController)){
            String result = customerController.depositMoney(SSN, accID, amount);
            if(result.contains("successfully")){
                transactionController.createEmployeeTransaction(accountNumber, amount);
            }
            System.out.println(result);
        } else{
            System.out.println("Incorrect password. Please try again.");
        }
    }

    public void withdrawFromCustomer(CustomerController customerController){
        TransactionController transactionController = new TransactionController();
        System.out.println("You have chosen: Withdraw from customer account.");
        System.out.println("Please note that the customer must be present at the time of deposit.");
        System.out.println();
        String accountNumber = Util.readLine("Enter the account number for the account where you want to withdraw (type EXIT to exit this menu): ");
        if (accountNumber.equals("EXIT")) return;
        double amount = Util.readDouble("Enter the withdraw amount: ");
        String customerPassword = Util.readPassword("Ask customer to type in their password in order to verify withdrawal: ");
        String SSN = accountNumber.substring(0,10);
        String accID = accountNumber.substring(10,12);
        if(customerAuthentication(SSN, customerPassword, customerController)){
            String result = customerController.withdrawMoney(SSN, accID, amount);
            if(result.contains("successfully")){
                transactionController.createEmployeeTransaction(accountNumber, (0-amount));
            }
            System.out.println(result);
        } else{
            System.out.println("Incorrect password. Please try again.");
        }
    }
    // prints all customers
    public void printAllCustomers(CustomerController customerController){
        System.out.println("You have chosen: Print all registered customers.");
        System.out.println(customerController.printAllCustomers());
    }
    //method to print all registered bank accounts
    public void printAllBankAccounts(CustomerController customerController){
        System.out.println(customerController.printAllBankAccountsEmployee());
    }
    //Method to print all transactions for all registered customers
    public void printAllTransactions(TransactionController transactionController){
        System.out.println(transactionController.printAllTransactions());
    }
    //method to print a specific customer's transactions
    public void printSpecificCustomerTransactions(TransactionController transactionController){
        String SSN = "";
        do {
            SSN = Util.readLine("Type in the SSN of the customer you want to look at (type EXIT to exit this menu): ");
            if (SSN.equals("EXIT")) return;
            if (!Util.isValidSSNFormat(SSN)){
                System.out.println("Please enter a valid account number.");
            }
        }while (!Util.isValidSSNFormat(SSN));
        System.out.println(transactionController.printTransactionsForAllAccounts(SSN));

    }
    public void printSpecificBankAccountTransactions(TransactionController transactionController){
        String accountNumber = "";
        do {
            accountNumber = Util.readLine("Type in the account number of the bank account you want to look at (type EXIT to exit this menu): ");
            if (accountNumber.equals("EXIT")) return;
            if (!Util.isValidAccountNumberFormat(accountNumber)){
                System.out.println("Please enter a valid account number.");
            }
        }while (!Util.isValidAccountNumberFormat(accountNumber));
        String SSN = accountNumber.substring(0,10);
        String accountID = accountNumber.substring(10,12);
        System.out.println(transactionController.printTransactionsForAnAccount(SSN, accountID));
    }
    // goes to a specific customers customer account requests
    public void specificCustomerAccountRequest(AccountRequestController accountRequestController) throws Exception {
        String SSN = Util.readLine("Which customer's request do you want to look at? Type the SSN: ");
        CustomerAccountRequest CAR = accountRequestController.getCustomerAccountRequestBySSN(SSN); // CAR = Customer Account Request
        System.out.println(CAR.toString());
        approveDenyCustomerAccountRequest(CAR, accountRequestController);
    }
    // goes to a specific customers bank account requests
    public void specificBankBankAccountRequest(AccountRequestController accountRequestController) throws Exception {
        String SSN = Util.readLine("Which customers request do you want to look at? Type the SSN: ");
        ArrayList<BankAccountRequest> BARs = accountRequestController.getBankAccountRequestsForSpecificCustomer(SSN); // BARs = Bank Account Requests
        for (BankAccountRequest BAR : BARs){
            System.out.println(BAR.toString());
        }
        if (BARs.size() == 1){
            approveDenyBankAccountRequest(BARs.get(0), accountRequestController);
        }else {
            int userInput = (Util.readInt("Which request do you want to look at: ")) - 1;
            BankAccountRequest BAR = BARs.get(userInput);
            System.out.println(BAR);
            approveDenyBankAccountRequest(BAR, accountRequestController);
        }
    }

    // lists all customer account requests, then allows the employee to approve/deny that request
    public void listAllCustomerAccountRequests(AccountRequestController accountRequestController) throws Exception {
        System.out.println(accountRequestController.printAllCustomerAccountRequests());
        int requestNum = (Util.readInt("Which request do you want to look at (type 0 to exit): "));
        if (requestNum != 0){
            CustomerAccountRequest request = accountRequestController.getSpecificCustomerAccountRequestFromList(requestNum-1);
            System.out.println(request.toString());
            approveDenyCustomerAccountRequest(request, accountRequestController);
        }
    }
    // lists all bank account requests, then allows the employee to approve/deny that request
    public void listAllBankAccountRequests(AccountRequestController accountRequestController) throws Exception {
        System.out.println(accountRequestController.printAllBankAccountRequests());
        int requestNum = (Util.readInt("Which request do you want to check out (type 0 to exit): "));
        if (requestNum != 0){
            BankAccountRequest request = accountRequestController.getSpecificBankAccountRequestFromList(requestNum-1);
            System.out.println(request.toString());
            approveDenyBankAccountRequest(request, accountRequestController);
        }
    }
    // approves or denies a customer account request, denial needs a accompanied message as to why it was denied
    public void approveDenyCustomerAccountRequest(CustomerAccountRequest request, AccountRequestController accountRequestController) throws Exception {
        AccountRequestController controller = new AccountRequestController();
        String stringUserInput = "";
        do {
            stringUserInput = Util.readLine("Please type in \"Approve\" or \"Deny\" (0 to go back): ");
            if (stringUserInput.equalsIgnoreCase("approve")) {
                //Util.readLine("Please type in the reason for the approval:"); do we need message for approval?
                String message = Util.readLine("Please type in the reason for the approval (can be left blank): ");
                accountRequestController.approveCustomerAccountRequest(request, message);
            } else if (stringUserInput.equalsIgnoreCase("deny")) {
                String message = Util.readLine("Please type in the reason for the denial: ");
                accountRequestController.denyAccountRequest(request, message);
            } else if (stringUserInput.equals("0")) {
                listAllCustomerAccountRequests(controller);
                break;
            } else {
                System.out.println("Please input a valid option (\"Approve\" or \"Deny\") (0 to go back)");
            }
        }while (!(stringUserInput.equalsIgnoreCase("approve") || stringUserInput.equalsIgnoreCase("deny")));
    }
    // approves or denies a bank account request, denial needs a accompanied message as to why it was denied
    public void approveDenyBankAccountRequest(BankAccountRequest request, AccountRequestController accountRequestController) throws Exception{
        AccountRequestController controller = new AccountRequestController();
        String stringUserInput = "";
        do {
            stringUserInput = Util.readLine("Please type in \"Approve\" or \"Deny\" (0 to go back): ");
            if (stringUserInput.equalsIgnoreCase("approve")) {
                //Util.readLine("Please type in the reason for the approval:"); do we need message for approval?
                String message = Util.readLine("Please type in the reason for the approval (can be left blank): ");
                accountRequestController.approveBankAccountRequest(request, message);
            } else if (stringUserInput.equalsIgnoreCase("deny")) {
                String message = Util.readLine("Please type in the reason for the denial: ");
                accountRequestController.denyAccountRequest(request, message);
            }else if (stringUserInput.equals("0")){
                listAllBankAccountRequests(controller);
                break;
            }else {
                System.out.println("Please input a valid option (\"Approve\" or \"Deny\") (0 to go back)");
            }
        }while (!(stringUserInput.equalsIgnoreCase("approve") || stringUserInput.equalsIgnoreCase("deny")));
    }
    //Method to change employee's password
    public void changePassword(EmployeeController employeeController, String SSN) throws Exception {
        String testPassword = Util.readPassword("Enter your old password: ");
        String newPassword = Util.readNewPassword();
        employeeController.changePassword(testPassword, newPassword, SSN);
    }
    // Method to print the information of an employee
    public void showUserInfo(EmployeeController employeeController, String SSN) {
        System.out.print(employeeController.printEmployee(SSN));
    }
    public void removeEmployee(EmployeeController employeeController) throws Exception {
        String SSN = "";
        do {
            SSN = Util.readLine("Enter the SSN of the Employee you want to remove (type EXIT to exit this menu): ");
            if (SSN.equals("EXIT")) return;
            if (!Util.isValidSSNFormat(SSN)){
                System.out.println("Please use format YYMMDDXXXX");
            }
        }while (!Util.isValidSSNFormat(SSN));
        employeeController.removeEmployee(SSN);
        System.out.println("Employee "+SSN+" was removed successfully.");
    }
    public void createEmployee(EmployeeController employeeController){
        String firstName = Util.readLine("Enter employee's first name (type EXIT to exit this menu): ");
        if (firstName.equals("EXIT")) return;
        String lastName = Util.readLine("Enter employee's last name: ");
        String newSSN = Util.readLine("Enter employee's social security number: ");
        String password = Util.readNewPassword();
        String startDate = Util.getDateAndTime();
        employeeController.createEmployee(newSSN, password, firstName, lastName, startDate);
        System.out.println("Employee "+ newSSN +" was created successfully.");

    }
    public void createManager(EmployeeController employeeController){
        String firstName = Util.readLine("Enter employee's first name (type EXIT to exit this menu): ");
        if (firstName.equals("EXIT")) return;
        String lastName = Util.readLine("Enter manager's last name: ");
        String newSSN = Util.readLine("Enter manager's social security number: ");
        String password = Util.readNewPassword();
        String startDate = Util.getDateAndTime();
        employeeController.createManager(newSSN, password, firstName, lastName, startDate);
        System.out.println("Manager "+ newSSN +" was created successfully.");
    }
}

