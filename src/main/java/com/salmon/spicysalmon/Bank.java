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

    public void createCustomer(double salary, int residentialArea, String occupation, int socialSecurityNumber) throws Exception{
        try{
            User newCustomer = new Customer(salary, residentialArea, occupation, socialSecurityNumber);
            int uniqueID;
            do{
                uniqueID = (int) (Math.random()*999999);
            } while(transactions.get(uniqueID) != null);
            uniqueID = Integer.parseInt("3"+ uniqueID);
            usersCollection.put(uniqueID, newCustomer);
        } catch(Exception e){
            throw new Exception("Please enter valid customer information");
        }
    }

    // unsure about type for from account
    public void transferBetweenCustomers(Account from, String to, double amount) throws Exception{
        try{
            int userID = Integer.parseInt(to.substring(1,7));
            int accountID = Integer.parseInt(to.substring(8,9))
            User to = usersCollection.get(userID);
            to.deposit(accountID, amount);
            from.withdraw(amount);
        } catch(Exception e){
            throw new Exception("Please enter valid transaction data.");
        }
    }
}
