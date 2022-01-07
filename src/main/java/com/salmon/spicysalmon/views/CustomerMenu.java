package com.salmon.spicysalmon.views;


import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.AccountRequestController;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.TransactionController;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Menu;


public class CustomerMenu {

    // heading is dynamically generated
    String[] CUSTOMER_OPTIONS = {
            "Log out",
            "View all bank accounts",
            "Show My Bank Account Requests",
            "Apply for new Bank Account",
            "Transactions for all Accounts",
            "Account settings"
            // Continue to add more options here and to the switch case as you see fit, it's ok to create submenus if anyone want to do that,
            // just make a switch case inside the switch case (or make a seperate method that is called inside the switch case)
    };

    // heading is dynamically generated
    String [] CUSTOMER_OPTIONS2 =  {
            "Return to Customer Menu",
            "Check Balance",
            "Money Transfer Between Your Accounts",
            "Money Transfer To Another Person Bank Account",
            "Show Most Recent Transactions",
            "Show Oldest Transactions",
            "Show Transactions By Ascending Order Of Amount",
            "Show Transactions By Descending Order Of Amount",
            "Show Transactions Between Two Given Dates"
    };
    String ACCOUNT_SETTINGSHEADING = "Welcome to Account Settings: Please choose a valid option";
    String[] ACCOUNT_SETTINGSOPTIONS =  {
            "Return to Customer Menu",
            "Update Password",
            "Update Occupation Status",
            "Update Residential Area",
            "My Information"
    };
    //Show the customer menu
    public void show(String SSN){
        AccountRequestController accountRequestController = new AccountRequestController();
        CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        Customer currentCustomer = customerController.findCustomer(SSN);
        String customerHeading = "Logged in as: "+currentCustomer.getFirstName()+" "+currentCustomer.getLastName();
        customerHeading += ". Please select an option.";
        Menu customerMenu = new Menu(customerHeading, CUSTOMER_OPTIONS);

        int userInput = 0;
        do{
            System.out.print(customerMenu);
            userInput = customerMenu.getValidOption();
            switch (userInput) {

                case 1 -> showBankAccountMenu(customerController, transactionController, SSN);
                case 2 -> System.out.print(showAllApplications(accountRequestController, SSN));
                case 3 -> applyForBankAccount(customerController, SSN, accountRequestController);
                case 4 -> System.out.print(transactionController.printTransactionsForAllAccounts(SSN));
                case 5 -> showAccountSettings(SSN, customerController);
            }
        } while (userInput != 0);
        System.out.println("You have been logged out.");
    }

    // Bank account menu for a specific account
    public void showBankAccountMenu(CustomerController customerController, TransactionController transactionController, String SSN) {

        int totalNumberOfAccounts = customerController.getNumOfAccounts(SSN);
        if (totalNumberOfAccounts == 0) {
            System.out.println("You do not have a bank account. You can apply for one in the main menu.");
        } else {
            int inputFromUser = 0;// Initialize the variable called inputFromUser
            System.out.println(customerController.printAllAccounts(SSN));
            // make sure account id is valid
            do {
                inputFromUser = Util.readInt("Select the account you want to view (#): ");
                if (inputFromUser > totalNumberOfAccounts || inputFromUser <= 0) {
                    System.out.println("Please type in a valid option.");
                }
            } while (inputFromUser > totalNumberOfAccounts || inputFromUser <= 0 );
            String accountID = inputFromUser < 10 ? "0" + inputFromUser : inputFromUser+"";// Parse inputFromUser to String

            // Do-while for the bank account menu
            int userInput = 0;
            do {
                String accountHeading = "Current Account: "+customerController.printSpecificAccount(SSN, accountID);
                Menu bankAccountMenu = new Menu(accountHeading, CUSTOMER_OPTIONS2);
                System.out.print(bankAccountMenu);
                userInput = bankAccountMenu.getValidOption();
                switch (userInput) {
                    case 1 -> showBalance(customerController, SSN, accountID);
                    case 2 -> transferWithinAccounts(customerController, SSN, accountID);
                    case 3 -> transferToOtherCustomer(customerController, SSN, accountID);
                    case 4 -> showRecentTransactions(transactionController, SSN, accountID);
                    case 5 -> showEarliestTransactions(transactionController, SSN, accountID);
                    case 6 -> transactionsSortedInAscendingOrder(transactionController,SSN, accountID);
                    case 7 -> transactionsSortedInDescendingOrder(transactionController, SSN, accountID);
                    case 8 -> showTransactionsBetweenDates(transactionController, SSN, accountID);
                }
            } while (userInput != 0);
        }
    }
    //display account settings
    public void showAccountSettings(String SSN, CustomerController customerController) {
        Menu accountSettingsMenu = new Menu(ACCOUNT_SETTINGSHEADING, ACCOUNT_SETTINGSOPTIONS);
        int userOption = 0;
        do {
            System.out.print(accountSettingsMenu);
            userOption = accountSettingsMenu.getValidOption();
            switch (userOption) {
                case 1 -> changePassword(customerController, SSN);
                case 2 -> changeOccupation(customerController, SSN);
                case 3 -> changeResidentialArea(customerController, SSN);
                case 4 -> showUserInfo(customerController, SSN);
            }
        } while (userOption!= 0);
    }

