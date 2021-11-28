package com.salmon.spicysalmon.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Customer extends User {
    // change linkedhashmap to arraylist?
    private final LinkedHashMap<String, BankAccount> accounts;
    private final ArrayList<BankAccount> customerAccount;
    private double salary;
    private String residentialArea;
    private String occupation;

    public Customer(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) {
        super(socialSecurity, password, firstName, lastName);
        this.accounts = new LinkedHashMap<>();
        this.customerAccount = new ArrayList<>();
        this.salary = salary;
        this.residentialArea = residentialArea;
        this.occupation = occupation;
    }

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

    @Override
    public String toString() {
        return "Customer{" +
                "accounts=" + accounts +
                ", salary=" + salary +
                ", residentialArea='" + residentialArea + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }

    public double getTotalBalance(String accName){
        return accounts.get(accName).getBalance();
    }

    public double deposit(String accountID, double amount) throws Exception {
        // Find account
        for (String key : accounts.keySet()) {
            if (accountID.equals(key)) {
                BankAccount myBankAccount = accounts.get(accountID);
                if (amount <= 0) {
                    throw new Exception("Amount to be deposited is invalid");
                } else {
                    return myBankAccount.getBalance() + amount;
                }

            } else {
                System.out.println("Account could not be found.");
            }
        }
        return 0.0;
    }

    public void createBankAccount(String SSN, String firstName, String lastName) {
        BankAccount bankAccount = new BankAccount(SSN, firstName, lastName);
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
    
}
