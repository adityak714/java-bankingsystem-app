package com.salmon.spicysalmon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bank {

    private HashMap<int, User> usersCollection;
    private LinkedHashMap<Integer, ArrayList<Transaction>> transactions;

    public Bank() {
        this.userCollection = new HashMap();
        this.transactions = new HashMap();
    }

    public void createCustomer(double salary,
                                  int residentialArea, String occupation, int socialSecurityNumber){

        User newCustomer = new Customer(salary, residentialArea, occupation, socialSecurityNumber);
        int uniqueID;
        do{
            uniqueID = (int) (Math.random()*999999);
        } while(transactions.get(uniqueID) != null);
        uniqueID = Integer.parseInt("3"+ uniqueID);
        usersCollection.put(uniqueID, newCustomer);
    }
}
