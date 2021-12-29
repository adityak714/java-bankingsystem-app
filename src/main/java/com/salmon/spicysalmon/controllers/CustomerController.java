package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;

import java.util.HashMap;

public class CustomerController {
    private static final HashMap<String, Customer> customersList = new HashMap<>(); // "customerList" is a better name?

    public void createCustomer(String SSN, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) throws Exception {
        TransactionController transactionsController = new TransactionController();
        if(Util.checkSSNFormat(SSN)){
            if(findCustomer(SSN) == null && transactionsController.checkIfSSNUnique(SSN)) {
                Customer newCustomer = new Customer(SSN, password, firstName, lastName, salary, residentialArea, occupation);
                customersList.put(SSN, newCustomer);
            } else{
                throw new Exception("A customer with that SSN already exists.");
            }
        } else{
            throw new Exception("SSN formatting incorrect, use format YYMMDDXXXX");
        }
    }

    public Customer findCustomer(String SSN){
        return customersList.get(SSN);
    }

    public void removeCustomer(String SSN) throws Exception{
        Customer customer = findCustomer(SSN);
        if(customer != null){
            customersList.remove(customer);
        } else{
            throw new Exception("The customer was not found.");
        }
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
        try {
            String EOL = System.lineSeparator();
            Customer customer = findCustomer(SSN);
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            return ("Name: " + firstName + " " + lastName + EOL + "Occupation: " +
                    customer.getOccupation() + EOL + "Residential Area: " +
                    customer.getResidentialArea() + EOL);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }

    public String printSpecificCustomer(String SSN) {
        try {
            Customer customer = findCustomer(SSN);
            return printCustomer(customer.getSocialSecurityNumber());
        } catch(Exception ex) {
            return ex.getMessage();
        }
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
        try {
            Customer customer = findCustomer(SSN);
            return customer.createBankAccount(accountName);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }


    // method needs to call createTransaction from TransactionController
    public String depositMoney(String SSN, String accID, double depositAmount)  {
        try {
            BankAccount account = findBankAccount(SSN, accID);
            String message = "";
            if (depositAmount < 0) {
                message = "Amount to be deposited is too small";
            }else {
                account.setBalance(depositAmount + account.getBalance());
                message = "You have deposited " + depositAmount + " SEK successfully!!";
            }
            return message;
        } catch(Exception accountNotFound){
            return accountNotFound.getMessage();
        }
    }

    // method needs to call createTransaction from TransactionController
    public String withdrawMoney(String SSN, String accountID, double amount)  {
        try {
            BankAccount account = findBankAccount(SSN, accountID);
            String message = "";
            if (amount < 0 || amount > account.getBalance())  {
                message = "Withdrawal unsuccessful!!";
            } else {
                account.setBalance(account.getBalance() - amount);
                message = "You have withdrawn "+ amount + " SEK successfully!!";

            }
            return message;
        } catch (Exception accountNotFound){
            return accountNotFound.getMessage();
        }
    }

    public BankAccount findBankAccount(String SSN, String accountID){
        BankAccount desiredAccount = null;
        try{
            Customer customer = findCustomer(SSN);
            String accountNumber = SSN + accountID;
            for (BankAccount account : customer.getBankAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    desiredAccount = account;
                }
            }
            return desiredAccount;

        } catch(Exception e){
            return desiredAccount;
        }
    }

    // Returns the number of bank accounts a customer has, from the SSN
    public int getNumOfAccounts(String SSN){
        Customer currentCustomer = findCustomer(SSN);
        if(currentCustomer != null){
            return currentCustomer.getNumOfAccounts();
        }
        return 0;
    }

    public void deleteBankAccount(String accountNumber) throws Exception{
        String SSN = accountNumber.substring(0, 9);
        String accID = accountNumber.substring(10, 11);
        BankAccount bankAccount = findBankAccount(SSN, accID);
        if(bankAccount != null){
            Customer customer = findCustomer(SSN);
            customer.removeAccount(bankAccount);
        } else{
            throw new Exception("The bank account could not be found.");
        }
    }
    ///Made new method to check balance of a specific account
    public String checkBalance(String SSN, String accountID) {
        try {
            BankAccount bankAccount = findBankAccount(SSN, accountID);
            double balance = bankAccount.getBalance();
            return "Balance: " + balance + " SEK.";
        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
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

    public String transferMoneyWithinCustomerAccounts(String SSNSender, double amount, String accountID1, String accountID2)  {
        //TransactionController transactionController = new TransactionController();
        try {
            BankAccount to = findBankAccount(SSNSender, accountID2);
            depositMoney(SSNSender, accountID2, amount);
            withdrawMoney(SSNSender,accountID1, amount);
            return "Successfully transferred " + amount + " SEK from Account " + accountID1 + " to Account " + accountID2 + ". ";
        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
        }
            //Create a transaction here right
           // transactionController.createTransaction(SSNSender+accountID1, SSNSender+accountID2, amount);
    }

    public String transferMoneyToOtherCustomer(String SSNSender, String accountNumber, double amount, String accountID1) {
        String SSNReceiver = accountNumber.substring(0, 9);
        String accID2 = accountNumber.substring(10, 11);
        try {
            TransactionController transactionController = new TransactionController();
            BankAccount accountReceiver = findBankAccount(SSNReceiver, accID2);
            depositMoney(SSNReceiver, accID2, amount);
            withdrawMoney(SSNSender, accountID1, amount);
            transactionController.createTransaction(SSNSender+accountID1, accountNumber, amount);
            return "Successfully transferred " + amount + " SEK to " + accountNumber;
            // Make a transaction here too
        } catch (Exception accountNotFound){
          return accountNotFound.getMessage();
        }
    }

    public String printAllAccounts(String SSN) {
        try {
            //Assuming the customer is logged in already
            Customer customer = findCustomer(SSN);
            String message = "";
            if (customer.getBankAccounts().size() == 0) {
                return "No bank accounts exist for you";
            } else {
                for (BankAccount account : customer.getBankAccounts()) {
                    message += account + Util.EOL;
                }
                return message;
            }
        } catch(Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    //Added this so we can show this to the customer
    public String printSpecificAccount(String SSN, String accountID)  {
        try {
            BankAccount account = findBankAccount(SSN, accountID);
            return account.toString();
        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
        }
    }

    public String changePassword(String testPassword, String newPassword, String SSN) {
        String message = "";
        try {
            Customer customer = findCustomer(SSN);
            customer.changePassword(testPassword, newPassword);
            message = "Password changed successfully.";
        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
        }
        return message;
    }

    public String changeOccupation(String occupation, String SSN) {
        try {
            Customer customer = findCustomer(SSN);
            customer.setOccupation(occupation);
            return "Occupation changed successfully.";
            // Should an application be sent for changes like this?
        } catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    public String changeResidentialArea(String residentialArea, String SSN) {
        try {
            Customer customer = findCustomer(SSN);
            customer.setResidentialArea(residentialArea);
            return "Residential Area changed successfully.";
        } catch (Exception customerNotFound) {
            return customerNotFound.getMessage();
        }
    }
}


