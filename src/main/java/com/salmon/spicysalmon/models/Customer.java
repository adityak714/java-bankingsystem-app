package com.salmon.spicysalmon.models;

import java.util.ArrayList;

public class Customer extends User {
    private final ArrayList<BankAccount> customerAccounts;
    private double salary;
    private String residentialArea;
    private String occupation;

    public Customer(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) {
        super(socialSecurity, password, firstName, lastName);
        this.customerAccounts = new ArrayList<>();
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
        return super.toString()+" Customer{" +
                "customerAccounts=" + customerAccounts +
                ", salary=" + salary +
                ", residentialArea='" + residentialArea + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }

    public double getTotalBalance(){
        BankAccount bankAccount = customerAccounts.get(0);
        return bankAccount.getBalance();
    }

    public ArrayList<BankAccount> getCustomerAccounts() {
        return customerAccounts;
    }



    // no need to pass in any of the parameters, all are already attributes of the customer, however, should it in a name for the account
    public String createBankAccount(String accountName) {
        String accID = customerAccounts.size() < 10 ? "0"+(customerAccounts.size()+1) : (customerAccounts.size()+1)+"";
        BankAccount bankAccount = new BankAccount(this.getSocialSecurityNumber(), accID, this.getFirstName(), this.getLastName(), accountName);
        customerAccounts.add(bankAccount);
        return "Account " + accountName + " created successfully.";
    }

    public String deleteBankAccount(String accID){
        for(BankAccount acc : customerAccounts){
            if(acc.getAccountNumber() == this.getSocialSecurityNumber()+accID){
                customerAccounts.remove(acc);
                return "Account deleted successfully.";
            }
        }
        return "Account was not found.";
    }

    public int getNumberOfAccounts(){
        return customerAccounts.size();
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
