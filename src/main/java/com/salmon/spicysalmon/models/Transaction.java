package com.salmon.spicysalmon.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    public final String ID;
    public final String TO;
    public final String FROM;
    public final double AMOUNT;
    public final String DATE;
    
    public Transaction(String to, String from, double amount){
        this.ID = UUID.randomUUID().toString().replace("-", "");
        this.TO = to;
        this.FROM = from;
        this.AMOUNT = amount;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.DATE = formatter.format(date);
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

    @Override
    public int compareTo(Transaction o) {
        return Double.compare(this.AMOUNT, o.AMOUNT);
    }
}
