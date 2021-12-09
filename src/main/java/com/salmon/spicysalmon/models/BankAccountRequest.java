package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.*;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.models.Customer;

public class BankAccountRequest extends AccountRequest{
    private final Customer requestee;
    private String accountName;


    public BankAccountRequest(Customer customer, String accountName){
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
        if (super.getIsApproved() == null)
            return "Application for account: " + this.accountName + " is pending.";
        if (super.getIsApproved())
            return "Application for account: " + this.accountName + " has been approved.";
        else
            return "Application for account: " + this.accountName + " has been denied.";
    }
    public String toString(){
        return  this.requestee.getFirstName() + this.requestee.getLastName() + Util.EOL
                + this.requestee.getSocialSecurityNumber() + Util.EOL
                + this.accountName + Util.EOL
                + getApprovalStatus();
    }
}
