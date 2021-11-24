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

    public void createCustomer(String password, String firstName, String lastName, double salary, String residentialArea, String occupation, String socialSecurityNumber) throws Exception{
        try{
            String uniqueID;
            do{
                int temp = (int) (Math.random()*999999);
                uniqueID = "3"+ temp;
            } while(transactionsCollection.get(uniqueID) != null);
            User newCustomer = new Customer(uniqueID, firstName, lastName, socialSecurityNumber, salary, residentialArea, occupation, socialSecurityNumber);
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

    ///We decided that we have apply account and delete account in the bank class
    public boolean applyAccount(String accName) {  return true;  }

    //Potential sub-types - Savings, Current, Pension, Family, Aktier/Fonder

    public boolean deleteAccount(int accountNumber) {
        // When the user enter the account number of the account, we have to look for
        //the account that has that number but first the user must exist before
        //The plan is that after the user has logged in, we have the option to delete account

        //User's first account has a different process to apply, and all further accounts like savings account can be added by the customer by themselves
        return true; }
    public HashMap<String, User> getUsersCollection(){
        return this.usersCollection;
    }
}
