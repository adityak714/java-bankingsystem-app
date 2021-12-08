package com.salmon.spicysalmon.models;

import java.util.HashMap;

public class BankAccount {
    private final String ACCOUNTNUMBER;
    private final String CUSTOMERFIRSTNAME;
    private final String CUSTOMERLASTNAME;
    private double balance;
    private HashMap transactionMap = new HashMap();

    public BankAccount(String number, String accountNumber, String customerFirstName, String customerLastName) {
        this.ACCOUNTNUMBER = accountNumber;
        this.CUSTOMERFIRSTNAME = customerFirstName;
        this.CUSTOMERLASTNAME = customerLastName;
        this.balance = 0.00;
    }

    public String getAccountNumber() {
        return ACCOUNTNUMBER;
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


    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + ACCOUNTNUMBER +
                ", customerFirstName='" + CUSTOMERFIRSTNAME + '\'' +
                ", customerLastName='" + CUSTOMERLASTNAME + '\'' +
                ", balance=" + balance +
                '}';
    }
}
