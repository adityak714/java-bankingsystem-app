package com.salmon.spicysalmon.models;

import java.util.UUID;

public class Transaction {
    public final String ID;
    public final String TO;
    public final String FROM;
    public final double AMOUNT;

    // potentially add date

    public Transaction(String to, String from, double amount){
        this.ID = UUID.randomUUID().toString().replace("-", "");
        this.TO = to;
        this.FROM = from;
        this.AMOUNT = amount;
    }

    public String getID() {
        return ID;
    }

    public String getTO() {
        return TO;
    }

    public String getFROM() {
        return FROM;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }
}