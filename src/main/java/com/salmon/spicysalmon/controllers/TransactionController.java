package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Transaction;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransactionController {

    private final LinkedHashMap<String, ArrayList<Transaction>> allTransactions;

    public TransactionController() {
        allTransactions = new LinkedHashMap<>();
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }
}
