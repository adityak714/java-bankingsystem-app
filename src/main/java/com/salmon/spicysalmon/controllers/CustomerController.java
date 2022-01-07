package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerController {
    private static final HashMap<String, Customer> customersList = new HashMap<>(); // Arraylist of customers
    //Method to create a customer.
    public void createCustomer(String SSN, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) throws Exception {
        TransactionController transactionsController = new TransactionController();
        //If customer exists and the SSN is unique
        if(findCustomer(SSN) == null && transactionsController.checkIfSSNUnique(SSN)) {
            Customer newCustomer = new Customer(SSN, password, firstName, lastName, salary, residentialArea, occupation);
            customersList.put(SSN, newCustomer);
            transactionsController.initializeCustomer(SSN);
        } else{
            throw new Exception("A customer with that SSN already exists.");
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
            customersList.remove(SSN, customer);
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
            String result = customer.toString();
            result += bankAccountsStringBuilder(customer.getBankAccounts());
            return result;
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
    public void createBankAccount(String SSN, String accountName) throws Exception {
        TransactionController transactionController = new TransactionController();
        Customer customer = findCustomer(SSN);
        if(customer != null){
            String newAccID = customer.createBankAccount(accountName);
            transactionController.initializeBankAccount(SSN, newAccID);
        } else{
            throw new Exception("No customer with that SSN was found.");
        }
    }


    // Method to deposit funds into accounts
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
    public BankAccount findBankAccount(String SSN, String accountID) throws Exception{
        Customer customer = findCustomer(SSN);
        String accountNumber = SSN + accountID;
        BankAccount desiredAccount = null;
        for (BankAccount account : customer.getBankAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                desiredAccount = account;
            }
        }
        if(desiredAccount == null){
            throw new Exception("Bank account not found.");
        }
        return desiredAccount;
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
        String SSN = accountNumber.substring(0, 10);
        String accID = accountNumber.substring(10, 12);
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
    ///Method to transfer money from one account to another owned by the same person
    public String transferMoneyWithinCustomerAccounts(String SSNSender, double amount, String accountID1, String accountID2) throws Exception {
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
        //Create a transaction here right
        // transactionController.createTransaction(SSNSender+accountID1, SSNSender+accountID2, amount);
    }
    //Method for transfer of money from a logged in customer to another customer
    public String transferMoneyToOtherCustomer(String SSNSender, String accountNumber, double amount, String accountID1) throws Exception{
        String SSNReceiver = accountNumber.substring(0, 10);
        String accID2 = accountNumber.substring(10, 12);

        try {
            BankAccount receipient = findBankAccount(SSNReceiver, accID2);
        } catch (NullPointerException accountNotFound) {
            //Check if account is not registered
            return "Recipient account not found.";
        }

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
    //Method to print all registered bank accounts
    public String printAllAccounts(String SSN) {
        try {
            //Assuming the customer is logged in already
            Customer customer = findCustomer(SSN);
            return bankAccountsStringBuilder(customer.getBankAccounts());
        } catch(Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    //Method to display the bank account of a customer
    public String printSpecificAccount(String SSN, String accountID)  {
        try {
            BankAccount account = findBankAccount(SSN, accountID);
            return account.oneLineToString();
        } catch (Exception accountNotFound) {
            return accountNotFound.getMessage();
        }
    }
    //Method to change the password of a customer
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
    //Method that changes the occupation of the customer
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
    //method that changes the residential area of a customer
    public String changeResidentialArea(String residentialArea, String SSN) {
        try {
            Customer customer = findCustomer(SSN);
            customer.setResidentialArea(residentialArea);
            return "Residential Area changed successfully.";
        } catch (Exception customerNotFound) {
            return customerNotFound.getMessage();
        }
    }
    //Returns all registered customers
    public HashMap<String, Customer> getCustomersList(){
        return customersList;
    }

    public String bankAccountsStringBuilder(ArrayList<BankAccount> accounts){
        StringBuilder sb = new StringBuilder();
        String accName, accountNumber, accID;
        double balance;
        if(accounts.isEmpty()){
            return "No registered accounts were found.";
        }
        int counter = 0;
        for(BankAccount account: accounts){
            accName = account.getAccountName();
            // trim to 10 if account name is long
            accName = accName.length() > 11 ? accName.substring(0,12) + "." : accName;
            accountNumber = account.getAccountNumber();
            accID = accountNumber.charAt(10) == '0' ? String.valueOf(accountNumber.charAt(11)) : accountNumber.substring(10, 12);
            balance = account.getBalance();
            sb.append(accID).append(" ".repeat(6-accID.length())).append(accName).append(" ".repeat(17-accName.length()))
                    .append(String.format("%.2f", balance)).append(" ".repeat(17-String.format("%.2f", balance).length()))
                    .append(accountNumber);
            counter += 1;
            if(counter != accounts.size()){
                sb.append(Util.EOL);
            }
        }

        return Util.EOL
                + "---------------------------------------------------------" + Util.EOL
                + "#     " + "Name             " + "Balance          " + "Account Number   " + Util.EOL
                + "---------------------------------------------------------" +  Util.EOL
                + sb + Util.EOL
                + "---------------------------------------------------------" + Util.EOL;

    }

    public String printAllBankAccountsEmployee() {
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



