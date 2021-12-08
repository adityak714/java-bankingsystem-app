package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Transaction;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> allTransactions = new LinkedHashMap<>();

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

    public TransactionController() {
        allTransactions = new LinkedHashMap<>();
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }
}