    // Having the functionality in methods is the best for design instead of being in the switch block

    //This method forwards the user preferred account name to the createBankAccountRequest
    //In the account Request Controlller
    public void applyForBankAccount(CustomerController customerController, String SSN, AccountRequestController accountRequestController) {
        System.out.println("Kindly follow the instructions to create an account.");
        String accountName = Util.readLine("Enter the name of the new account: ");
        Customer customer = customerController.findCustomer(SSN);
        accountRequestController.createBankAccountRequest(customer, accountName);
        System.out.print("Your new bank account is pending approval.");
    }
    //Method to display all bank account applications made by the customer for a bank account
    public String showAllApplications(AccountRequestController accountRequestController, String SSN) {
        try {
            return (accountRequestController.printBankAccountRequestsForSpecificCustomer(SSN));
        } catch (Exception exception) {
            return (exception.getMessage());
        }
    }
    //Method to display sorted transactions by price in ascending order of amount transferred
    public void transactionsSortedInAscendingOrder(TransactionController transactionController, String SSN, String accID) {
        System.out.print(transactionController.sortTransactionsAscending(SSN, accID));
    }
    //Method to display sorted transactions by price in descending order of amount transferred
    public void transactionsSortedInDescendingOrder(TransactionController transactionController, String SSN, String accID) {
        System.out.print(transactionController.sortTransactionsDescending(SSN, accID));
    }
   //Method to display balance
    public void showBalance(CustomerController customerController, String SSN, String accountID) {
        System.out.println(customerController.checkBalance(SSN, accountID));
    }

    public void transferWithinAccounts(CustomerController customerController, String SSN, String accountID) {
        int numberOfAccounts = customerController.getNumOfAccounts(SSN);
        int  random = 0;
        String accountID2;
        do  {
            System.out.println(customerController.printAllAccounts(SSN));
            random = Util.readInt("Enter your desired bank account ID: ");
            if (random > numberOfAccounts || random <= 0) {
                System.out.println("Invalid accountID. You need to type a valid account ID.");
            }
            accountID2 = random < 10 ? "0" + random : random+"";
            // make sure the current account cannot be typed in again.
            if(accountID2.equals(accountID)){
                System.out.println("You cannot transfer money within the same account.");
            }
        } while (random > numberOfAccounts || random <= 0 || accountID2.equals(accountID));

        double amount = Util.readDouble("Enter the amount: ");

        try{
            System.out.println(customerController.transferMoneyWithinCustomerAccounts(SSN, amount, accountID, accountID2));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    //This method forwards information form the menu to the customer controller to transfer money to another customer
    public void transferToOtherCustomer(CustomerController customerController, String SSN, String accountID1)  {
        String accountNumber = "";
        do  {
            accountNumber = Util.readLine("Enter the account number of the recipient: ");
            if (!Util.isValidAccountNumberFormat(accountNumber)) {
                System.out.println("The account number you entered has an incorrect format. Try again.");
            }
        } while (!Util.isValidAccountNumberFormat(accountNumber));

        double amount = Util.readDouble("Enter the amount: ");
        try{
            System.out.println(customerController.transferMoneyToOtherCustomer(SSN, accountNumber, amount, accountID1));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //Method to display most recent transactions
    public void showRecentTransactions(TransactionController transactionController, String SSN, String accID) {
        System.out.print(transactionController.printTransactionsSortedLatest(SSN, accID));
    }
    //Displays earliest transactions
    public void showEarliestTransactions(TransactionController transactionController, String SSN, String accID) {
        System.out.print(transactionController.printTransactionsSortedEarliest(SSN, accID));
    }

    //Method to print transactions within a date interval
    public void showTransactionsBetweenDates(TransactionController transactionController, String SSN, String accID) {
        try {
            String startDate = Util.readLine("Enter the start date (YYYY/MM/DD): ");
            String endDate = Util.readLine("Enter the end date (YYYY/MM/DD): ");
            System.out.print(transactionController.sortByDateInterval(SSN, accID, startDate, endDate));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Change customer's password
    public void changePassword(CustomerController customerController, String SSN) {
        String testPassword = Util.readPassword("Enter your old password: ");
        String newPassword = Util.readNewPassword();
        System.out.print(customerController.changePassword(testPassword, newPassword, SSN));
    }

    // Forwards our new occupation to the method in customer controller to be updated
    public void changeOccupation(CustomerController customerController, String SSN) {
        String occupation = Util.readLine("Enter your new occupation: ");
        System.out.println(customerController.changeOccupation(occupation, SSN));
    }

    // Change residential status
    public void changeResidentialArea(CustomerController customerController, String SSN) {
        String residentialArea = Util.readLine("Enter your new residential area: ");
        System.out.println(customerController.changeResidentialArea(residentialArea, SSN));
    }

    // Displays all our information
    public void showUserInfo(CustomerController customerController, String SSN) {
        System.out.print(customerController.printCustomer(SSN));
    }
}
