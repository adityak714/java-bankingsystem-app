package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.TransactionController;
import com.salmon.spicysalmon.models.Menu;


/// Maybe we should have deposit money, withdraw money, and transfer money from a bank account to another
public class CustomerMenu {
    String CUSTOMER_HEADING = "Customer Menu: Please choose a valid option.";
    String[] CUSTOMER_OPTIONS = {
            "Log out",

            "View all bank accounts",
            "Show My Applications",
            "Apply for new Bank Account",
            "Transactions for all Accounts",
            "Account settings"


            // Continue to add more options here and to the switch case as you see fit, it's ok to create submenus if anyone want to do that,
            // just make a switch case inside the switch case (or make a seperate method that is called inside the switch case)
    };


    String CUSTOMER_HEADING2 = "You chose to select a specific bank account: Please choose a valid option.";
    String [] CUSTOMER_OPTIONS2 =  {
            "Return to Customer Menu",
            "Deposit Money",
            "Withdraw Money",
            "Check Balance",
            "Money Transfer Between User Accounts",
            "Money Transfer To Another Person Bank Account",
            "Show Most Recent Transactions",
            "Show Oldest Transactions",
            "Show All Transactions",
            "Show Transactions By Ascending Order Of Price",
            "Show Transactions By Descending Order Of Price"




    };

    public void show(String SSN){
        CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        Menu customerMenu = new Menu(CUSTOMER_HEADING, CUSTOMER_OPTIONS);


        int userInput = 0;
        do{
            System.out.print(customerMenu);
            userInput = customerMenu.getValidOption();
            switch (userInput) {

                case 1:
                    showBankAccountMenu(customerController, transactionController, SSN);
                    break;
                case 2:

                    break;
                case 3:
                    //
                    ///Apply for bank accounts
                    break;
                case 4:
                    //Show Applications
                case 5:///Transactions for all accounts
                    System.out.print(transactionController.printTransactionsForAllAccounts(SSN));
                    break;
                case 6:///Account settings(Customer can change) Need to know what can change in an account
                    System.out.print("Account settings");
                    //Change password
                    //Change occupation
                    //Change salary
                    //Location
                    break;
                default:

            }
        }while(userInput != 0);
    }

    ///Bank account menu for a specific account
    public void showBankAccountMenu(CustomerController customerController, TransactionController transactionController, String SSN) {
        Menu bankAccountMenu = new Menu(CUSTOMER_HEADING2, CUSTOMER_OPTIONS2);
        int userInput = 0;
        System.out.print(customerController.printAllAccounts(SSN));

        String accountID = Util.readLine("To select a specific bank account, Enter account ID: ");
        do {
            System.out.println(bankAccountMenu);
            userInput = bankAccountMenu.getValidOption();
            switch (userInput) {
                case 1 -> depositMoney(customerController,SSN, accountID);
                case 2 -> withdrawMoney(customerController,SSN, accountID);
                case 3 -> showBalance(customerController, SSN, accountID);
                case 4 -> transferWithinAccounts(customerController, SSN, accountID);
                case 5 -> transferToOtherCustomer(customerController, SSN, accountID);
                case 6 -> showRecentTransactions(transactionController, SSN, accountID);
                case 7 -> showEarliestTransactions(transactionController, SSN, accountID);
                case 8 -> showTransactionsForAnAccount(transactionController, SSN, accountID);
                case 9 -> transactionsSortedInAscendingOrder(transactionController,SSN, accountID);
                case 10 -> transactionsSortedInDescendingOrder(transactionController, SSN, accountID);
            }

        } while (userInput != 0);
    }





    ////Having the functionality in methods is the best for design instead of being in the switch block
    public void printSpecificAccount(CustomerController customerController, String SSN) {

        String accountNumber = Util.readLine("Enter your account ID: ");

        customerController.printSpecificAccount(SSN, accountNumber);
        System.out.println("______________________");
    }
    public void transactionsSortedInAscendingOrder(TransactionController transactionController, String SSN, String accID) {


        System.out.print(transactionController.ascendingTransactionsByPriceForAccount(SSN, accID));
    }
    public void transactionsSortedInDescendingOrder(TransactionController transactionController, String SSN, String accID) {


        System.out.print(transactionController.descendingTransactionsByPriceForAccount(SSN, accID));
    }

    public void depositMoney(CustomerController customerController, String SSN, String accountID) {


        System.out.print("Enter the deposit amount: ");
        double depositAmount = UserIO.readDouble();
        customerController.depositMoney(SSN, accountID, depositAmount);
        System.out.print("You have deposited "+ depositAmount + " SEK into your account!!");

    }
    public void withdrawMoney(CustomerController customerController, String SSN, String accountID) {


        System.out.print("Enter the withdraw amount: ");
        double withdrawAmount = UserIO.readDouble();
        customerController.withdrawMoney(SSN, accountID, withdrawAmount);
        System.out.print("You have withdrawn "+ withdrawAmount + " SEK from your account!!");
    }
    public void showBalance(CustomerController customerController, String SSN, String accountID) {


        customerController.checkBalance(SSN, accountID);
    }
    public void transferWithinAccounts(CustomerController customerController, String SSN, String accountID) {


        String accountID2 = Util.readLine("Enter your second bank account ID: ");
        System.out.print("Enter the amount: ");
        double amount = UserIO.readDouble();
        customerController.transferMoneyWithinCustomerAccounts(SSN, amount, accountID, accountID2);
        System.out.println("You have transferred " + amount + " SEK successfully!!");

    }
    public void transferToOtherCustomer(CustomerController customerController, String SSN, String accountID1) {


        String accountNumber = Util.readLine("Enter the account number of the recipient: ");

        System.out.print("Enter the amount: ");
        double amount = UserIO.readDouble();
        customerController.transferMoneyToOtherCustomer(SSN, accountNumber, amount, accountID1);
        System.out.println("You have transferred " + amount + " SEK successfully!!");

    }
    public void showRecentTransactions(TransactionController transactionController, String SSN, String accID) {


        System.out.print(transactionController.printTransactionsSortedLatest(SSN, accID));
    }
    public void showEarliestTransactions(TransactionController transactionController, String SSN, String accID) {


        System.out.print(transactionController.printTransactionsSortedEarliest(SSN, accID));


    }
    public void showTransactionsForAnAccount(TransactionController transactionController, String SSN, String accID) {


        System.out.print(transactionController.printTransactionsForAnAccount(SSN, accID));
    }


}
