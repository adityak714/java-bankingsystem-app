package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.ApplicationController;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.models.Customer;

public class BankAccountApplication extends Application{
    private final Customer requestee;
    private String accountName;

    public BankAccountApplication(Customer customer, String accountName){
        super();
        this.requestee = customer;
        this.accountName = accountName;


    }


    public Customer getRequestee(){
        return this.requestee;
    }
    public String getAccountName(){
        return this.accountName;
    }

    public String getApprovalStatus(){
        if (super.getApprovalStatus() == null)
            return "Application for account: " + this.accountName + " is pending.";
        if (super.getApprovalStatus())
            return "Application for account: " + this.accountName + " has been approved.";
        else
            return "Application for account: " + this.accountName + " has been denied.";
    }
    public String toString(){
        return  this.requestee.getFirstName() + this.requestee.getLastName() + "\n"
                + this.requestee.getSocialSecurityNumber() + "\n"
                + this.accountName + "\n"
                + getApprovalStatus();
    }
}
