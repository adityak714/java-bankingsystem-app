package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

import java.util.HashMap;

public class BankAccount {
    private final String ID;
    private final String SSN;
    private final String ACCOUNT_NAME;
    private final String CUSTOMERFIRSTNAME;
    private final String CUSTOMERLASTNAME;
    private double balance;
    private final HashMap transactionMap = new HashMap();

    public BankAccount(String SSN, String ID, String customerFirstName, String customerLastName, String accName) {
        this.ID = ID;
        this.SSN = SSN;
        this.CUSTOMERFIRSTNAME = customerFirstName;
        this.CUSTOMERLASTNAME = customerLastName;
        this.ACCOUNT_NAME = accName;
        this.balance = 0.00;
    }

    public String getAccountNumber() {
        return SSN+ID;
    }

    public String getCustomerFirstName() {
        return CUSTOMERFIRSTNAME;
    }

    public String getCustomerLastName() {
        return CUSTOMERLASTNAME;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountName(){
        return this.ACCOUNT_NAME;
    }

    @Override
    public String toString() {
        return    "|--------------------------------------------------" + Util.EOL
                + "|Bank Account ID: <" + getAccountNumber() + ">" + Util.EOL
                + "|--------------------------------------------------" + Util.EOL
                + "|Account name: " + ACCOUNT_NAME + Util.EOL
                + "|Balance: " + String.format("%.2f", balance) + Util.EOL
                + "|Account Owner: " + CUSTOMERFIRSTNAME + " " + CUSTOMERLASTNAME + Util.EOL
                + "|SSN: " + SSN + Util.EOL
                + "|--------------------------------------------------";

    }
    // Investments 898722.00; #89901123123123
    public String oneLineToString(){
        return ACCOUNT_NAME+" (#"+getAccountNumber()+"), "+String.format("%.2f", balance)+" SEK";
    }
}
