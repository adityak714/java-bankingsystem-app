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
            "Select a  Bank Account",
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
            "Money Transfer between user accounts",
            "Money Transfer to another person bank account",
            "Show Recent transactions",
            "Show oldest Transactions",
            "Show All Transactions",




    };

    public void show(String SSN){
        CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        Menu customerMenu = new Menu(CUSTOMER_HEADING, CUSTOMER_OPTIONS);
        Menu bankAccountMenu = new Menu(CUSTOMER_HEADING2, CUSTOMER_OPTIONS2);

        int userInput = 0;
        do{
            System.out.print(customerMenu);
            userInput = customerMenu.getValidOption();
            switch (userInput) {
                //// Do a case for logout
                /*case 1:
                    System.out.print("Goodbye");
                    break;

                 */
                case 1: ///Add functionality that displays the bank account to the user
                    ///Print all bank accounts
                    String accountNumber = Util.readLine("Enter your account number: ");

                    customerController.printSpecificAccount(SSN, accountNumber);
                    System.out.println("______________________");
                    do {
                        System.out.println(bankAccountMenu);
                        userInput = customerMenu.getValidOption();
                        switch (userInput) {
                            case 1:
                                System.out.print(customerMenu);
                                break;
                            case 2:
                                System.out.print("Enter the deposit amount: ");
                                double depositAmount = UserIO.readDouble();
                                customerController.depositMoney(SSN, accountNumber, depositAmount);
                                break;
                            case 3:
                                System.out.print("Enter the withdraw amount: ");
                                double withdrawAmount = UserIO.readDouble();
                                customerController.withdrawMoney(SSN, accountNumber, withdrawAmount);
                                break;
                            case 4:
                                customerController.printBalance(SSN);
                                break;
                            case 5:

                                String accountNumber2 = Util.readLine("Enter the account number: ");
                                System.out.print("Enter the amount: ");
                                double amount = UserIO.readDouble();
                                customerController.transferMoneyWithinCustomerAccounts(SSN, amount, accountNumber, accountNumber2);
                                break;

                            case 6:
                                String SSNReceiver = Util.readLine("Enter the SSN of the second receiver: ");
                                accountNumber2 = Util.readLine("Enter the account number: ");
                                System.out.print("Enter the amount: ");
                                amount = UserIO.readDouble();
                                customerController.transferMoneyToOtherCustomer(SSN, SSNReceiver, amount, accountNumber, accountNumber2);
                                break;
                            case 7:
                                String accID = Util.readLine("Enter the account ID: ");
                                System.out.print(transactionController.printTransactionsSortedLatest(SSN, accID));
                                break;
                            case 8:
                                accID = Util.readLine("Enter the account ID: ");
                                System.out.print(transactionController.printTransactionsSortedEarliest(SSN, accID));
                                break;
                            case 9:
                                accID = Util.readLine("Enter the account ID: ");
                                System.out.print(transactionController.printTransactionsForAnAccount(SSN, accID));
                                break;
                            case 10:
                                accID = Util.readLine("Enter the account ID: ");
                                System.out.print(transactionController.ascendingTransactionsByPriceForAccount(SSN, accID));
                                break;
                            case 11:
                                accID = Util.readLine("Enter the account ID: ");
                                System.out.print(transactionController.descendingTransactionsByPriceForAccount(SSN, accID));
                                break;
                        }

                    } while (userInput != 0);




                    break;
                case 2:
                    customerController.printAllAccounts(SSN);
                    break;
                case 3:
                    //
                    ///Apply for bank accounts
                    break;
                case 4:
                    //Show Applications
                case 5:///Transactions for all accounts
                    transactionController.printTransactionsForAllAccounts(SSN);
                    break;
                case 6:///Account settings(Customer can change) Need to know what can change in an account






            }
        }while(userInput != 0);
    }

}
