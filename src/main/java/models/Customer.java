package models;

import java.util.LinkedHashMap;

public class Customer extends User {
    // change linkedhashmap to arraylist?
    private final LinkedHashMap<String, BankAccount> accounts;
    private double salary;
    private String residentialArea;
    private String occupation;

    public Customer(String userID, String password, String firstName, String lastName, String socialSecurityNumber, double salary, String residentialArea, String occupation) {
        super(userID, password, firstName, lastName, socialSecurityNumber);
        this.accounts = new LinkedHashMap<>();
        this.salary = salary;
        this.residentialArea = residentialArea;
        this.occupation = occupation;
    }

    // super functions, adding social security numbers and attributes inherited from super class User

    public LinkedHashMap<String, BankAccount> getAccounts() {
        return accounts;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getResidentialArea() {
        return residentialArea;
    }
    public void setResidentialArea(String residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getTotalBalance(String accName){
        return accounts.get(accName).getBalance();
    }
<<<<<<< HEAD
}
=======

    // deposit to an Account, a method takes in account number and amount
    // deposit to an Account, a method takes in account number and amount
    /*public boolean verifyAccount(String accountID) {
        for (String key : accounts.keySet()) {
            if (accountID.equals(key)) {
                 return true;
            }
            else {
                return false;
            }
        }
    }

     */





    public double deposit(String accountID, double amount) throws Exception {

        ////Find account
        for (String key : accounts.keySet()) {
            if (accountID.equals(key)) {
                BankAccount myBankAccount = accounts.get(accountID);
                if (amount <= 0) {throw new Exception("Amount to be deposited is invalid"); }
                else { return myBankAccount.getBalance() + amount; }

            }
            else {
                System.out.println("Account could not be found.");
            }
        /*if (amount <= 0.00) {
            throw new Exception("Please enter a valid amount to be deposited:");
        }
        else {
            for (String key : accounts.keySet()) {
                if (accountID.equals(key)) {
                    BankAccount myBankAccount = accounts.get(accountID);
                    return myBankAccount.getBalance() + amount;
                }
                else {
                    System.out.println("Account could not be found.");
                }
            }
        }

         */


    }
    
}
>>>>>>> cabbe5d3189da8eff79abe118bf8661dc744c796
