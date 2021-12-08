package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.BankAccountApplication;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransactionController {

    private final LinkedHashMap<String, ArrayList<Transaction>> allTransactions;
    private final String EOL = System.lineSeparator();

    CustomerController customerController = new CustomerController();

    public TransactionController(){
        allTransactions = new LinkedHashMap<>();
    }

    public boolean checkIfSSNUnique(String SSN){ // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }
    public String printTransactionsForAnAccount(String SSN, String accID){
        String transactionForAnAccount="";
        Customer customer = customerController.findCustomer(SSN);
        if(customer!=null){
            for(Transaction transaction : allTransactions.get(SSN).get(accID){
                transactionForAnAccount += transaction;
            }
        }
        return transactionForAnAccount;
    }
    public String printTransactionsForAllAccounts(String SSN){
        String transactionsForAllAccounts = "";
        Customer customer = customerController.findCustomer(SSN);
        if(customer!=null){
            for(String accountKey : allTransactions.get(SSN).keySet()){
                for(Transaction transaction : allTransactions.get(SSN).get(accountKey)){
                    transactionsForAllAccounts += transaction + EOL;
                }
            }
        }
        return transactionsForAllAccounts;
    }
}
