package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerController {
    private static final HashMap<String, Customer> customersList = new HashMap<>(); // "customerList" is a better name?
    //Method to create a customer.
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
    //Method to find a customer
    public Customer findCustomer(String SSN){
        return customersList.get(SSN);
    }

    //Method to remove a customer
    public void removeCustomer(String SSN) throws Exception{
        Customer customer = findCustomer(SSN);
        if(customer != null){
            customersList.remove(customer);
        } else{
            throw new Exception("The customer was not found.");
        }
    }
    //Functionality to print all customers
    public String printAllCustomers() {
        int customerNumber = 1;
        if (customersList.isEmpty()) {
            return "There are no registered customers";
        }
        String message = "All registered customers" + Util.EOL
                + "--------------------------------------------------" + Util.EOL
                + "#    SSN          Name" + Util.EOL
                + "--------------------------------------------------";
        StringBuilder sb = new StringBuilder();
        for (Customer customer : customersList.values()) {
            String customerName = customer.getFirstName() + " " + customer.getLastName();
            if (customerName.length() > 32){customerName.substring(0,33);}
            sb.append(Util.EOL)
                    .append(customerNumber)
                    .append(" ".repeat(5-String.valueOf(customerNumber).length()))
                    .append(customer.getSocialSecurityNumber())
                    .append(" ".repeat(3)).append(customerName);
            customerNumber += 1;
        }
        message += sb.toString() + Util.EOL
                + "--------------------------------------------------";
        return message;
    }

    //Functionality to display a customer
    public String printCustomer(String SSN) {
        try {
            Customer customer = findCustomer(SSN);
            return customer.toString();
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
    ///Method to create a bank account for a customer
    public String createBankAccount(String SSN, String accountName){
        try {
            Customer customer = findCustomer(SSN);
            return customer.createBankAccount(accountName);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }


    // Method to deposit funds into accounts
    public String depositMoney(String SSN, String accID, double depositAmount)  {
        try {
            TransactionController transactionController = new TransactionController();
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

    // Method to withdraw funds from accounts
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
    //Method to find the bank account of the customer
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
    //Method to delete bank account
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

        try {
            TransactionController transactionController = new TransactionController();
            BankAccount from = findBankAccount(SSNSender, accountID1);

            if (from.getBalance() < amount ) {
                return "Amount too large to transfer";
            }
            else {
                depositMoney(SSNSender, accountID2, amount);
                withdrawMoney(SSNSender,accountID1, amount);
                transactionController.createTransaction(SSNSender+accountID1, SSNSender+accountID2, amount);
                return "Successfully transferred " + amount + " SEK from Account " + accountID1 + " to Account " + accountID2 + ". ";
            }

        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
        }
        //Create a transaction here right
        // transactionController.createTransaction(SSNSender+accountID1, SSNSender+accountID2, amount);
    }

    public String transferMoneyToOtherCustomer(String SSNSender, String accountNumber, double amount, String accountID1) {
        String SSNReceiver = accountNumber.substring(0, 10);
        String accID2 = accountNumber.substring(10, 12);
        BankAccount to = findBankAccount(SSNReceiver, accID2);
        if (to == null) {
            return "Receipient account not found";
        }
        else {
            try {


                TransactionController transactionController = new TransactionController();
                BankAccount from = findBankAccount(SSNSender, accountID1);
                if (from.getBalance() < amount ) {
                    return "Amount too large to transfer";
                } else {
                    depositMoney(SSNReceiver, accID2, amount);
                    withdrawMoney(SSNSender, accountID1, amount);
                    transactionController.createTransaction(SSNSender+accountID1, accountNumber, amount);
                    return "Successfully transferred " + amount + " SEK to " + accountNumber;
                }
                // Make a transaction here too
            } catch (Exception accountNotFound){
                return accountNotFound.getMessage();
            }
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
                    message += account.getAccountName() + " ACCOUNT" + Util.EOL +
                            "Account ID: " + account.getAccountNumber() + Util.EOL +
                            "Balance: " + account.getBalance() + " SEK" + Util.EOL +
                            Util.EOL;
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
    public HashMap<String, Customer> getCustomersList(){
        return customersList;
    }

    public String printAllBankAccounts() {
        int bankAccountNumber = 1;
        if (customersList.isEmpty()) {
            return "There are no bank accounts.";
        }

        String message = "All Bank Accounts" + Util.EOL
                + "--------------------------------------------------" + Util.EOL
                + "#   Account ID    Account Name      Owner Name" + Util.EOL
                + "--------------------------------------------------";
        StringBuilder sb = new StringBuilder();
        for (Customer customer : customersList.values()) {
            for (BankAccount bankAccount : customer.getBankAccounts()) {
                String accountName = bankAccount.getAccountName();
                if (accountName.length() > 17) {
                    accountName.substring(0, 18);
                }
                sb.append(Util.EOL)
                        .append(bankAccountNumber)
                        .append(" ".repeat(4 - String.valueOf(bankAccountNumber).length()))
                        .append(bankAccount.getAccountNumber())
                        .append(" ".repeat(14 - bankAccount.getAccountNumber().length()))
                        .append(accountName)
                        .append(" ".repeat(18 - accountName.length()))
                        .append(bankAccount.getCustomerFirstName()).append(" ").append(bankAccount.getCustomerLastName().substring(0, 1)).append(".");
                bankAccountNumber += 1;
            }
        }
        message += sb.toString() + Util.EOL
                + "--------------------------------------------------";
        return message;
    }
       /* ArrayList<String> allBankAccounts = new ArrayList<>();
        String bankAccountsOfACustomer = "";
        String result = "";
        for (Customer customer : getCustomersList().values()){
            bankAccountsOfACustomer = printAllAccounts(customer.getSocialSecurityNumber());
            allBankAccounts.add(bankAccountsOfACustomer);
        }
        for (String bankAccount : allBankAccounts){
            if (bankAccount.equals("No bank accounts exist for you")){
                allBankAccounts.remove(bankAccount);
            } else {
                result += bankAccount + Util.EOL;
            }
        }
        return result;*/
    }



