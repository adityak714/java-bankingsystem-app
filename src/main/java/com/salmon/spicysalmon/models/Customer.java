package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

import java.util.ArrayList;

public class Customer extends User {
    private final ArrayList<BankAccount> bankAccounts;
    private double salary;
    private String residentialArea;
    private String occupation;

    public Customer(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) {
        super(socialSecurity, password, firstName, lastName);
        this.bankAccounts = new ArrayList<>();
        this.salary = salary;
        this.residentialArea = residentialArea;
        this.occupation = occupation;
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
        String tempArea = this.residentialArea;
        if (this.residentialArea.length() > 31){ tempArea = this.residentialArea.substring(0,32);}
        return    "--------------------------------------------------" + Util.EOL
                + "Customer Information" + Util.EOL
                + "--------------------------------------------------" + Util.EOL
                + super.toString() + Util.EOL
                + "Salary: " + this.salary + Util.EOL
                + "Residential Area: " + tempArea + Util.EOL
                + "Occupation: " + this.occupation + Util.EOL;
    }

    public double getTotalBalance(){
        BankAccount bankAccount = bankAccounts.get(0);
        return bankAccount.getBalance();
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    // returns the number of bank accounts this customer has
    public int getNumOfAccounts(){
        return bankAccounts.size();
    }

    // no need to pass in any of the parameters, all are already attributes of the customer, however, should it in a name for the account
    public String createBankAccount(String accountName) {
        String accID = bankAccounts.size() < 10 ? "0"+(bankAccounts.size()+1) : (bankAccounts.size()+1)+"";
        BankAccount bankAccount = new BankAccount(this.getSocialSecurityNumber(), accID, this.getFirstName(), this.getLastName(), accountName);
        bankAccounts.add(bankAccount);
        return accID;
    }

    public String deleteBankAccount(String accID){
        for(BankAccount acc : bankAccounts){
            if(acc.getAccountNumber() == this.getSocialSecurityNumber()+accID){
                bankAccounts.remove(acc);
                return "Account deleted successfully.";
            }
        }
        return "Account was not found.";
    }

    public int getNumberOfAccounts(){
        return bankAccounts.size();
    }

    public void removeAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
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
