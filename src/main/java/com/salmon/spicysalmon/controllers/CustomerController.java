package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;

import java.util.HashMap;

public class CustomerController {
    private static final HashMap<String, Customer> customersList = new HashMap<>(); // "customerList" is a better name?

    public String createCustomer(String socialSecurityNumber, String password, String firstName, String lastName, double salary, String residentialArea, String occupation){
        TransactionController transactionsController = new TransactionController();
        if(transactionsController.checkIfSSNUnique(socialSecurityNumber)){
            Customer newCustomer = new Customer(socialSecurityNumber, password, firstName, lastName, salary, residentialArea, occupation);
            customersList.put(socialSecurityNumber, newCustomer);
            return "Customer "+firstName+" "+lastName+" created successfully.";
        } else {
            return "A customer with that social security number already exists!";
        }
    }

    public Customer findCustomer(String SSN){
        return customersList.get(SSN);
    }


    public String removeCustomer(String SSN) {
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            return ("Customer " + SSN + " could not be removed.");
        }
        customersList.remove(customer);
        return ("Customer " + SSN + " was successfully removed.");
    }

    public String printAllCustomers() {
        String EOL = System.lineSeparator();
        if (customersList.isEmpty()) {
            return "No customers registered yet.";
        }
        String message = "All registered customers:" + EOL + "-------------------------" + EOL;
        for (String SSN : customersList.keySet()) {
            Customer customer = customersList.get(SSN);
            message += printCustomer(customer.getSocialSecurityNumber()) + EOL;
        }
        return message;
    }

    public String printCustomer(String SSN) {
        String EOL = System.lineSeparator();
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            return "Customer " + SSN + " was not registered yet.";
        } else {
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            return ("Name: " + firstName + " " + lastName + EOL + "Occupation: " + customer.getOccupation() + EOL + "Residential Area: " + customer.getResidentialArea() + EOL);
        }
    }

    public String printSpecificCustomer(String SSN) {
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            return ("Customer " + SSN + " could not be found.");
        }
        return printCustomer(customer.getSocialSecurityNumber());
    }

    /*
    // method should use constructor BankAccount
    public String createBankAccount(String SSN, String accountName){
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            return "Customer does not exist.";
        } else {
            System.out.println("kladdkaka 32");
            return customer.createBankAccount(accountName);
        }
    }
     */

    public String createBankAccount(String SSN, String accountName){
        Customer customer = findCustomer(SSN);
        if(customer == null){
            return "Customer does not exist.";
        }else{
            return customer.createBankAccount(accountName);
        }
    }


    // method needs to call createTransaction from TransactionController
    public String depositMoney(String SSN, String accID, double depositAmount)  {

        BankAccount account = findBankAccount(SSN, accID);
        if (account == null) {
            return "Account does not exist";
        } else {
            String message = "";
            if (depositAmount < 0) {

                message = "Amount to be deposited is too small";
            }else {
                account.setBalance(depositAmount + account.getBalance());
                message = "You have deposited " + depositAmount + " SEK successfully!!";

            }
            return message;



        }
    }

    // method needs to call createTransaction from TransactionController
    public String  withdrawMoney(String SSN, String accountID, double amount)  {

        BankAccount account = findBankAccount(SSN, accountID);
        if (account == null) {
            return "Account does not exist";
        }
        else {
            String message = "";
            if (amount < 0 || amount > account.getBalance())  {
                message = "Withdrawal unsuccessful!!";
            } else {
                account.setBalance(account.getBalance() - amount);
                message = "You have withdrawn "+ amount + " SEK successfully!!";

            }
            return message;
        }



    }

    public BankAccount findBankAccount(String SSN, String accountID) {

        Customer customer = findCustomer(SSN);
        String accountNumber = SSN + accountID;
        for (BankAccount account : customer.getCustomerAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public String deleteBankAccount(String accountNumber) {
        String SSN = accountNumber.substring(0, 9);
        String accID = accountNumber.substring(10, 11);
        Customer customer = findCustomer(SSN);
        if (customer != null) {

            return customer.deleteBankAccount(accID);
        } else {
            return "Account was not found.";
        }
    }
    ///Made new method to check balance of a specific account
    public String checkBalance(String SSN, String accountID) {

        BankAccount bankAccount = findBankAccount(SSN, accountID);
        if(bankAccount == null) {
            return "Your bank account does not exist.";
        }
        else {
            String message = "";
            double balance = bankAccount.getBalance();
            message = "Balance: " + balance + " SEK.";
            return message;

        }


    }
    //This method will only print the total balance of all existing bank accounts."Should be refactored"
    /*public void printBalance(String SSN) {
        System.out.println("kladdkaka 34");
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            System.out.println("Customer does not exist");
        } else {
            System.out.println(customer.getTotalBalance());
        }
    }

     */

    public void transferMoneyWithinCustomerAccounts(String SSNSender, double amount, String accountID1, String accountID2)  {
        TransactionController transactionController = new TransactionController();
        BankAccount account = findBankAccount(SSNSender, accountID1);
        if (account == null) {
            System.out.print("The other bank account does not exist!!");
        } else {
            depositMoney(SSNSender, accountID2, amount);
            withdrawMoney(SSNSender,accountID1,  amount);
            //Create a transaction here right
            transactionController.createTransaction(SSNSender+accountID1, SSNSender+accountID2, amount);
        }
    }
    public void transferMoneyToOtherCustomer(String SSNSender, String accountNumber,  double amount, String accountID1) {
        TransactionController transactionController = new TransactionController();

        String SSNReceiver = accountNumber.substring(0, 9);
        String accID2 = accountNumber.substring(10, 11);
        BankAccount accountReceiver = findBankAccount(SSNReceiver, accID2);


        if(accountReceiver == null) {
            System.out.print("The other bank account does not exist!!");
        } else {

            depositMoney(SSNReceiver, accID2, amount);
            withdrawMoney(SSNSender, accountID1, amount);
            //Make a transaction here too
            transactionController.createTransaction(SSNSender+accountID1, accountNumber, amount);

        }



    }
    public String printAllAccounts(String SSN) {

        //Assuming the customer is logged in already
        Customer customer = findCustomer(SSN);
        if (customer.getCustomerAccounts().size() == 0) {
            return "No bank accounts exist for you";

        } else {
            String message = "";
            for (BankAccount account : customer.getCustomerAccounts()) {
                message += account + Util.EOL;

            }
            return message;
        }
    }
    //Added this so we can show this to the customer
    public void printSpecificAccount(String SSN, String accountID)  {
        BankAccount account = findBankAccount(SSN, accountID);
        if (account == null) {
            System.out.println("Account does not exist");
        }
        else {
            System.out.println(account);
        }

    }
    public void changePassword(String testPassword, String newPassword, String SSN) {
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            System.out.print("Customer does not exist.");
        } else {
            try {
                customer.changePassword(testPassword, newPassword);
                System.out.print("Password changed successfully!!");
            } catch(Exception ex) {
                System.out.print(ex.getMessage());
            }
        }
    }
    public void changeOccupation(String occupation, String SSN) {
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            System.out.print("Customer does not exist.");
        } else {
            customer.setOccupation(occupation);
            System.out.print("Occupation changed successfully");
        }
    }
    public void changeResidentialArea(String residentialArea, String SSN) {
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            System.out.print("Customer does not exist.");
        } else {
            customer.setResidentialArea(residentialArea);
            System.out.print("Residential Area changed successfully!!");
        }
    }



}


