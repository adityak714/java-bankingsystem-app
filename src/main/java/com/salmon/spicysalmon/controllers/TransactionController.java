package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class TransactionController {

    private final LinkedHashMap<String, ArrayList<Transaction>> allTransactions;
    CustomerController customerController = new CustomerController();

    public TransactionController(){
        allTransactions = new LinkedHashMap<>();
    }

    public boolean checkIfSSNUnique(String SSN){ // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }

    public String ascendingTransactionsByPriceForAccount (String SSN, String accID){
        Customer desiredCustomer = customerController.findCustomer(SSN);
        String sortedTransactions = "";
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        for(Transaction transaction : sortedList){
            sortedTransactions += transaction;
        }

        return sortedTransactions;
    }

    public ArrayList<Transaction> sortTransactionsByPriceInAcc (String SSN, String accID){
        return allTransactions.get(SSN).get(accID).sort(Comparator.comparingDouble(Transaction::getAMOUNT));
    }

    public String descendingTransactionsByPriceForAccount (String SSN, String accID) {
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID).sort(Comparator.reverseOrder());
        String sortedTransactions = "";
        for (Transaction transaction : sortedList) {
            sortedTransactions += transaction;
        }

        return sortedTransactions;
    }
}
