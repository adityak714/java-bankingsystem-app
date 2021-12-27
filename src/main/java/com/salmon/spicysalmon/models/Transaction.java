package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    public final String ID;
    public final String ACC1;
    public final String ACC2;
    public final double AMOUNT;
    public final String DATE;
    
    public Transaction(String acc1, String acc2, double amount){
        this.ID = UUID.randomUUID().toString().replace("-", "");
        this.ACC1 = acc1;
        this.ACC2 = acc2;
        this.AMOUNT = amount;
        this.DATE = Util.getDateAndTime();
    }

    public Transaction(String acc1, String acc2, double amount, String date){
        this.ID = UUID.randomUUID().toString().replace("-", "");
        this.ACC1 = acc1;
        this.ACC2 = acc2;
        this.AMOUNT = amount;
        this.DATE = date;
    }

    public String getID() {
        return ID;
    }

    public String getACC1() {
        return ACC1;
    }

    public String getACC2() {
        return ACC2;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }

    public String getDATE() {
        return DATE;
    }

    @Override
    public int compareTo(Transaction o) {
        return Double.compare(this.AMOUNT, o.AMOUNT);
    }
}
