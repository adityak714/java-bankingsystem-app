package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> allTransactions = new LinkedHashMap<>();
    CustomerController customerController = new CustomerController();

    public void createTransaction(String acc1, String acc2, double amount){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount);
        Transaction transaction2 = new Transaction(acc2, acc1, amount);
        String SSN1 = acc1.substring(0,9);
        String accID1 = acc1.substring(10,11);
        String SSN2 = acc2.substring(0,9);
        String accID2 = acc2.substring(10,11);
        allTransactions.get(SSN1).get(accID1).add(transaction1);
        allTransactions.get(SSN2).get(accID2).add(transaction2);
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }

    public String ascendingTransactionsByPriceForAccount (String SSN, String accID){
        Customer desiredCustomer = customerController.findCustomer(SSN);
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        String sortedTransactions = "";
        for(Transaction transaction : sortedList){
            sortedTransactions += transaction;
        }

        return sortedTransactions;
    }

    public ArrayList<Transaction> sortTransactionsByPriceInAcc (String SSN, String accID){
        ArrayList<Transaction> sortedList = allTransactions.get(SSN).get(accID);
        Collections.sort(sortedList);
        return sortedList;
    }

    public String descendingTransactionsByPriceForAccount (String SSN, String accID) {
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        String sortedTransactions = "";
        for(int i = sortedList.size(); i > 0; i--){
            sortedTransactions += sortedList.get(i) + Util.EOL;
        }

        return sortedTransactions;
    }

    public String printTransactionsForAnAccount(String SSN, String accID){
        String transactionForAnAccount="";
        Customer customer = customerController.findCustomer(SSN);
        if(customer!=null){
            for(Transaction transaction : allTransactions.get(SSN).get(accID)){
                transactionForAnAccount += transaction + Util.EOL;
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
                    transactionsForAllAccounts += transaction + Util.EOL;
                }
            }
        }
        return transactionsForAllAccounts;
    }
}
