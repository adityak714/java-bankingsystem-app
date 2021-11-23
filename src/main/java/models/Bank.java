package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bank {

    private final HashMap<String, User> usersCollection;
    private final LinkedHashMap<String, ArrayList<Transaction>> transactionsCollection;

    public Bank() {
        this.usersCollection = new HashMap();
        this.transactionsCollection = new LinkedHashMap<>();
    }

    public void createCustomer(String firstName, String lastName, double salary, String residentialArea, String occupation, String socialSecurityNumber) throws Exception{
        try{
            String ID = "";
            User newCustomer = new Customer(ID, firstName, lastName, socialSecurityNumber, salary, residentialArea, occupation, socialSecurityNumber);
            String uniqueID;
            do{
                int temp = (int) (Math.random()*999999);
                uniqueID = "3"+ temp;
            } while(transactionsCollection.get(uniqueID) != null);
            usersCollection.put(uniqueID, newCustomer);
        } catch(Exception e){
            throw new Exception("Please enter valid customer information");
        }
    }

    // unsure about type for from account
    public void transferBetweenCustomers(BankAccount from, String to, double amount) throws Exception{
        try{
            String userID = to.substring(1,7);
            String accountID = to.substring(8,9);
            User toUser = usersCollection.get(userID);
            toUser.deposit(accountID, amount);
            // maybe change this to match to if from is type String
            from.withdraw(amount);
            Transaction newTransaction =  new Transaction(from.getAccountNumber(), to, amount);
        } catch(Exception e){
            throw new Exception("Please enter valid transaction data.");
        }
    }
}
