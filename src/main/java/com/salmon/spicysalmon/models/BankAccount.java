package com.salmon.spicysalmon.models;

import java.util.HashMap;

public class BankAccount {
    private final String ID;
    private final String SSN;
    private final String ACCOUNT_NAME;
    private final String CUSTOMERFIRSTNAME;
    private final String CUSTOMERLASTNAME;
    private double balance;
    private HashMap transactionMap = new HashMap();

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
        return "Account{" +
                "accountNumber=" + getAccountNumber() +
                ", customerFirstName='" + CUSTOMERFIRSTNAME + '\'' +
                ", customerLastName='" + CUSTOMERLASTNAME + '\'' +
                ", balance=" + balance +
                '}';
    }
}
