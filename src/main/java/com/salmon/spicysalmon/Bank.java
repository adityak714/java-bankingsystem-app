package com.salmon.spicysalmon;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {

    private HashMap<User> userCollection;
    private HashMap<ArrayList<Transaction>> transactions;

    public Bank() {
        this.userCollection = new HashMap();
        this.transactions = new HashMap();
    }

}
